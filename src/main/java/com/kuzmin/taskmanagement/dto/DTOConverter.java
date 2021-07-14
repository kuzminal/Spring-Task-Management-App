package com.kuzmin.taskmanagement.dto;

import com.kuzmin.taskmanagement.persistence.model.Project;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.model.TaskStatus;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class DTOConverter {
    public ProjectDto convertToDto(Project entity) {
        ProjectDto dto = new ProjectDto(entity.getId(), entity.getName(), entity.getDateCreated());
        dto.setTasks(entity.getTasks()
                .stream()
                .map(this::convertTaskToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Project convertToEntity(ProjectDto dto) {
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

    public TaskDto convertTaskToDto(Task entity) {
        TaskDto dto = new TaskDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getDateCreated(), entity.getDueDate(), entity.getStatus());
        return dto;
    }

    public Task convertTaskToEntity(TaskDto dto) {
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
