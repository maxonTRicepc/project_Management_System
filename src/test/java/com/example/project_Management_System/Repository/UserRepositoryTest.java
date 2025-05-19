package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Role;
import com.example.project_Management_System.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role testRole;

    @BeforeEach
    void setUp() {
        // Создаем тестовую роль
        testRole = new Role();
        testRole.setName("ROLE_USER");
        roleRepository.save(testRole);
    }

    @Test
    void testUserCRUD(){
        User testUser = new User();
        testUser.setLogin("testuser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser.setRole(testRole);

        // Create
        User saved = userRepository.save(testUser);
        assertThat(saved.getId()).isNotNull();

        // Read
        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getLogin()).isEqualTo("testuser");

        // Update
        saved.setEmail("updated@example.com");
        userRepository.save(saved);

        Optional<User> updatedUser = userRepository.findById(saved.getId());
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getEmail()).isEqualTo("updated@example.com");

        // Delete
        userRepository.deleteById(saved.getId());

        Optional<User> deletedUser = userRepository.findById(saved.getId());
        assertThat(deletedUser).isEmpty();
    }
}