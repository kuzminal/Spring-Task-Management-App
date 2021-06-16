package com.kuzmin.taskmanagement.persistence.repository;

import com.kuzmin.taskmanagement.persistence.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {
    Optional<Project> findByName(String name);
    List<Project> findByDateCreatedBetween(LocalDate start, LocalDate end);
}
