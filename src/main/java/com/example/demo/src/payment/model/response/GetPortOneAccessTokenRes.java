package com.example.demo.src.payment.model.response;

import com.example.demo.src.payment.model.PortOneAccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneAccessTokenRes {
    private int code;
    private String message;
    private PortOneAccessToken response;

}
