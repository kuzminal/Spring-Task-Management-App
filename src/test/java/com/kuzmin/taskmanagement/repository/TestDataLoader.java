package com.kuzmin.taskmanagement.repository;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.repository.IProjectRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.LocalDate;

@Component
public class TestDataLoader implements ApplicationContextAware {
    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
    }
}
