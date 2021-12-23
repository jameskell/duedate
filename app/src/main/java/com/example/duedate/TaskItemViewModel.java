package com.example.duedate;

import android.annotation.TargetApi;
import android.app.Application;

import com.example.duedate.db.TaskItem;
import com.example.duedate.db.TaskItemRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

public class TaskItemViewModel extends AndroidViewModel {

    private TaskItemRepository taskItemRepository;
    private LiveData<List<TaskItem>> allTasks;
    private LiveData<TaskItem> taskItem;

    public TaskItemViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application);
        this.taskItemRepository = new TaskItemRepository(application);
        this.allTasks = this.taskItemRepository.getAllTasks();
        this.taskItem = Transformations.switchMap(savedStateHandle.<Long>getLiveData("taskId"), (id) -> taskItemRepository.getTaskById(id));
    }

    public LiveData<List<TaskItem>> getAllTasks() {
        return this.allTasks;
    }

    public void insert(TaskItem item) {
        this.taskItemRepository.insert(item);
    }

    public void update(TaskItem item) {
        this.taskItemRepository.update(item);
    }

    public LiveData<TaskItem> getTask() {
        return this.taskItem;
    }
}
