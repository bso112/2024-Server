package com.example.demo.src.payment.model.reqest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentReq {

    @JsonProperty("imp_uid")
    @NotNull
    String impUid;
}
