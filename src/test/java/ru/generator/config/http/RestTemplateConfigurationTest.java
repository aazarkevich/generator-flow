package ru.generator.config.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateConfigurationTest {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void restTemplateTest() {
        assertNotNull("restTemplateTest", restTemplate);
    }
}