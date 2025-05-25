package com.example.project_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<Task> tasks = new ArrayList<>();

    // Spring Security методы
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) &&
                password.equals(user.password) && email.equals(user.email) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role);
    }

    @Override
    public String toString(){
        return "User(" +
                "id=" + this.id +
                ", login=" + this.login +
                ", email=" + this.email +
                ", role=" + this.role +
                ")";
    }
}