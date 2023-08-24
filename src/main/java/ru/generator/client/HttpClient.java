package ru.generator.client;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpClient {
    private final RestTemplate restTemplate;

    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <R> ResponseEntity<R> executeRequest(String url, HttpEntity request, HttpMethod httpMethod, Class<R> responseType) {
        MDC.put("request", request.toString());
        try {
            ResponseEntity<R> response = restTemplate.exchange(url, httpMethod, request, responseType);
            return response;
        } catch (Exception ex) {
            log.error("Execute request error! Message: " + ex.getMessage());
            throw ex;
        }
    }
}
