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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tana.todoapp.Dialogs.NewTaskDialog;

import java.util.List;

public class TasksListActivity extends AppCompatActivity {
    private TaskViewModel mTaskViewModel;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.green));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
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

        TaskAdapter adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);

        //Data handler
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTask(tasks);
            }
        });

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
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.action_profile:
//
//            case R.id.action_setting:
//
//            default:
//
//        }
    }
}