package com.kuzmin.taskmanagement.service;

import com.kuzmin.taskmanagement.exception.TaskNotSavedException;
import com.kuzmin.taskmanagement.persistence.model.Task;

public interface ITaskService {
    Iterable<Task> findAll();

    Task save(Task task) throws TaskNotSavedException;
}
