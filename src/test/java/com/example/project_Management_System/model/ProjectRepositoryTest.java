package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProjectRepositoryTest {
    @Test
    void testProjectCreation() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Project Description");

        assertEquals(1L, project.getId());
        assertEquals("Test Project", project.getName());
        assertEquals("Project Description", project.getDescription());
    }

    @Test
    void testProjectWithTasks() {
        Project project = new Project();
        Status status1 = new Status();
        status1.setId(1L);
        status1.setName("TODO");
        Priority priority1 = new Priority();
        priority1.setId(1L);
        priority1.setName("HIGH");
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setDescription("Desc 1");
        task1.setStatus(status1);
        task1.setPriority(priority1);
        task1.setAssignedUsers(List.of());
        task1.setProject(project);

        Status status2 = new Status();
        status2.setId(2L);
        status2.setName("IN_PROGRESS");
        Priority priority2 = new Priority();
        priority2.setId(2L);
        priority2.setName("MEDIUM");
        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setDescription("Desc 2");
        task2.setStatus(status2);
        task2.setPriority(priority2);
        task2.setAssignedUsers(List.of());
        task2.setProject(project);

        project.setTasks(List.of(task1, task2));

        assertEquals(2, project.getTasks().size());
        assertEquals("Task 1", project.getTasks().get(0).getName());
        assertEquals("Task 2", project.getTasks().get(1).getName());
    }

    @Test
    void testProjectDelete() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project");
        project.setDescription("Desc");
        List<Project> projects = new ArrayList<>();
        projects.add(project);

        projects.remove(project);

        assertTrue(projects.isEmpty());
    }
}
