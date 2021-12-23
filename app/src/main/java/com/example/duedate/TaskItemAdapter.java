package com.example.duedate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duedate.db.TaskItem;
import com.example.duedate.db.TaskPriority;

import java.time.format.DateTimeFormatter;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder> {

    static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameView;
        private final TextView dateView;
//        private final TextView priorityView;
        private final TextView taskIdView;
        private final LinearLayout taskRow;
        private final Context context;

        private TaskViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = itemView.getContext();
            this.taskRow = itemView.findViewById(R.id.taskRow);
            this.nameView = itemView.findViewById(R.id.taskName);
            this.dateView = itemView.findViewById(R.id.taskDate);
            this.taskIdView = itemView.findViewById(R.id.taskId);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(this.context, ViewTaskActivity.class);
            intent.putExtra("taskId", Long.parseLong(this.taskIdView.getText().toString()));
            context.startActivity(intent);
        }
    }

    private List<TaskItem> allTasks;
    private Context context;

    TaskItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TaskItemAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if(this.allTasks != null) {
            TaskItem currentItem = this.allTasks.get(position);
            holder.dateView.setText(currentItem.getTaskDate().format(DateTimeFormatter.ofPattern("h:mm a")));
            holder.nameView.setText(currentItem.getTaskName());
            holder.taskIdView.setText(Long.toString(currentItem.getId()));
            if(currentItem.getPriority() == TaskPriority.HIGH) {
                holder.taskRow.setBackgroundResource(R.drawable.layout_border);
            }
            if(currentItem.getPriority() == TaskPriority.MEDIUM) {
                holder.taskRow.setBackgroundResource(R.drawable.layout_border_med);
            }
            if(currentItem.getPriority() == TaskPriority.LOW) {
                holder.taskRow.setBackgroundResource(R.drawable.layout_border_low);
            }
        }
    }

    public void setAllTasks(List<TaskItem> tasks) {
        this.allTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.allTasks == null ? 0 : this.allTasks.size();
    }
}
