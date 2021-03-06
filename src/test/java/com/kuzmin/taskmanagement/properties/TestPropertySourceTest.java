package com.kuzmin.taskmanagement.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TestPropertySourceTest {
    @Value("${testproperty}")
    private String testproperty;

    @Test
    public void whenTestPropertySource_thenValuesRetreived() {
        assertEquals("Test Property Value", testproperty);
    }
}
