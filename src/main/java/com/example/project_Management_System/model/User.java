package com.example.project_Management_System.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private List<Task> tasks;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role); // или List.of(role) в Java 9+
    }

    public String getUsername() {
        return login;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
