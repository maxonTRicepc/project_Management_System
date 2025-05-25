package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Project;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testFindProjectByName() {
        Project project = new Project();
        project.setName("Awesome Project");
        projectRepository.save(project);

        Optional<Project> found = projectRepository.findByName("Awesome Project");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Awesome Project");
    }
}