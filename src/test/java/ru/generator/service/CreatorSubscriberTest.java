package ru.generator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;
import ru.generator.model.InstructionFlow;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreatorSubscriberTest {
    @Autowired
    CreatorSubscriber creatorSubscriber;

    @Test
    public void subscriberTest() {
        InstructionFlow.Flow.Configuration config = Mockito.mock(InstructionFlow.Flow.Configuration.class);
        MessageChannel messageChannel = new QueueChannel();
        IntegrationFlow flow = creatorSubscriber.getSubscriberHttp(messageChannel, config);
        assertNotNull("subscriberTest", flow);
    }
}
