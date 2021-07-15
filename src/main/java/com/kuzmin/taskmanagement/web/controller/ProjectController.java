package com.kuzmin.taskmanagement.web.controller;

import com.kuzmin.taskmanagement.dto.DTOConverter;
import com.kuzmin.taskmanagement.events.ProjectCreatedEvent;
import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kuzmin.taskmanagement.dto.ProjectDto;
import com.kuzmin.taskmanagement.dto.TaskDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
    private IProjectService projectService;
    private final DTOConverter dtoConverter = new DTOConverter();
    @Autowired
    private ApplicationEventPublisher publisher;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getProjects(Model model) {
        Iterable<Project> projects = projectService.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        projects.forEach(p -> projectDtos.add(dtoConverter.convertToDto(p)));
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
        Project newProject = projectService.save(dtoConverter.convertToEntity(project));
        publisher.publishEvent(new ProjectCreatedEvent(newProject.getId()));
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String getProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id)
                .get();
        model.addAttribute("project", dtoConverter.convertToDto(project));
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
        projectService.addTask(project, dtoConverter.convertTaskToEntity(task));
        return "redirect:/projects/" + project.getId();
    }
}
