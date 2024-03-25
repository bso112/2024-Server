package com.example.demo.src.payment.model.response;

import com.example.demo.src.payment.model.PaymentAnnotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentsRes {
    private int code;
    private String message;
    private List<PaymentAnnotation> response;
}
