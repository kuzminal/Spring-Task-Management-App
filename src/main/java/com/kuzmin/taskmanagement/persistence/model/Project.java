package com.kuzmin.taskmanagement.persistence.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

@Data
public class Project {
    private Long id;
    private String name;
    private LocalDate dateCreated;

    public Project(Long id, String name, LocalDate dateCreated) {
        if (Objects.isNull(id)) {
            id = new Random().nextLong();
        }
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Project(Project project) {
        this(project.getId(), project.getName(), project.getDateCreated());
    }

}
