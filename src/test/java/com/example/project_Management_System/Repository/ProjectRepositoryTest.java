package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Project;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testFindProjectByName() {
        Project project = new Project();
        project.setName("Awesome Project");
        projectRepository.save(project);

        List<Project> found = projectRepository.findByName("Awesome Project");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("Awesome Project");
    }
}