package com.kuzmin.taskmanagement.service;

import com.kuzmin.taskmanagement.config.TestConfig;
import com.kuzmin.taskmanagement.persistence.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(value = TestConfig.class)
public class ProjectServiceIntegrationTest {
    @Autowired
    private IProjectService projectService;

    @Test
    public void whenSavingProject_thenOK() {
        Project savedProject = projectService.save(new Project("name", LocalDate.now()));
        assertNotNull(savedProject);
    }
}
