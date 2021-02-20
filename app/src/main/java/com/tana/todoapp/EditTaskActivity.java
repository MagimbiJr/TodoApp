package com.tana.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tana.todoapp.Dialogs.TaskDatePicker;
import com.tana.todoapp.Dialogs.TaskTimePicker;

import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity {
    public static final String TASK_NAME = "com.tana.todoapp.TASK_NAME";
    public static final String TASK_DATE = "com.tana.todoapp.TASK_DATE";
    public static final String TASK_START_TIME = "com.tana.todoapp.TASK_START_TIME";
    public static final String TASK_END_TIME = "com.tana.todoapp.TASK_END_TIME";
    public static final String TASK_ID = "com.tana.todoapp.TASK_ID";
    TextView mSetTaskDate, mSetTaskStartTime, mSetTaskEndTime;
    EditText mEditTaskName;
    private TaskDatePicker mDatePicker;
    private TaskTimePicker mStartTimePicker;
    private TaskTimePicker mEndTimePicker;
    private int mTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mEditTaskName = (EditText) findViewById(R.id.updateEditText);
        mSetTaskDate = (TextView) findViewById(R.id.dueDate);
        mSetTaskStartTime = (TextView) findViewById(R.id.dueStartTime);
        mSetTaskEndTime = (TextView) findViewById(R.id.dueEndTime);

        readValuesFromIntent();

        mSetTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker = new TaskDatePicker(v);
                mDatePicker.show(getSupportFragmentManager(), "date");
            }
        });

        mSetTaskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimePicker = new TaskTimePicker(v, null);
                mStartTimePicker.show(getSupportFragmentManager(), "start time");
            }
        });

        mSetTaskEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndTimePicker = new TaskTimePicker(null, v);
                mEndTimePicker.show(getSupportFragmentManager(), "end time");
            }
        });


        
    }

    private void readValuesFromIntent() {
        Intent intent = getIntent();
        mEditTaskName.setText(intent.getStringExtra(TASK_NAME));
        mSetTaskDate.setText(intent.getStringExtra(TASK_DATE));
        mSetTaskStartTime.setText(intent.getStringExtra(TASK_START_TIME));
        mSetTaskEndTime.setText(intent.getStringExtra(TASK_END_TIME));
        mTaskId = intent.getIntExtra(TASK_ID, 1);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_save) {

            String updatedTaskName = mEditTaskName.getText().toString();
            String updatedDate = mSetTaskDate.getText().toString();
            String updatedStartTime = mSetTaskStartTime.getText().toString();
            String updateEndTime = mSetTaskEndTime.getText().toString();

            Task updatedTask = new Task(updatedTaskName, updatedDate, updatedStartTime, updateEndTime);
            TaskViewModel viewModel = new TaskViewModel(getApplication());
            updatedTask.setTaskId(mTaskId);
            viewModel.update(updatedTask);

            finish();
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}