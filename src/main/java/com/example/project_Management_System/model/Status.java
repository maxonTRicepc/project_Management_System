package com.example.project_Management_System.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Status {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
}
