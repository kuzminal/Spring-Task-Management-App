package com.kuzmin.taskmanagement.web.controller;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.kuzmin.taskmanagement.web.dto.ProjectDto;
import com.kuzmin.taskmanagement.web.dto.TaskDto;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
    private IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "/{id}")
    public ProjectDto findOne(@PathVariable Long id) {
        Project entity = projectService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(entity);
    }

    @PostMapping
    public void create(@RequestBody ProjectDto newProject) {
        Project entity = convertToEntity(newProject);
        entity.setDateCreated(LocalDate.now());
        this.projectService.save(entity);
    }

    private ProjectDto convertToDto(Project entity) {
        ProjectDto dto = new ProjectDto(entity.getId(), entity.getName());
        dto.setTasks(entity.getTasks()
                .stream()
                .map(this::convertTaskToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    private Project convertToEntity(ProjectDto dto) {
        Project project = new Project();
        if (!ObjectUtils.isEmpty(dto)) {
            project.setId(dto.getId());
            project.setName(dto.getName());
            project.setTasks((dto.getTasks()
                    .stream()
                    .map(this::convertTaskToEntity)
                    .collect(Collectors.toSet())));
        }
        return project;
    }

    protected TaskDto convertTaskToDto(Task entity) {
        TaskDto dto = new TaskDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getDateCreated(), entity.getDueDate(), entity.getStatus());
        return dto;
    }

    protected Task convertTaskToEntity(TaskDto dto) {
        Task task = new Task();
        if (!ObjectUtils.isEmpty(dto)) {
            task.setId(dto.getId());
            task.setDescription(dto.getDescription());
            task.setName(dto.getName());
            task.setDateCreated(dto.getDateCreated());
            task.setDueDate(dto.getDueDate());
            task.setStatus(dto.getStatus());
        }
        return task;
    }
}
