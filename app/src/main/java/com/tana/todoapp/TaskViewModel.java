package com.tana.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository mRepository;
    private LiveData<List<Task>> mGetAllTask;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mGetAllTask = mRepository.getAllTasks();
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }

    public void update(Task task) {
        mRepository.update(task);
    }

    public void delete(Task task) {
        mRepository.delete(task);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mGetAllTask;
    }
}
