package com.tana.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tana.todoapp.Dialogs.NewTaskDialog;

import java.util.ArrayList;
import java.util.List;

public class TasksListActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    public static final String TASK_DATE = "com.tana.todoapp.TASK_DATE";
    public static final String TASK_START_TIME = "com.tana.todoapp.TASK_START_TIME";
    public static final String TASK_END_TIME = "com.tana.todoapp.TASK_END_TIME";
    private TaskViewModel mTaskViewModel;
    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.green));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Add new task

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTaskDialog bottomSheet = new NewTaskDialog(getApplication());
                bottomSheet.show(getSupportFragmentManager(), "bottom sheet dialog");
            }
        });

        //RecyclerView Setup

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTaskAdapter = new TaskAdapter(this, this);
        recyclerView.setAdapter(mTaskAdapter);

        //Data handler
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mTaskAdapter.setTask(tasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mTaskViewModel.delete(mTaskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TasksListActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_profile) {
            Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.action_setting) {
            Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.action_delete) {
            mTaskViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskClick(Task task) {
        Intent updateActivity = new Intent(this, EditTaskActivity.class);
        updateActivity.putExtra(EditTaskActivity.TASK_NAME, task.getTaskName());
        updateActivity.putExtra(EditTaskActivity.TASK_DATE, task.getTaskDate());
        updateActivity.putExtra(EditTaskActivity.TASK_START_TIME, task.getStartTime());
        updateActivity.putExtra(EditTaskActivity.TASK_END_TIME, task.getEndTime());
        updateActivity.putExtra(EditTaskActivity.TASK_ID, task.getTaskId());

        startActivity(updateActivity);
    }
}