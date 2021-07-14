package com.kuzmin.taskmanagement.api.controller;

import com.kuzmin.taskmanagement.dto.DTOConverter;
import com.kuzmin.taskmanagement.dto.ProjectDto;
import com.kuzmin.taskmanagement.dto.TaskDto;
import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/projects")
public class ProjectRestController {
    private IProjectService projectService;
    private final DTOConverter dtoConverter = new DTOConverter();

    public ProjectRestController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = "application/json")
    public List<ProjectDto> getProjects(@RequestParam Optional<String> name) {
        Iterable<Project> projects;
        if (name.isPresent()) {
            projects = projectService.findByName(name.get());
        } else {
            projects = projectService.findAll();
        }
        List<ProjectDto> projectDtos = new ArrayList<>();
        projects.forEach(p -> projectDtos.add(dtoConverter.convertToDto(p)));
        return projectDtos;
    }

    @PostMapping(headers = "accept=application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto addProject(@RequestBody ProjectDto project) {
        Project proj = projectService.save(dtoConverter.convertToEntity(project));
        return dtoConverter.convertToDto(proj);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ProjectDto getProject(@PathVariable Long id) {
        Project entity = projectService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Project not found"));
        return dtoConverter.convertToDto(entity);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable("id") Long id, @RequestBody ProjectDto updatedProject) {
        Project projectEntity = dtoConverter.convertToEntity(updatedProject);
        return dtoConverter.convertToDto(this.projectService.save(projectEntity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable("id") Long id) {
        projectService.delete(id);
    }

    @PostMapping(value = "{id}/save-task", headers = "accept=application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto saveTasks(@RequestBody TaskDto task, @PathVariable Long id) {
        Project project = projectService.findById(id)
                .orElse(new Project());
        project = projectService.addTask(project, dtoConverter.convertTaskToEntity(task));
        return dtoConverter.convertToDto(project);
    }
}
