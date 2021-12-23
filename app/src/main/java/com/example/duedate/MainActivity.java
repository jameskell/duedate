package com.example.duedate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duedate.databinding.ActivityMainBinding;
import com.example.duedate.db.TaskItem;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private TaskItemViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final TaskDateListAdapter adapter = new TaskDateListAdapter(this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(TaskItemViewModel.class);
        final DateTimeFormatter f = DateTimeFormatter.ofPattern("M/dd/yyyy");
        viewModel.getAllTasks().observe(this, taskItems -> {
            taskItems.sort((o1, o2) -> {
                if(o1.getTaskDate().toLocalDate().isEqual(o2.getTaskDate().toLocalDate())) {
                    if(o1.getPriority().compareTo(o2.getPriority()) == 0) {
                        return o1.getTaskDate().compareTo(o2.getTaskDate());
                    } else {
                        return o1.getPriority().compareTo(o2.getPriority());
                    }
                } else {
                    return o1.getTaskDate().compareTo(o2.getTaskDate());
                }
            });

            SortedMap<String, DateTaskList> dateDateTaskListMap = new TreeMap<>();
            for(TaskItem i : taskItems) {
                String dd = i.getTaskDate().format(f);
                if(!dateDateTaskListMap.containsKey(dd)) {
                    dateDateTaskListMap.put(dd, new DateTaskList(dd, new ArrayList<>()));
                }
                dateDateTaskListMap.get(dd).getTasks().add(i);
            }
            List<DateTaskList> allDateTasks = new ArrayList<>();
            for(String dd : dateDateTaskListMap.keySet()) {
                allDateTasks.add(dateDateTaskListMap.get(dd));
            }
            adapter.setAllDateTasks(allDateTasks);
            if(allDateTasks.size() > 0) {
                binding.noTasksView.setVisibility(View.INVISIBLE);
            } else {
                binding.noTasksView.setVisibility(View.VISIBLE);
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            if(data.hasExtra(NewTaskActivity.SAVE_REPLY)) {
                TaskItem item = (TaskItem) data.getSerializableExtra(NewTaskActivity.SAVE_REPLY);
                viewModel.insert(item);
            }
        } else {
            Toast.makeText(getApplicationContext(), "PROBLEM", Toast.LENGTH_LONG).show();
        }
    }
}