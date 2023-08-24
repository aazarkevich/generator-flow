package ru.generator.config.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonUtilities {
    private final ObjectMapper mapper;

    public JsonUtilities(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String objectToJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> T jsonToObject(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json.getBytes(), valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
