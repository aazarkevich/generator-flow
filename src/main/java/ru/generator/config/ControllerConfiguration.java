package ru.generator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import ru.generator.model.InstructionFlow;
import ru.generator.service.GeneratorIntegrationFlow;

import java.util.UUID;

@Slf4j
@Configuration
@EnableIntegration
public class ControllerConfiguration {

    @Bean
    public IntegrationFlow requestFlow(GeneratorIntegrationFlow generator) {
        return IntegrationFlow.from("generatorChannel")
                .handle(InstructionFlow.class, (instructionFlow, messageHeaders) -> {
                    log.debug("REQUEST: " + instructionFlow);
                    UUID flowHttp = generator.createFlowHttp(instructionFlow);
                    return flowHttp.toString();
                })
                .get();
    }

    @Bean
    public IntegrationFlow generator() {
        return IntegrationFlow.from(Http.inboundGateway("/generator")
                        .requestMapping(m -> m.methods(HttpMethod.POST))
                        .requestPayloadType(String.class))
                .channel("generatorChannel")
                .get();
    }

}
