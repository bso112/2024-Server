package com.example.demo.src.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnnotation {

    @JsonProperty("imp_uid")
    private String impUid;
    private int amount;
    private String name;
}
