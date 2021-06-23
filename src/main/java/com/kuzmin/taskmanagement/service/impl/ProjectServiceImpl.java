package com.kuzmin.taskmanagement.service.impl;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.repository.IProjectRepository;
import com.kuzmin.taskmanagement.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Cacheable("projects")
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    @CachePut("projects")
    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
