package com.luv2code.springbootlibrary.service.impl;

import com.luv2code.springbootlibrary.dao.PaymentRepository;
import com.luv2code.springbootlibrary.dao.entity.Payment;
import com.luv2code.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.luv2code.springbootlibrary.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    Logger log = Logger.getLogger("");
    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Testing Payment");

        CustomerCreateParams customerCreateParams =
                CustomerCreateParams.builder()
                        .setName("Shankz")
                        .setAddress(
                                CustomerCreateParams.Address.builder()
                                        .setLine1("510 Townsend St")
                                        .setPostalCode("98140")
                                        .setCity("San Francisco")
                                        .setState("CA")
                                        .setCountry("US")
                                        .build()
                        )
                        .build();

        Customer customer = Customer.create(customerCreateParams);

        params.put("customer", customer.getId());

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);
        if (payment == null) {
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
