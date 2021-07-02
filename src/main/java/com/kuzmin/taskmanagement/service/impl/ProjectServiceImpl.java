package com.kuzmin.taskmanagement.service.impl;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.repository.IProjectRepository;
import com.kuzmin.taskmanagement.service.IProjectService;
import com.kuzmin.taskmanagement.service.ITaskService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements IProjectService {
    private final IProjectRepository projectRepository;
    private final ITaskService taskService;

    public ProjectServiceImpl(IProjectRepository projectRepository, ITaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    @Override
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Cacheable(value = "projects")
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    @CachePut(value = "projects")
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project addTask(Project project, Task task) {
        project.getTasks()
                .add(task);
        projectRepository.save(project);

        return project;
    }
}
