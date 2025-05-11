package com.example.project_Management_System.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private List<Task> tasks;
}
