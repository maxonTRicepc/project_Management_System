package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.PriorityRepository;
import com.example.project_Management_System.model.Priority;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriorityService {
    @Autowired
    private final PriorityRepository priorityRepository;

    public Priority getById(@NonNull Long priorityId){
        log.info("[PriorityService] Попытка найти приоритет с ID {}", priorityId);
        return priorityRepository.findById(priorityId)
                .orElseThrow(() -> new EntityNotFoundException("[PriorityService] Приоритет с ID " + priorityId + " не найден"));
    }

    public Priority getByName(@NonNull String priorityName){
        log.info("[PriorityService] Попытка найти приоритет с именем {}", priorityName);
        return priorityRepository.findByName(priorityName)
                .orElseThrow(() -> new EntityNotFoundException("[PriorityService] Приоритет с именем " + priorityName + " не найден"));
    }

    @Transactional
    public Priority savePriority(@NonNull Priority priority){
        log.info("[PriorityService] Сохранение приоритета {}", priority);
        return priorityRepository.save(priority);
    }

    public Priority createPriority(@NonNull Priority priority){
        log.info("[PriorityService] Создание приоритета");
        return savePriority(priority);
    }
}
