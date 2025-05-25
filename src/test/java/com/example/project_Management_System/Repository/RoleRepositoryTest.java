package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testRoleCRUD() {
        Role role = new Role();
        role.setName("ROLE_MODERATOR");

        // Create
        Role saved = roleRepository.save(role);
        assertThat(saved.getId()).isNotNull();

        // Read
        Optional<Role> found = roleRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getAuthority()).isEqualTo("ROLE_MODERATOR");

        // Update
        saved.setName("MODERATOR");
        roleRepository.save(saved);
        assertThat(roleRepository.findById(saved.getId()).get().getAuthority())
            .isEqualTo("ROLE_MODERATOR");

        // Delete
        roleRepository.delete(saved);
        assertThat(roleRepository.findById(saved.getId())).isEmpty();
    }
}