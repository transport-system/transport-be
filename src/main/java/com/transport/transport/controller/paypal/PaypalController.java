package com.transport.transport.controller.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.entity.PayPal;

import com.transport.transport.service.PaypalService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Payment.PAYMENT_ENDPOINT)
@Api( tags = "Payment")
public class PaypalController {
    @Autowired
    PaypalService service;

    @PostMapping("/payment/{request}")
    public String payment(@PathVariable("request") double request) throws PayPalRESTException {
            Payment payment = service.createPayment(request);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        return "redirect:/";
    }

    @PostMapping("/paypal/success")
    public ResponseEntity<String> success(@RequestBody String body) {
        JSONObject jsonObject = new JSONObject(body);
        String paymentId = jsonObject.getJSONObject("payment").getString("id");
        String state = jsonObject.getJSONObject("payment").getString("state");
        // Xử lý thông tin trả về từ PayPal ở đây
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/paypal/cancel")
    public ResponseEntity<String> cancle(@RequestBody String body) {
        return ResponseEntity.ok("Cancle");
    }

    @PostMapping(value = "/createPaypal")
    public void successPay(@RequestBody PayPal request) throws PayPalRESTException {
        Payment payment = service.executePayment(request.getPaymentId(), request.getPayerId());
        service.addPayment(request);
    }

}
