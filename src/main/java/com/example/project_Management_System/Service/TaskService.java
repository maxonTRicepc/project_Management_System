package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.TaskRepository;
import com.example.project_Management_System.model.History;
import com.example.project_Management_System.model.Project;
import com.example.project_Management_System.model.Task;
import com.example.project_Management_System.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final StatusService statusService;
    private final PriorityService priorityService;
    private final HistoryService historyService;

    public @NonNull Task getById(@NonNull Long taskId){
        log.info("[TaskService] Попытка найти задачу по ID {}", taskId);
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("[TaskService] Задача с ID " + taskId + " не найдена"));
    }

    @Transactional
    public Task saveTask(@NonNull Task task){
        log.info("[TaskService] Сохранение задачи {}", task);
        return taskRepository.save(task);
    }

    public void createTask(@NonNull Task task, @NonNull Long creatorId){
        log.info("[TaskService] Создание задачи");

        task.setPriority(priorityService.getByName(task.getPriority().getName()));
        task.setStatus(statusService.getByName(task.getStatus().getName()));

        User user = userService.getUserById(creatorId);
        task.getAssignedUsers().add(user);

        Task savedTask = saveTask(task);

        History history = new History()
                .setTask(savedTask)
                .setChangedBy(user)
                .setChangeDescription("Создание");
        historyService.createHistory(history);
    }

    public void updateTask(@NonNull Long taskId, @NonNull Task newData, @NonNull Long userId){
        log.info("[TaskService] Обновление задачи");
        Task taskUpToDate = getById(taskId);
        updateEntity(taskUpToDate, newData);

        Task updatedTask = saveTask(taskUpToDate);

        History history = new History()
                .setTask(updatedTask)
                .setChangedBy(userService.getUserById(userId))
                .setChangeDescription("Изменение");
        historyService.createHistory(history);
    }

    @Transactional
    public void deleteById (@NonNull Long taskId){
        log.info("[TaskService] удаление задачи с ID {}", taskId);

        taskRepository.deleteById(taskId);
    }

    public void addUser(@NonNull Long taskId, @NonNull Long userId, @NonNull Long userWhoAddId){
        log.info("[TaskService] Добавление пользователя в задачу");
        Task task = getById(taskId);
        User userToAdd = userService.getUserById(userId);

        task.getAssignedUsers().add(userToAdd);
        userToAdd.getTasks().add(task);
        Task savedTask = saveTask(task);
        User savedUser = userService.saveUser(userToAdd);

        History history = new History()
                .setTask(savedTask)
                .setChangedBy(userService.getUserById(userWhoAddId))
                .setChangeDescription("Добавление пользователя " + savedUser.getLogin());
        historyService.createHistory(history);
    }

    public void removeUser(@NonNull Long taskId, @NonNull Long userId, @NonNull Long userWhoRemoveId){
        log.info("[TaskService] Удаление пользователя из задачи");
        Task task = getById(taskId);
        User userToRemove = userService.getUserById(userId);

        task.getAssignedUsers().removeIf(u -> u.getId().equals(userId));
//        userToRemove.getTasks().remove(task);
        Task savedTask = saveTask(task);
//        User savedUser = userService.saveUser(userToRemove);

        History history = new History()
                .setTask(savedTask)
                .setChangedBy(userService.getUserById(userWhoRemoveId))
                .setChangeDescription("Удаление пользователя " + userToRemove.getLogin());
        historyService.createHistory(history);
    }

    private void updateEntity(@NonNull Task oldTask, @NonNull Task newTask){
        Optional.ofNullable(newTask.getStatus()).map(oldTask::setStatus);
        Optional.ofNullable(newTask.getDescription()).map(oldTask::setDescription);
        Optional.ofNullable(newTask.getPriority()).map(oldTask::setPriority);
        Optional.ofNullable(newTask.getName()).map(oldTask::setName);
    }

    protected void addTaskToProject(@NonNull Task task, @NonNull Project project){
        task.setProject(project);
        saveTask(task);
        log.info("[TaskService] Задача успешно добавлена в проект");
    }

    protected void removeTaskFromProject(@NonNull Task task, @NonNull Project project){
        task.setProject(null);
        saveTask(task);
        log.info("[TaskService] Задача успешно убрана из проекта");
    }

    protected List<Task> getAllTasks(@NonNull Project project){
        return taskRepository.findAllTaskByProject(project);
    }
}
