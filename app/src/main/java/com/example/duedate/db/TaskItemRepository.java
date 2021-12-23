package com.example.duedate.db;

import android.app.Application;

import java.util.Comparator;
import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskItemRepository {

    private TaskItemDao taskItemDao;
    private LiveData<List<TaskItem>> allTasks;

    public TaskItemRepository(Application application) {
        DueDateDatabase db = DueDateDatabase.getDatabase(application);
        this.taskItemDao = db.taskItemDao();
        this.allTasks = this.taskItemDao.getTasksByDate();
    }

    public LiveData<List<TaskItem>> getAllTasks() {
        return this.allTasks;
    }

    public LiveData<TaskItem> getTaskById(long id) {
        return this.taskItemDao.getTaskById(id);
    }

    public void insert(TaskItem item) {
        DueDateDatabase.databaseWriteExecutor.execute(() -> {
            this.taskItemDao.insert(item);
        });
    }

    public void update(TaskItem item) {
        DueDateDatabase.databaseWriteExecutor.execute(() -> {
            this.taskItemDao.update(item);
        });
    }
}
