package com.example.demo.common.intercepter;

import com.example.demo.src.payment.PortOneTokenRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@AllArgsConstructor
public class PortOneHttpHeaderInterceptor implements ClientHttpRequestInterceptor {

    private final PortOneTokenRepository tokenRepository;

    @Override
    public @NotNull ClientHttpResponse intercept(@NotNull HttpRequest request, byte @NotNull [] body,
                                                 @NotNull ClientHttpRequestExecution execution) throws IOException {
        if(request.getURI().getHost().equals("api.iamport.kr")){
            request.getHeaders().addAll(createPortOneReqHeader());
        }

        return execution.execute(request, body);
    }

    private HttpHeaders createPortOneReqHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", tokenRepository.getToken());
        return httpHeaders;
    }
}
