package com.example.demo.src.payment;

import com.example.demo.src.payment.model.PortOneAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class PortOneTokenRepository {

    private final PortOneService portOneService;
    private PortOneAccessToken token;

    public String getToken() {
        if (token == null || token.getExpiredAt() <= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            token = portOneService.getAccessToken();
        }
        if (token != null) {
            return "Bearer " + token.getAccessToken();
        } else {
            return null;
        }
    }
}
