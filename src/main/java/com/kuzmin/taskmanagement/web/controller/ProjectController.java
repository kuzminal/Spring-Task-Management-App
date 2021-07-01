package com.kuzmin.taskmanagement.web.controller;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.service.IProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.kuzmin.taskmanagement.web.dto.ProjectDto;
import com.kuzmin.taskmanagement.web.dto.TaskDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
    private IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getProjects(Model model) {
        Iterable<Project> projects = projectService.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        projects.forEach(p -> projectDtos.add(convertToDto(p)));
        model.addAttribute("projects", projectDtos);
        return "projects";
    }

    private ProjectDto convertToDto(Project entity) {
        ProjectDto dto = new ProjectDto(entity.getId(), entity.getName(), entity.getDateCreated());
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
