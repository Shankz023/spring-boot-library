package com.luv2code.springbootlibrary.requestmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PaymentInfoRequest {

    private int amount;
    private String currency;
    private String receiptEmail;
    private String paymentMethodId;
}
