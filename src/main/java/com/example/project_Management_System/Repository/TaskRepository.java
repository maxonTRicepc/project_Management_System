package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.Project;
import com.example.project_Management_System.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllTaskByProject(Project project);
}
