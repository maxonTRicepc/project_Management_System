package com.example.project_Management_System.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RoleRepositoryTest {
    @Test
    void testRoleCreation() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
        assertEquals("ROLE_ADMIN", role.getAuthority());
    }

    @Test
    void testRoleAsAuthority() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        assertTrue(role instanceof GrantedAuthority);
    }

    @Test
    void testRoleDelete() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        roles.remove(role);

        assertTrue(roles.isEmpty());
    }
}
