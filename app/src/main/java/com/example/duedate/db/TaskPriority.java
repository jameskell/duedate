package com.example.duedate.db;

public enum TaskPriority {

    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private String value;

    TaskPriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
