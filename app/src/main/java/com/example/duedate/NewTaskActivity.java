package com.example.duedate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.duedate.databinding.ActivityNewTaskBinding;
import com.example.duedate.db.TaskItem;
import com.example.duedate.db.TaskPriority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityNewTaskBinding binding;
    public static final String SAVE_REPLY = "com.example.james.duedate.ON_SAVE";
    private LocalDateTime taskDate = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void openDatepicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void openTimepicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
//        this.taskDate = LocalDateTime.of(year, month + 1, day, 0, 0);
        this.taskDate = this.taskDate.withYear(year).withMonth(month + 1).withDayOfMonth(day);
        binding.dateTimeView.setText(this.taskDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy h:mm a")));
        binding.dateTimeView.setVisibility(View.VISIBLE);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
//        if(taskDate != null) {

//        }
        this.taskDate = this.taskDate.withHour(hourOfDay).withMinute(minute);
        binding.dateTimeView.setText(this.taskDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy h:mm a")));
        binding.dateTimeView.setVisibility(View.VISIBLE);
    }

    public void saveTask(View view) {
        Intent replyIntent = new Intent();
        int priorityId = binding.newPriority.getCheckedRadioButtonId();
        View radioButton = findViewById(priorityId);
        int idx = binding.newPriority.indexOfChild(radioButton);
        TaskPriority p;
        if(idx == 0) {
            p = TaskPriority.HIGH;
        } else if(idx == 1) {
            p = TaskPriority.MEDIUM;
        } else {
            p = TaskPriority.LOW;
        }
        TaskItem newItem = new TaskItem(binding.newTaskName.getText().toString(), binding.newTaskDesc.getText().toString(),
                this.taskDate, p, false);
        replyIntent.putExtra(SAVE_REPLY, newItem);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void cancelTask(View view) {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    public static class TimePickerFragment extends DialogFragment  {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), (NewTaskActivity) getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }



    }
}