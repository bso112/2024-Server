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
public class GetPortOneAccessTokenReq {
    @JsonProperty("imp_secret")
    private String secret;
    @JsonProperty("imp_key")
    private String apiKey;
}
