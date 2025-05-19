package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    private Task testTask;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Создание зависимостей
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        testUser = new User();
        testUser.setLogin("hist_user");
        testUser.setPassword("pass");
        testUser.setEmail("hist@test.com");
        testUser.setRole(role);
        userRepository.save(testUser);

        Project project = new Project();
        project.setName("Hist Project");
        projectRepository.save(project);

        Status status = new Status();
        status.setName("NEW");
        statusRepository.save(status);

        Priority priority = new Priority();
        priority.setName("MEDIUM");
        priorityRepository.save(priority);

        testTask = new Task();
        testTask.setName("Hist Task");
        testTask.setStatus(status);
        testTask.setPriority(priority);
        testTask.setProject(project);
        taskRepository.save(testTask);
    }

    @Test
    void testHistoryCRUD() {
        History history = new History();
        history.setTask(testTask);
        history.setChangedBy(testUser);
        history.setChangeDate(new Date());
        history.setChangeDescription("Initial creation");

        // Create
        History saved = historyRepository.save(history);
        assertThat(saved.getId()).isNotNull();

        // Read
        Optional<History> found = historyRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getChangeDescription()).isEqualTo("Initial creation");

        // Update
        saved.setChangeDescription("Updated description");
        historyRepository.save(saved);
        assertThat(historyRepository.findById(saved.getId()).get().getChangeDescription())
            .isEqualTo("Updated description");

        // Delete
        historyRepository.delete(saved);
        assertThat(historyRepository.findById(saved.getId())).isEmpty();
    }
}