package com.example.duedate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duedate.db.TaskItem;
import com.example.duedate.db.TaskPriority;

import java.time.format.DateTimeFormatter;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskDateListAdapter extends RecyclerView.Adapter<TaskDateListAdapter.TaskDateListHolder> {

    static class TaskDateListHolder extends RecyclerView.ViewHolder {
        private final TextView dateView;
        private final RecyclerView taskRecyclerView;
        private final Context context;

        private TaskDateListHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.dateView = itemView.findViewById(R.id.taskListDate);
            this.taskRecyclerView = itemView.findViewById(R.id.taskRecyclerView);
        }

    }

    private List<DateTaskList> allTasks;
    private Context context;

    TaskDateListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TaskDateListAdapter.TaskDateListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tasklist, parent, false);

        return new TaskDateListHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(TaskDateListHolder holder, int position) {
        if(this.allTasks != null) {
            DateTaskList currentItem = this.allTasks.get(position);
            holder.dateView.setText(currentItem.getDate());
            final TaskItemAdapter adapter = new TaskItemAdapter(this.context);
            holder.taskRecyclerView.setAdapter(adapter);
            holder.taskRecyclerView.setLayoutManager(new LinearLayoutManager(this.context));
            adapter.setAllTasks(currentItem.getTasks());
        }
    }

    public void setAllDateTasks(List<DateTaskList> tasks) {
        System.out.println(tasks.size());
        this.allTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.allTasks == null ? 0 : this.allTasks.size();
    }
}
