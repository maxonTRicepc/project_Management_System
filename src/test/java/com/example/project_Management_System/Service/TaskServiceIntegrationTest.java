package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.RoleRepository;
import com.example.project_Management_System.model.*;
import com.example.project_Management_System.Repository.HistoryRepository;
import com.example.project_Management_System.Repository.TaskRepository;
import com.example.project_Management_System.Repository.UserRepository;
import com.example.project_Management_System.Service.PriorityService;
import com.example.project_Management_System.Service.StatusService;
import com.example.project_Management_System.Service.TaskService;
import com.example.project_Management_System.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User testUser;
    private Status openStatus;
    private Priority highPriority;
    private Task testTask;

    @BeforeEach
    void setUp() {
        // Создание статуса
        openStatus = new Status();
        openStatus.setName("Open");
        openStatus = statusService.createStatus(openStatus);

        // Создание приоритета
        highPriority = new Priority();
        highPriority.setName("High");
        highPriority = priorityService.createPriority(highPriority);

        // Создание роли
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        // Создание пользователя
        testUser = new User();
        testUser.setLogin("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser = userService.createUser(testUser);

        // Подготовка задачи
        testTask = new Task();
        testTask.setName("Integration Test Task");
        testTask.setDescription("This is a test task for integration tests.");
        testTask.setStatus(openStatus);
        testTask.setPriority(highPriority);
        testTask.getAssignedUsers().add(testUser);
    }

    @Test
    @DisplayName("Создание задачи должно сохранять её в БД и создавать запись в истории")
    void testCreateTask_ShouldSaveTaskAndCreateHistory() {
        // Act
        taskService.createTask(testTask, testUser.getId());

        // Assert
        assertThat(testTask.getId()).isNotNull();

        List<History> histories = historyRepository.findByTaskId(testTask.getId());
        assertThat(histories).isNotEmpty();
        assertThat(histories.get(0).getChangeDescription()).contains("Создание");
    }

    @Test
    @DisplayName("Обновление задачи должно обновлять поля и добавлять запись в историю")
    void testUpdateTask_ShouldUpdateFieldsAndAddToHistory() {
        // Arrange
        taskService.createTask(testTask, testUser.getId());

        String updatedName = "Updated Task Name";
        String updatedDesc = "Updated description";

        Task updateData = new Task();
        updateData.setName(updatedName);
        updateData.setDescription(updatedDesc);

        // Act
        taskService.updateTask(testTask.getId(), updateData, testUser.getId());

        // Assert
        Task updatedTask = taskService.getById(testTask.getId());
        assertThat(updatedTask.getName()).isEqualTo(updatedName);
        assertThat(updatedTask.getDescription()).isEqualTo(updatedDesc);

        List<History> histories = historyRepository.findByTaskId(testTask.getId());
        assertThat(histories).hasSize(2); // 1 - create, 2 - update
        assertThat(histories.get(1).getChangeDescription()).contains("Изменение");
    }

    @Test
    @DisplayName("Удаление задачи должно удаляться и оставлять запись в истории")
    void testDeleteTask_ShouldRemoveFromDatabaseAndAddHistoryEntry() {
        // Arrange
        taskService.createTask(testTask, testUser.getId());

        // Act
        taskService.deleteById(testTask.getId());

        // Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.getById(testTask.getId()));

        List<History> histories = historyRepository.findByTaskId(testTask.getId());
        assertThat(histories).isNotEmpty();
    }

    @Test
    @DisplayName("Добавление пользователя к задаче должно добавиться и создаться запись в истории")
    void testAddUserToTask_ShouldAddUserAndUpdateHistory() {
        // Arrange
        taskService.createTask(testTask, testUser.getId());

        User newUser = new User();
        newUser.setLogin("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password");
        newUser = userService.createUser(newUser);

        // Act
        taskService.addUser(testTask.getId(), newUser.getId(), testUser.getId());

        // Assert
        Task updatedTask = taskService.getById(testTask.getId());
        assertThat(updatedTask.getAssignedUsers()).contains(newUser);

        List<History> histories = historyRepository.findByTaskId(testTask.getId());
        assertThat(histories).hasSize(2); // create + add user
        assertThat(histories.get(1).getChangeDescription()).contains("Добавление пользователя " + newUser.getLogin());
    }

    @Test
    @DisplayName("Удаление пользователя из задачи должно удалиться и создаться запись в истории")
    void testRemoveUserFromTask_ShouldRemoveUserAndUpdateHistory() {
        // Arrange
        taskService.createTask(testTask, testUser.getId());

        // Act
        taskService.removeUser(testTask.getId(), testUser.getId(), testUser.getId());

        // Assert
        Task updatedTask = taskService.getById(testTask.getId());
        assertThat(updatedTask.getAssignedUsers()).hasSize(0);

        List<History> histories = historyRepository.findByTaskId(testTask.getId());
        assertThat(histories).hasSize(2); // create + remove user
        assertThat(histories.get(1).getChangeDescription()).contains("Удаление пользователя");
    }

    @Test
    @DisplayName("Просмотр истории изменений должен возвращать все записи по задаче")
    void testGetHistoryForTask_ShouldReturnAllHistoryEntries() {
        // Arrange
        taskService.createTask(testTask, testUser.getId());

        taskService.updateTask(testTask.getId(), new Task() {{
            setName("New name");
        }}, testUser.getId());

        taskService.addUser(testTask.getId(), testUser.getId(), testUser.getId());

        // Act
        List<History> historyList = historyRepository.findByTaskId(testTask.getId());

        // Assert
        assertThat(historyList).hasSize(3); // create + update + add user
    }
}