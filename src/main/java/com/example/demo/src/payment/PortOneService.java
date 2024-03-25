package com.example.demo.src.payment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.payment.model.*;
import com.example.demo.src.payment.model.reqest.GetPaymentsReq;
import com.example.demo.src.payment.model.reqest.GetPortOneAccessTokenReq;
import com.example.demo.src.payment.model.response.GetPaymentsRes;
import com.example.demo.src.payment.model.response.GetPortOneAccessTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PortOneService {
    private final RestTemplate restTemplate;
    @Value("${apiConfig.portone.secret}")
    private String portOneSecret;
    @Value("${apiConfig.portone.apikey}")
    private String portOneApikey;

    public PortOneAccessToken getAccessToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        GetPortOneAccessTokenReq req = new GetPortOneAccessTokenReq(portOneSecret, portOneApikey);
        GetPortOneAccessTokenRes res = restTemplate.postForObject("https://api.iamport.kr/users/getToken", new HttpEntity<>(req, httpHeaders), GetPortOneAccessTokenRes.class);
        if (res != null && res.getResponse() != null) {
            return res.getResponse();
        }
        throw new BaseException(BaseResponseStatus.GET_FAIL_PORTONE_ACCESS_TOKEN);
    }

    public List<PaymentAnnotation> getPayments(String accessToken, List<String> impUid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", accessToken);
        GetPaymentsReq req = new GetPaymentsReq(impUid);
        GetPaymentsRes res = restTemplate.postForObject("https://api.iamport.kr/payments", new HttpEntity<>(req, httpHeaders), GetPaymentsRes.class);
        if (res != null && res.getResponse() != null) {
            return res.getResponse();
        }
        throw new BaseException(BaseResponseStatus.GET_FAIL_PORTONE_PAYMENTS);
    }
}

