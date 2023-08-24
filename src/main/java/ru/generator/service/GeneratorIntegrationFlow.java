package ru.generator.service;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.generator.enums.HttpMethods;
import ru.generator.model.InstructionFlow;

import java.util.UUID;

@Service
public class GeneratorIntegrationFlow {
    private final CreatorSubscriber subscriber;
    private final RegistryFlow registry;

    public GeneratorIntegrationFlow(CreatorSubscriber subscriber, RegistryFlow registry) {
        this.subscriber = subscriber;
        this.registry = registry;
    }

    public UUID createFlowHttp(InstructionFlow instruction) {
        MessageChannel channel = new DirectChannel();
        IntegrationFlow subscriberHttp = subscriber.getSubscriberHttp(channel, instruction.getFlows().getConfiguration());
        StandardIntegrationFlow controller = IntegrationFlow.from(Http.inboundGateway(instruction.getFlows().getUrl())
                        .requestMapping(m -> m.methods(HttpMethods.getHttpMethod(instruction.getFlows().getConfiguration().getRequest().getMethod())))
                        .requestPayloadType(String.class))
                .channel(channel)
                .get();
        UUID flowUUID = UUID.nameUUIDFromBytes(instruction.getName().getBytes());
        registry.registry(flowUUID.toString(), controller, subscriberHttp);
        return flowUUID;
    }
}
