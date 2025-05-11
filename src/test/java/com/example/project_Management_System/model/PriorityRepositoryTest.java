package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityRepositoryTest {
    @Test
    void testPriorityCreation() {
        Priority priority = new Priority();
        priority.setId(1L);
        priority.setName("HIGH");

        assertEquals(1L, priority.getId());
        assertEquals("HIGH", priority.getName());
    }

    @Test
    void testPriorityHashCode() {
        Priority p1 = new Priority();
        p1.setId(1L);
        p1.setName("HIGH");
        Priority p2 = new Priority();
        p2.setId(1L);
        p2.setName("HIGH");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testPriorityDelete() {
        Priority priority = new Priority();
        priority.setId(1L);
        priority.setName("HIGH");
        List<Priority> priorities = new ArrayList<>();
        priorities.add(priority);

        priorities.remove(priority);

        assertTrue(priorities.isEmpty());
    }
}