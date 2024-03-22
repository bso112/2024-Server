package com.example.demo.common.scheduler;

import com.example.demo.src.payment.entity.Payment;
import com.example.demo.src.payment.model.PaymentAnnotation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidPayment {
    private final Payment asIs;
    private final PaymentAnnotation toBe;

}
