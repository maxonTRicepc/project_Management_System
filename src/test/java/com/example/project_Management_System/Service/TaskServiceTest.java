package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.TaskRepository;
import com.example.project_Management_System.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserService userService;
    @Mock
    private StatusService statusService;
    @Mock
    private PriorityService priorityService;
    @Mock
    private HistoryService historyService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask() {
        // Arrange
        User creator = new User();
        creator.setId(1L);

        Status status = new Status();
        status.setName("Open");

        Priority priority = new Priority();
        priority.setName("High");

        Task task = new Task();
        task.setName("Test task");
        task.setDescription("Test description");
        task.setStatus(status);
        task.setPriority(priority);

        when(userService.getUserById(1L)).thenReturn(creator);
        when(statusService.getByName("Open")).thenReturn(status);
        when(priorityService.getByName("High")).thenReturn(priority);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task saved = invocation.getArgument(0);
            saved.setId(1L); // ← Присваиваем ID вручную
            return saved;
        });
        when(historyService.createHistory(any(History.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        taskService.createTask(task, 1L);

        // Assert
        assertNotNull(task.getId());
        assertEquals(1, task.getAssignedUsers().size());
        verify(taskRepository).save(task);
        verify(historyService).createHistory(any(History.class));
    }
}