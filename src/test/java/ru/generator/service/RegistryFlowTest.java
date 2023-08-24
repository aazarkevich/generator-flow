package ru.generator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistryFlowTest {
    @Autowired
    RegistryFlow registryFlow;

    @Test
    public void registryTest() {
        MessageChannel channel = new QueueChannel();
        IntegrationFlow flow = IntegrationFlow.from(channel).handle(message -> {
        }).get();
        IntegrationFlow subscriber = IntegrationFlow.from(channel).handle(message -> {
            System.out.println("test");
        }).get();

        String flowId = UUID.randomUUID().toString();
        boolean registry = registryFlow.registry(flowId, flow, subscriber);
        assertTrue(registry);
    }
}
