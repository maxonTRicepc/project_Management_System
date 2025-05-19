package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Status;
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
class StatusRepositoryTest {

    @Autowired
    private StatusRepository statusRepository;

    @Test
    void testStatusCRUD() {
        Status status = new Status();
        status.setName("REOPENED");

        // Create
        Status saved = statusRepository.save(status);
        assertThat(saved.getId()).isNotNull();

        // Read
        Optional<Status> found = statusRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("REOPENED");

        // Update
        saved.setName("CLOSED");
        statusRepository.save(saved);
        assertThat(statusRepository.findById(saved.getId()).get().getName())
            .isEqualTo("CLOSED");

        // Delete
        statusRepository.delete(saved);
        assertThat(statusRepository.findById(saved.getId())).isEmpty();
    }
}