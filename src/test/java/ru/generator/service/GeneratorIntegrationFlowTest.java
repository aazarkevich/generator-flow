package ru.generator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.generator.config.json.JsonUtilities;
import ru.generator.model.InstructionFlow;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorIntegrationFlowTest {
    @Mock
    GeneratorIntegrationFlow generator;
    @Autowired
    JsonUtilities utilities;
    String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Test
    public void createFlow() {
        InstructionFlow instruction = new InstructionFlow();
        Mockito.when(generator.createFlowHttp(instruction)).thenReturn(UUID.randomUUID());
        Pattern pattern = Pattern.compile(regex);
        UUID uuid = generator.createFlowHttp(instruction);
        assertTrue(pattern.matcher(uuid.toString()).matches());
    }

}
