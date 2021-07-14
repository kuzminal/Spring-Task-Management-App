package com.kuzmin.taskmanagement.repository;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.repository.IProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootTest
public class ProjectRepositoryIntegrationTest {
    @Autowired
    private IProjectRepository projectRepository;

    @Test
    public void whenSavingNewProject_thenSuccess() {
        Project newProject = new Project(randomAlphabetic(6), LocalDate.now());

        assertNotNull(projectRepository.save(newProject));
    }

    @Test
    public void givenProject_whenFindById_thenSuccess() {
        Project newProject = new Project(randomAlphabetic(6), LocalDate.now());
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertEquals(retrievedProject.get(), newProject);
    }

    @Test
    public void givenProjectCreated_whenFindByName_thenSuccess() {
        Project newProject = new Project(randomAlphabetic(6), LocalDate.now());
        Iterable<Project> newProjects = Arrays.asList(newProject);
        projectRepository.save(newProject);

        Iterable<Project> retreivedProject = projectRepository.findByName(newProject.getName());
        assertEquals(retreivedProject, newProjects);
    }

    @Test
    public void givenProjectCreated_whenFindByDateCreatedBetween_thenSuccess() {
        Project oldProject = new Project(randomAlphabetic(6), LocalDate.now().minusYears(1));
        projectRepository.save(oldProject);

        Project newProject = new Project(randomAlphabetic(6), LocalDate.now());
        projectRepository.save(newProject);

        Project newProject2 = new Project(randomAlphabetic(6), LocalDate.now());
        projectRepository.save(newProject2);

        List<Project> retreivedProjects = projectRepository.findByDateCreatedBetween(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertThat(retreivedProjects, hasItems(newProject, newProject2));
    }
}
