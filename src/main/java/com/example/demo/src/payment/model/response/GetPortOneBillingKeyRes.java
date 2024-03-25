package com.example.demo.src.payment.model.response;

import com.example.demo.src.payment.model.CustomerAnnotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneBillingKeyRes {

    private int code;
    private String message;
    private CustomerAnnotation response;
}

