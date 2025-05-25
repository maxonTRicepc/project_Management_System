package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.HistoryRepository;
import com.example.project_Management_System.model.History;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {
    @Autowired
    private final HistoryRepository historyRepository;

    public @NonNull History getById(@NonNull Long historyId){
        log.info("[HistoryService] Попытка найти историю с ID {}", historyId);
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException("[HistoryService] История с ID " + historyId + " не найдена"));
    }

    @Transactional
    public @NonNull History saveHistory(@NonNull History history){
        log.info("[HistoryService] Сохранение истории {}", history);
        return historyRepository.save(history);
    }

    public @NonNull History createHistory(@NonNull History history){
        log.info("[HistoryService] Создание истории");
        history.setChangeDate(new Date());

        return saveHistory(history);
    }
}
