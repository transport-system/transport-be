package com.transport.transport.controller.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.entity.PayPal;
import com.transport.transport.model.request.booking.CancleBooking;
import com.transport.transport.model.request.paypal.PaypalRequest;
import com.transport.transport.service.PaypalService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Payment.PAYMENT_ENDPOINT)
@Api( tags = "Payment")
public class PaypalController {
    @Autowired
    PaypalService service;

    @PostMapping("/payment")
    public String payment(@Valid @RequestBody PaypalRequest request) throws PayPalRESTException {
            Payment payment = service.createPayment(request);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        return "redirect:/";
    }
    @PostMapping(value = "/createPaypal")
    public void successPay(@RequestBody PayPal request) throws PayPalRESTException {
        Payment payment = service.executePayment(request.getPaymentId(), request.getPayerId());
        service.addPayment(request);
    }

    @PostMapping("/refund")
    public String refund(@RequestBody CancleBooking request) throws PayPalRESTException {
        return service.ReturnTicket(request);
    }
}
