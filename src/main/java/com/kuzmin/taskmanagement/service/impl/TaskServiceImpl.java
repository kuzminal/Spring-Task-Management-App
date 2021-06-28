package com.kuzmin.taskmanagement.service.impl;

import com.kuzmin.taskmanagement.exception.TaskNotSavedException;
import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.repository.ITaskRepository;
import com.kuzmin.taskmanagement.service.ITaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;

    public TaskServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) throws TaskNotSavedException {
        throw new TaskNotSavedException("Unable to save task");
    }
}
