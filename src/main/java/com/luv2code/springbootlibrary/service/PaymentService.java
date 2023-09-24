package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException;

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception;
}
