package com.kuzmin.taskmanagement.repository;

import com.kuzmin.taskmanagement.persistence.model.Task;
import com.kuzmin.taskmanagement.persistence.repository.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskRepositoryIntegrationTest {
    @Autowired
    ITaskRepository taskRepository;

    @Test
    public void givenProjectCreated_thenFindByTaskNameMatchesSuccess() {
        Task task1 = new Task("Low Priority Task", "Low Priority Task", LocalDate.now(), LocalDate.now());
        Task task2 = new Task("Low Priority Task", "Low Priority Task", LocalDate.now(), LocalDate.now());
        Task task3 = new Task("High Priority Task", "High Priority Task", LocalDate.now(), LocalDate.now());
        Task task4 = new Task("High Priority Task", "High Priority Task", LocalDate.now(), LocalDate.now());

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);

        List<Task> retreivedTasks = taskRepository.findByNameMatches("High");
        assertThat(retreivedTasks).contains(task3, task4);
    }
}
