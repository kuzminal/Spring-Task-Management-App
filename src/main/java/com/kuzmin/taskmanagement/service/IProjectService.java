package com.kuzmin.taskmanagement.service;

import com.kuzmin.taskmanagement.exception.TaskNotSavedException;
import com.kuzmin.taskmanagement.persistence.model.Project;

import java.util.Optional;

public interface IProjectService {
    Iterable<Project> findAll();

    public Optional<Project> findById(Long id);

    public Project save(Project project);

    void createProjectWithTasks() throws TaskNotSavedException;
}
