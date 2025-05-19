package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Priority;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PriorityRepositoryTest {

    @Autowired
    private PriorityRepository priorityRepository;

    @Test
    void testPriorityCRUD() {
        Priority priority = new Priority();
        priority.setName("URGENT");

        // Create
        Priority saved = priorityRepository.save(priority);
        assertThat(saved.getId()).isNotNull();

        // Read
        Optional<Priority> found = priorityRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("URGENT");

        // Update
        saved.setName("CRITICAL");
        priorityRepository.save(saved);
        assertThat(priorityRepository.findById(saved.getId()).get().getName())
            .isEqualTo("CRITICAL");

        // Delete
        priorityRepository.delete(saved);
        assertThat(priorityRepository.findById(saved.getId())).isEmpty();
    }
}