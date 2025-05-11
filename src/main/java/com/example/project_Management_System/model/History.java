package com.example.project_Management_System.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class History {
    @EqualsAndHashCode.Include
    private Long id;
    private Task task;
    private User changedBy;
    private Date changeDate;
    private String changeDescription;
}
