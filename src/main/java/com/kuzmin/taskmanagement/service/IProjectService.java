package com.kuzmin.taskmanagement.service;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;

import java.util.Optional;

public interface IProjectService {
    Iterable<Project> findAll();

    Optional<Project> findById(Long id);

    Iterable<Project> findByName(String name);

    Project save(Project project);

    void delete(Long id);

    Project addTask(Project project, Task tasks);


}
