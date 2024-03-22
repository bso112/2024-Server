package com.example.demo.common.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @NotNull ClientHttpResponse intercept(HttpRequest request, byte @NotNull [] body, ClientHttpRequestExecution execution) throws IOException {
        // request log
        URI uri = request.getURI();
        traceRequest(request, body);

        // execute
        ClientHttpResponse response = execution.execute(request, body);

        // response log
        traceResponse(response, uri);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        String reqLog = "[REQUEST] " +
                "Uri : " + request.getURI() +
                ", Method : " + request.getMethod() +
                ", Request Body : " + new String(body, StandardCharsets.UTF_8);
        log.info(reqLog);
    }

    private void traceResponse(ClientHttpResponse response, URI uri) throws IOException {
        String resLog = "[RESPONSE] " +
                "Uri : " + uri +
                ", Status code : " + response.getStatusCode() +
                ", Response Body : " + StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        log.info(resLog);
    }
}
