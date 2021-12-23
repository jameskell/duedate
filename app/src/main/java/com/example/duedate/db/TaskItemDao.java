package com.example.duedate.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskItemDao {

    @Insert
    void insert(TaskItem item);

    @Update
    void update(TaskItem item);

    @Query("SELECT * FROM tasks WHERE completed = 0 ORDER BY taskDate DESC")
    LiveData<List<TaskItem>> getTasksByDate();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskItem> getTaskById(long id);

}
