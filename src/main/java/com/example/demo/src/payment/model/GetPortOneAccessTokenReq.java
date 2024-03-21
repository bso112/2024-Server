package com.example.demo.src.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneAccessTokenReq {

    private String secret;
    private String apiKey;
}
