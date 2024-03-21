package com.example.demo.src.payment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.payment.model.GetPortOneAccessTokenReq;
import com.example.demo.src.payment.model.GetPortOneAccessTokenRes;
import com.example.demo.src.payment.model.PortOneAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class PortOneService {
    private final RestTemplate restTemplate;
    @Value("${apikey.portone.secret}")
    private String portOneSecret;
    @Value("${apikey.portone.apikey}")
    private String portOneApikey;

    public PortOneAccessToken getAccessToken() {
        GetPortOneAccessTokenReq req = new GetPortOneAccessTokenReq(portOneSecret, portOneApikey);
        GetPortOneAccessTokenRes res = restTemplate.postForObject("https://api.iamport.kr/users/getToken", req, GetPortOneAccessTokenRes.class);
        if (res != null && res.getPortOneAccessToken() != null) {
            return res.getPortOneAccessToken();
        }
        throw new BaseException(BaseResponseStatus.GET_FAIL_PORTONE_ACCESS_TOKEN);
    }
}
