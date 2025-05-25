package com.example.project_Management_System.Service;

import com.example.project_Management_System.Repository.RoleRepository;
import com.example.project_Management_System.model.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public Role getByName(String name){
        log.info("[RoleService] Попытка получить роль по имени {}", name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Роли с таким именем " + name + " не существует"));
    }
}
