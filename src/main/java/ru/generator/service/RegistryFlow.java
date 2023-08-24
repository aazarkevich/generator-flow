package ru.generator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegistryFlow {
    private final IntegrationFlowContext context;

    public RegistryFlow(IntegrationFlowContext context) {
        this.context = context;
    }

    public boolean registry(String flowId, IntegrationFlow flow, IntegrationFlow subscriber) {
        if (!context.getRegistry().containsKey(flowId)) {
            context.registration(flow).id(flowId)
                    .register();
            context.registration(subscriber).id(flowId + "-subscriber")
                    .register();
            log.debug("Registry new flow with id: " + flowId);
            return true;
        }
        return false;
    }

}
