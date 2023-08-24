package ru.generator.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.generator.client.HttpClient;
import ru.generator.config.json.JsonUtilities;
import ru.generator.enums.HttpMethods;
import ru.generator.model.InstructionFlow;
import utils.MapUtils;

import java.util.Map;

@Slf4j
@Service
public class CreatorSubscriber {
    private final JsonUtilities utilities;
    private final HttpClient httpClient;

    public CreatorSubscriber(JsonUtilities utilities, HttpClient httpClient) {
        this.utilities = utilities;
        this.httpClient = httpClient;
    }

    public IntegrationFlow getSubscriberHttp(MessageChannel channel, InstructionFlow.Flow.Configuration config) {
        return IntegrationFlow.from(channel)
                .handle(String.class, (request, messageHeaders) -> {
                    Map<String, Object> mapRequest = utilities.jsonToObject(request, Map.class);
                    JSONObject body = getBody(config.getRequest(), mapRequest);
                    HttpHeaders httpHeaders = getHttpHeaders(config.getRequest());
                    HttpEntity httpEntity = new HttpEntity(body.toString(), httpHeaders);
                    ResponseEntity<String> responseEntity = httpClient.executeRequest(config.getRequest().getPath(), httpEntity,
                            HttpMethods.getHttpMethod(config.getRequest().getMethod()), String.class);
                    if (config.getResponse() == null || isValidResponse(responseEntity, config.getResponse())) {
                        return responseEntity.getBody();
                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                })
                .get();
    }

    private JSONObject getBody(InstructionFlow.Flow.Configuration.Request request, Map<String, Object> mapRequest) {
        if (request.getFields() != null) {
            JSONObject body = new JSONObject();
            for (InstructionFlow.Flow.Configuration.Request.Field v
                    : request.getFields()) {
                Object fieldValue = mapRequest.get(v.getFieldValue().replaceAll("[^A-Za-zА-Яа-я0-9]", ""));
                body.put(v.getFieldName(), fieldValue);
            }
            return body;
        }
        return new JSONObject();
    }

    private HttpHeaders getHttpHeaders(InstructionFlow.Flow.Configuration.Request request) {
        if (request.getHeaders() != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            for (InstructionFlow.Flow.Configuration.Request.Header header : request.getHeaders()) {
                httpHeaders.add(header.getHeaderName(), header.getHeaderValue());
            }
            return httpHeaders;
        }
        return new HttpHeaders();
    }

    private boolean isValidResponse(ResponseEntity responseEntity, InstructionFlow.Flow.Configuration.Response response) {
        Map<String, Object> map = new JSONObject((String) responseEntity.getBody()).toMap();
        for (InstructionFlow.Flow.Configuration.Request.Field field : response.getFields()) {
            String valueByKey = MapUtils.getValueByKey(map, field.getFieldName());
            if (valueByKey == null || !valueByKey.equals(field.getFieldValue())) {
                return false;
            }
        }
        return true;
    }
}
