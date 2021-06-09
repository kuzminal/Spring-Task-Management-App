package com.kuzmin.taskmanagement.properties;

import com.kuzmin.taskmanagement.TaskManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(value = TaskManagementApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TestPropertySourceTest {
    @Value("${testproperty}")
    private String testproperty;

    @Test
    public void whenTestPropertySource_thenValuesRetreived() {
        assertEquals("Test Property Value", testproperty);
    }
}
