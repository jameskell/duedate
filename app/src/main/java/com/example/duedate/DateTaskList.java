package com.example.duedate;

import com.example.duedate.db.TaskItem;

import java.util.List;

public class DateTaskList {

    private String date;
    private List<TaskItem> tasks;

    public DateTaskList(String date, List<TaskItem> tasks) {
        this.date = date;
        this.tasks = tasks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TaskItem> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskItem> tasks) {
        this.tasks = tasks;
    }
}
