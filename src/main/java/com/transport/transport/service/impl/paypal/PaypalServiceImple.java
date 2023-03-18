package com.transport.transport.service.impl.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.transport.transport.common.PaymentType;
import com.transport.transport.common.Status;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.entity.PayPal;
import com.transport.transport.model.request.booking.CancleBooking;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.request.paypal.PaypalRequest;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.PayPalRepository;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.service.PaypalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaypalServiceImple  implements PaypalService {

    @Autowired
    private APIContext apiContext;

    public final PayPalRepository payPalRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    @Override
    public Payment createPayment(PaypalRequest request) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        double total = new BigDecimal(request.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public PayPal addPayment(PayPal request) {
        PayPal add = new PayPal();
        add.setPaymentId(request.getPaymentId());
        add.setCustomerId(request.getCustomerId());
        add.setTripId(request.getTripId());
        add.setPayerId(request.getPayerId());
        add.setSaleId(request.getSaleId());
        payBooking(request);
        return payPalRepository.save(add);
    }

    public Booking payBooking(PayPal request) {
        return bookingRepository.findById(request.getBookingId()).map((booking) -> {
            if (booking.getStatus().equalsIgnoreCase(Status.Booking.PENDING.name())) {
                booking.setStatus(Status.Booking.DONE.name());
                return bookingRepository.save(booking);
            } else {
                throw new BadRequestException("Can't pay booking");
            }
        }).orElseThrow(() -> new NotFoundException("Booking id not found: " + request.getBookingId()));
    }

    @Override
    public String ReturnTicket(CancleBooking cancleBooking) throws PayPalRESTException {

        List<Integer> numberSeat = cancleBooking.getSeatNumber();
        for(Integer seat: numberSeat){
            FreeSeat freeSeat = seatRepository.findByBooking_IdAndSeatNumber(seat.intValue(), cancleBooking.getBookingId());
            freeSeat.setStatus(Status.Seat.INACTIVE.name());
            seatRepository.save(freeSeat);
        }
        Booking booking = bookingRepository.findById(cancleBooking.getBookingId()).get();

        //Change number seat
        int newNumberOfSeat = booking.getNumberOfSeats() - numberSeat.size();
        booking.setNumberOfSeats(newNumberOfSeat);

        //Check Time
        Calendar c = Calendar.getInstance();
        c.setTime(booking.getCreateBookingTime());
        c.add(Calendar.DATE, +1);
        Timestamp timeAccept = new Timestamp(c.getTimeInMillis());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp returnTime = booking.getTrip().getTimeReturn();

        //Check Date to Change total price
        //Check TH1: Time in Create after 1 day
        double price = booking.getTotalPrice().doubleValue() / booking.getNumberOfSeats();
        double newPrice = 0;
        if(now.after(timeAccept)) {
            newPrice = booking.getTotalPrice().doubleValue() - price * numberSeat.size();
        } else if (now.before(returnTime) && now.after(timeAccept)) {
            double total = booking.getTotalPrice().doubleValue() - price * numberSeat.size();
            newPrice = total - (total*(20/100));
        }else {
            newPrice = booking.getTotalPrice().doubleValue();
        }
        booking.setTotalPrice(BigDecimal.valueOf(newPrice));
        //Check if number seat = 0 => This Booking cancle => Change Status
        if (newNumberOfSeat == 0){
            booking.setStatus(Status.Seat.INACTIVE.name());
        }

        PayPal payPal = payPalRepository.findByBookingId(booking.getId());

// Tạo một đối tượng Refund với thông tin hoàn tiền
        Refund refund = new Refund();
        Amount amount = new Amount();
        amount.setTotal(String.valueOf(newPrice)); // Số tiền cần hoàn tiền
        amount.setCurrency("USD");
        refund.setAmount(amount);

// Gọi API để thực hiện hoàn tiền
        Payment payment = new Payment();
        Sale sale = new Sale();
        sale.setId(payPal.getSaleId());
        payment.setId(payPal.getPaymentId());
        Refund refundedPaymentRefund = sale.refund(apiContext, refund);
        return refundedPaymentRefund.toJSON();
    }
}
