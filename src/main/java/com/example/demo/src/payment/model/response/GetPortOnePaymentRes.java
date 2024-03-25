package com.example.demo.src.payment.model.response;

import com.example.demo.src.payment.model.PaymentAnnotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOnePaymentRes {

    private int code;
    private String message;
    private PaymentAnnotation response;
}

