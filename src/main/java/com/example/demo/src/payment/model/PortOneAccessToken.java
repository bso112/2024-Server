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
public class PortOneAccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    private Long now;
    @JsonProperty("expired_at")
    private Long expiredAt;


}
