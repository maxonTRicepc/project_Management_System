package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryRepositoryTest {
    @Test
    void testHistoryCreation() {
        Role role = new Role();
        role.setId(1L);
        role.setName("User");
        User user = new User();
        user.setId(1L);
        user.setLogin("user1");
        user.setPassword("pass");
        user.setEmail("user@test.com");
        user.setRole(role);
        Status status = new Status();
        status.setId(1L);
        status.setName("TODO");
        Priority priority = new Priority();
        priority.setId(1L);
        priority.setName("HIGH");
        Task task = new Task();
        task.setId(1L);
        task.setName("Task");
        task.setDescription("Desc");
        task.setStatus(status);
        task.setPriority(priority);
        task.setProject(null);
        task.setAssignedUsers(List.of(user));
        Date now = new Date();

        History history = new History();
        history.setId(1L);
        history.setTask(task);
        history.setChangedBy(user);
        history.setChangeDate(now);
        history.setChangeDescription("Status changed");

        assertEquals(1L, history.getId());
        assertEquals(task, history.getTask());
        assertEquals(user, history.getChangedBy());
        assertEquals(now, history.getChangeDate());
        assertEquals("Status changed", history.getChangeDescription());
    }

    @Test
    void testHistoryUpdate() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        User user = new User();
        user.setId(1L);
        user.setLogin("user1");
        user.setPassword("pass");
        user.setEmail("user@test.com");
        user.setRole(role);
        Task task = new Task();
        Date oldDate = new Date(System.currentTimeMillis() - 10000);
        Date newDate = new Date();

        History history = new History();
        history.setId(1L);
        history.setTask(task);
        history.setChangedBy(user);
        history.setChangeDate(oldDate);
        history.setChangeDescription("Old change");

        history.setChangeDate(newDate);
        history.setChangeDescription("New change");

        assertEquals(newDate, history.getChangeDate());
        assertEquals("New change", history.getChangeDescription());
    }


    @Test
    void testHistoryDelete() {
        History history = new History();
        List<History> histories = new ArrayList<>();
        histories.add(history);

        histories.remove(history);

        assertTrue(histories.isEmpty());
    }
}
