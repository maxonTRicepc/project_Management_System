package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.StatusRepository;
import com.example.project_Management_System.model.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {
    @Autowired
    private final StatusRepository statusRepository;

    @Transactional
    public Status saveStatus(Status status){
        log.info("[StatusService] Сохранение статуса {}", status);
        return statusRepository.save(status);
    }

    public Status createStatus(Status status) {
        log.info("[StatusService] Создание статуса {}", status);
        if (status.getName().isEmpty()){
            throw new NullPointerException("[StatusService] Статус не может быть пустым");
        }

        return saveStatus(status);
    }

    public @NonNull Status getById(@NonNull Long statusId){
        log.info("[StatusService] Найти статус по ID {}", statusId);
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("[StatusService] Статус с ID " + statusId + " не найден"));
    }

    public @NonNull Status getByName(@NonNull String statusName){
        log.info("[StatusService] Найти статус с именем {}", statusName);
        return statusRepository.findByName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("[StatusService] Статус с именем " + statusName + " не найден"));
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}