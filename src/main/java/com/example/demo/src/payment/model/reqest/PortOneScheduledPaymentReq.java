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
public class PortOneScheduledPaymentReq {
    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
}
