package com.example.project_Management_System.Repository;

import com.example.project_Management_System.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByTaskId(Long taskId);

}
