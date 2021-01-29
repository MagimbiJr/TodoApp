package com.tana.todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM task")
    void deleteAll();

    @Query("SELECT * from Task ORDER BY task_name ASC")
    LiveData<List<Task>> getAllTasks();
}
