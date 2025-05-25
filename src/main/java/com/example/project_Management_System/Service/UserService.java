package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.UserRepository;
import com.example.project_Management_System.model.Role;
import com.example.project_Management_System.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final String DEFAULT_ROLE = "ROLE_USER";

    public @NonNull User getUserById(Long userId){
        log.info("[UserService] Попытка найти пользователя с ID {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("[UserService] Пользователь с ID " + userId + " не найден"));
    }

    @Transactional
    public User saveUser(@NonNull User user){
        log.info("[UserService] Сохранение пользователя {}", user);
        return userRepository.save(user);
    }

    public User createUser(@NonNull User user) {
        log.info("[UserService] Создание пользователя {}", user);
        if (userRepository.existsByLogin(user.getLogin()) || userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("[UserService] Логин или почта уже заняты");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role defaultRole = roleService.getByName(DEFAULT_ROLE);
        user.setRole(defaultRole);

        return saveUser(user);
    }

    public @NonNull User assignRole(@NonNull Long userId, @NonNull Role role){
        User userToAssign = getUserById(userId);
        if (role.getName().equals(DEFAULT_ROLE)){
            return userToAssign;
        }
        else {
            Role newRole = roleService.getByName(role.getName());
            userToAssign.setRole(newRole);
            return saveUser(userToAssign);
        }
    }

    public User updateUser(@NonNull Long userId, @NonNull User newData){
        log.info("[UserService] Обновление пользователя с ID {}", userId);
        if (userRepository.existsByLogin(newData.getLogin()) || userRepository.existsByEmail(newData.getEmail())) {
            throw new IllegalArgumentException("[UserService] Логин или почта уже заняты");
        }
        User userUpToDate = getUserById(userId);
        
        updateEntity(userUpToDate, newData);
        
        return saveUser(userUpToDate);        
    }

    @Transactional
    public void deleteUserById(@NonNull Long userId){
        log.info("[UserService] Удаление пользователя с ID {}", userId);
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsersWithTasks() {
        return userRepository.findAllWithTasks();
    }

    private void updateEntity(User oldUser, User newUser){
        Optional.ofNullable(newUser.getEmail()).map(oldUser::setEmail);
        Optional.ofNullable(newUser.getLogin()).map(oldUser::setLogin);
        Optional.ofNullable(passwordEncoder.encode(newUser.getPassword())).map(oldUser::setPassword);
    }
}