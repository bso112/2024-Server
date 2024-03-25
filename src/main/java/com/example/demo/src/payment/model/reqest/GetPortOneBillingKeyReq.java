package com.example.demo.src.payment.model.reqest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneBillingKeyReq {

    @JsonProperty("card_number")
    private String cardNumber;
    private String expiry;
}
