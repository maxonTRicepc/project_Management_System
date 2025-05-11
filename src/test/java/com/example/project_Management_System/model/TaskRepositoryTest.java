package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {
    @Test
    void testTaskCreation() {
        Status status = new Status();
        status.setId(1L);
        status.setName("IN_PROGRESS");
        Priority priority = new Priority();
        priority.setId(1L);
        priority.setName("HIGH");
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Description");
        Role role = new Role();
        role.setId(1L);
        role.setName("User");
        User user = new User();
        user.setId(1L);
        user.setLogin("user1");
        user.setPassword("pass");
        user.setEmail("user@test.com");
        user.setRole(role);


        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Task Description");
        task.setStatus(status);
        task.setPriority(priority);
        task.setProject(project);
        task.setAssignedUsers(List.of(user));

        assertEquals(1L, task.getId());
        assertEquals("Test Task", task.getName());
        assertEquals("Task Description", task.getDescription());
        assertEquals(status, task.getStatus());
        assertEquals(priority, task.getPriority());
        assertEquals(project, task.getProject());
        assertEquals(1, task.getAssignedUsers().size());
    }

    @Test
    void testTaskDeleteFromProject() {
        Project project = new Project();
        Status status = new Status();
        status.setId(1L);
        status.setName("IN_PROGRESS");
        Priority priority = new Priority();
        priority.setId(1L);
        priority.setName("HIGH");
        Role role = new Role();
        role.setId(1L);
        role.setName("User");
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Task Description");
        task.setStatus(status);
        task.setPriority(priority);
        task.setProject(project);
        task.setAssignedUsers(List.of());
        project.setTasks(new ArrayList<>(List.of(task)));

        // "Удаление" задачи из проекта
        project.getTasks().remove(task);

        assertTrue(project.getTasks().isEmpty());
    }
}
