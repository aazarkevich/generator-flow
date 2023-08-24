package ru.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HttpMethods {
    GET("get", HttpMethod.GET),
    POST("post", HttpMethod.POST),
    PUT("put", HttpMethod.PUT),
    PATCH("patch", HttpMethod.PATCH);

    private String method;
    private HttpMethod httpMethod;

    public static HttpMethod getHttpMethod(String method) {
        return Arrays.stream(HttpMethods.values())
                .filter(httpMethods -> httpMethods.getMethod().equalsIgnoreCase(method))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Not found HttpMethod"))
                .getHttpMethod();
    }
}
