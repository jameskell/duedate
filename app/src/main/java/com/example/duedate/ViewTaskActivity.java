package com.example.duedate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.duedate.databinding.ActivityViewTaskBinding;
import com.example.duedate.db.TaskItem;

import java.time.format.DateTimeFormatter;

public class ViewTaskActivity extends AppCompatActivity {

    private TaskItemViewModel viewModel;
    private ActivityViewTaskBinding binding;
    private TaskItem taskItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(TaskItemViewModel.class);
        this.viewModel.getTask().observe(this, item -> {
            taskItem = item;
            if(item != null) {
                binding.taskNameValue.setText(item.getTaskName());
                binding.taskDueDateValue.setText(item.getTaskDate().format(DateTimeFormatter.ofPattern("M/dd/yyyy h:mm a")));
                binding.taskPriorityValue.setText(item.getPriority().getValue());
                binding.taskDescValue.setText(item.getTaskDescription());
            }
        });

    }

    public void completeTask(View v) {
        this.taskItem.setCompleted(true);
        this.viewModel.update(this.taskItem);
        finish();
    }
}