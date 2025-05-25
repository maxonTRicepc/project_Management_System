package com.example.project_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User changedBy;

    private Date changeDate;
    private String changeDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id.equals(history.id) && changeDate.equals(history.changeDate) &&
                changeDescription.equals(history.changeDescription) &&
                changedBy.equals(history.changedBy) && task.equals(history.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, changeDate, changeDescription, changedBy, task);
    }

    @Override
    public String toString(){
        return "History(" +
                "id=" + this.id +
                ", task=" + this.task +
                ", changedBy=" + this.changedBy +
                ", changedDate=" + this.changeDate +
                ", changedDescription=" + this.changeDescription +
                ")";
    }
}