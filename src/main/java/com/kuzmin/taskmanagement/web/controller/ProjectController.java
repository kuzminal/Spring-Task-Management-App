package com.kuzmin.taskmanagement.web.controller;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.model.TaskStatus;
import com.kuzmin.taskmanagement.service.IProjectService;
import com.kuzmin.taskmanagement.web.dto.TaskListDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kuzmin.taskmanagement.web.dto.ProjectDto;
import com.kuzmin.taskmanagement.web.dto.TaskDto;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("/new")
    public String newProject(Model model) {
        model.addAttribute("project", new ProjectDto());
        return "new-project";
    }

    @PostMapping
    public String addProject(@Valid @ModelAttribute("project") ProjectDto project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-project";
        }
        projectService.save(convertToEntity(project));
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String getProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id)
                .get();
        model.addAttribute("project", convertToDto(project));
        return "project";
    }

    @GetMapping("/{id}/add-task")
    public String getProjectEditPage(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id)
                .orElse(new Project());
        model.addAttribute("project", project);
        model.addAttribute("task", new TaskDto());
        return "add-task";
    }

    @PostMapping("{id}/save-task")
    //TODO Нужно победить валидацию
    public String saveTasks(@ModelAttribute("task") TaskDto task, @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/" + id + "/add-task";
        }
        Project project = projectService.findById(id)
                .orElse(new Project());
        projectService.addTask(project, convertTaskToEntity(task));
        return "redirect:/projects/" + project.getId();
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
            project.setDateCreated(dto.getDateCreated() == null ? LocalDate.now() : dto.getDateCreated());
            if (dto.getTasks() != null) {
                project.setTasks((dto.getTasks()
                        .stream()
                        .map(this::convertTaskToEntity)
                        .collect(Collectors.toSet())));
            }
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
            if (dto.getDateCreated() != null) {
                task.setDateCreated(dto.getDateCreated());
            } else {
                task.setDateCreated(LocalDate.now());
            }
            task.setDueDate(dto.getDueDate());
            if (dto.getStatus() != null) {
                task.setStatus(dto.getStatus());
            } else {
                task.setStatus(TaskStatus.TO_DO);
            }
        }
        return task;
    }
}
