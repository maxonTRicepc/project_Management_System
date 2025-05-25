package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Priority;
import com.example.project_Management_System.model.Project;
import com.example.project_Management_System.model.Status;
import com.example.project_Management_System.model.Task;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    private Project testProject;
    private Status testStatus;
    private Priority testPriority;

    @BeforeEach
    void setUp() {
        // Создаем зависимости
        testProject = new Project();
        testProject.setName("Test Project");
        projectRepository.save(testProject);

        testStatus = new Status();
        testStatus.setName("IN_PROGRESS");
        statusRepository.save(testStatus);

        testPriority = new Priority();
        testPriority.setName("HIGH");
        priorityRepository.save(testPriority);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setStatus(testStatus);
        task.setPriority(testPriority);
        task.setProject(testProject);

        Task savedTask = taskRepository.save(task);
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getProject().getName()).isEqualTo("Test Project");
    }
}