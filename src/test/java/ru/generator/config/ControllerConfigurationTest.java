package ru.generator.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.generator.config.json.JsonUtilities;
import ru.generator.model.InstructionFlow;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerConfigurationTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    JsonUtilities utilities;
    String body = "{\"name\":\"test\",\"type\":\"http\",\"flows\":{\"url\":\"/testik\",\"configuration\":{\"request\":{\"path\":\"http://localhost:8191/mock\",\"method\":\"POST\",\"headers\":[{\"headerName\":\"Content-Type\",\"headerValue\":\"application/json\"}],\"fields\":[{\"fieldName\":\"firstName\",\"fieldValue\":\"+#name#\"},{\"fieldName\":\"test1\",\"fieldValue\":\"#date#\"},{\"fieldName\":\"test2\",\"fieldValue\":\"#testNumber#\"},{\"fieldName\":\"lastName\",\"fieldValue\":\"#lastName#\"}]},\"response\":{\"fields\":[{\"fieldName\":\"success\",\"fieldValue\":\"true\"}]}}}}";
    InstructionFlow instruction;

    @BeforeEach
    public void init() {
        instruction = utilities.jsonToObject(body, InstructionFlow.class);
    }

    @Test
    public void generatorURL() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange("/generator", HttpMethod.POST, new HttpEntity(instruction, httpHeaders), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}