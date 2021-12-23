package com.example.duedate.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String taskName;
    private String taskDescription;
    private LocalDateTime taskDate;
    private TaskPriority priority;
    private boolean completed;

    public TaskItem(String taskName, String taskDescription, LocalDateTime taskDate, TaskPriority priority, boolean completed) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.priority = priority;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public LocalDateTime getTaskDate() {
        return taskDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
