package com.kuzmin.taskmanagement.persistence.repository;

import com.kuzmin.taskmanagement.persistence.model.Project;

import java.util.Optional;

public interface IProjectRepository {
    Optional<Project> findById(Long id);

    Project save(Project project);
}
