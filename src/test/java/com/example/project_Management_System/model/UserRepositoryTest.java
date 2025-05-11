package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserRepositoryTest {

    @Test
    void testUserCreation() {
        Role role = new Role();
        role.setId(1L);
        role.setName("User");
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setRole(role);

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(role, user.getRole());
    }

    @Test
    void testUserDetailsMethods() {
        Role role = new Role();
        role.setId(1L);
        role.setName("User");
        User user = new User();
        user.setLogin("testuser");
        user.setRole(role);

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertEquals("testuser", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    void testUserDelete() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        User user = new User();
        user.setId(1L);
        user.setLogin("test");
        user.setPassword("pass");
        user.setEmail("test@test.com");
        user.setRole(role);
        List<User> users = new ArrayList<>();
        users.add(user);

        // "Удаление" - в нашем случае просто удаление из коллекции
        users.remove(user);

        assertTrue(users.isEmpty());
    }
}
