package com.kuzmin.taskmanagement.service.impl;

import com.kuzmin.taskmanagement.exception.TaskNotSavedException;
import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.repository.IProjectRepository;
import com.kuzmin.taskmanagement.service.IProjectService;
import com.kuzmin.taskmanagement.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements IProjectService {
    private final IProjectRepository projectRepository;
    private final ITaskService taskService;

    public ProjectServiceImpl(IProjectRepository projectRepository, ITaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    @Override
    @Cacheable(value = "projects")
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Cacheable("projects")
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    @CachePut(value = "projects", key = "#project.id")
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Transactional(rollbackOn = TaskNotSavedException.class)
    @Override
    public void createProjectWithTasks() throws TaskNotSavedException {
        Project project = new Project("Project 1", LocalDate.now());

        Project newProject = save(project);

        Task task1 = new Task("Task 1", "Project 1 Task 1", LocalDate.now(), LocalDate.now()
                .plusDays(7));

        taskService.save(task1);

        Set<Task> tasks = new HashSet<>();
        tasks.add(task1);

        newProject.setTasks(tasks);

        save(newProject);
    }
}
