package com.transport.transport.service.impl.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.transport.transport.common.Status;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.entity.PayPal;
import com.transport.transport.model.request.booking.CancelBooking;
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

@Service
@RequiredArgsConstructor
public class PaypalServiceImple  implements PaypalService {

    @Autowired
    private APIContext apiContext;

    public final PayPalRepository payPalRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    @Override
    public Payment createPayment(double request) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        request = request/24000;
        double total = new BigDecimal(request).setScale(2, RoundingMode.HALF_UP).doubleValue();
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
        redirectUrls.setCancelUrl("http://localhost:8088/api/paypal/paypal/cancel");
        redirectUrls.setReturnUrl("http://localhost:8088/api/paypal/paypal/success");
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

}
