package ru.generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest
class ApplicationTest {
    @Autowired
    ApplicationContext context;

    @Test
    void ApplicationContext() {
        assertNotNull("ApplicationContextNotNull", context);
    }

}
