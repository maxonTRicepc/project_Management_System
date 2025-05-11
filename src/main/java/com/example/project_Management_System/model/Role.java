package com.example.project_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data

public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        // Стандартное соглашение Spring Security - добавляем префикс ROLE_
        return name.startsWith("ROLE_") ? name : "ROLE_" + name;
    }
}
