package com.transport.transport.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.transport.transport.model.entity.PayPal;
import com.transport.transport.model.request.booking.CancelBooking;
import com.transport.transport.model.request.paypal.PaypalRequest;

public interface PaypalService {

    public Payment createPayment(PaypalRequest paypalRequest) throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    public PayPal addPayment(PayPal request);

    public String ReturnTicket(CancelBooking cancelBooking) throws PayPalRESTException;
}
