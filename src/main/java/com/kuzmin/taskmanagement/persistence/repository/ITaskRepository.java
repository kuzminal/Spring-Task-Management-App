package com.kuzmin.taskmanagement.persistence.repository;

import com.kuzmin.taskmanagement.persistence.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITaskRepository extends CrudRepository<Task, Long> {
    @Query("select t from Task t where t.name like %?1%")
    List<Task> findByNameMatches(String name);
}