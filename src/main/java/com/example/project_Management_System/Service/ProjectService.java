package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.ProjectRepository;
import com.example.project_Management_System.model.Project;
import com.example.project_Management_System.model.Task;
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
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public @NonNull Project getById(@NonNull Long projectId){
        log.info("[ProjectService] Попытка найти проект по ID {}", projectId);
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("[ProjectService] Проект с ID " + projectId + " не найден"));
    }

    public @NonNull Project getByName(@NonNull String projectName){
        log.info("[ProjectService] Попытка найти проект по имени {}", projectName);
        return projectRepository.findByName(projectName)
                .orElseThrow(() -> new EntityNotFoundException("[ProjectService] Проект с именем " + projectName + " не найден"));
    }

    @Transactional
    public Project saveProject(@NonNull Project project){
        log.info("[ProjectService] Сохранение проекта {}", project);
        return projectRepository.save(project);
    }

    public Project createProject(@NonNull Project project){
        log.info("[ProjectService] Создание проекта");
        if (project.getName().isEmpty()){
            throw new IllegalArgumentException("[ProjectService] Неверное значение name");
        }
        return saveProject(project);
    }

    @Transactional
    public void deleteById(@NonNull Long projectId){
        log.info("[ProjectService] Удаление проекта с ID {}", projectId);
        projectRepository.deleteById(projectId);
    }

    public void addTask(@NonNull Long projectId, @NonNull Task task){
        Task taskToAdd = taskService.getById(task.getId());
        Project project = getById(projectId);

        log.info("[ProjectService] Добавление задачи {} в проект {}", taskToAdd, project);

        project.getTasks().add(taskToAdd);
        saveProject(project);
        taskService.addTaskToProject(taskToAdd, project);
    }

    public void removeTask(@NonNull Long projectId, @NonNull Task task){
        Task taskToRemove = taskService.getById(task.getId());
        Project project = getById(projectId);

        log.info("[ProjectService] Убрать задачу {} из проекта {}", taskToRemove, project);

        project.getTasks().remove(taskToRemove);
        saveProject(project);
        taskService.removeTaskFromProject(taskToRemove, project);
    }

    public List<Task> getTasks(@NonNull Project project){
        log.info("[ProjectService] Поиск всех задач в проекте {}", project);
        return taskService.getAllTasks(project);
    }
}
