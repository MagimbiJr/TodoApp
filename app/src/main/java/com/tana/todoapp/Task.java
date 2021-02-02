package com.tana.todoapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int taskId;
    @ColumnInfo(name = "task_name")
    private String taskName;
    @ColumnInfo(name = "task_date")
    private String taskDate;
    @ColumnInfo(name = "start_time")
    private String startTime;
    @ColumnInfo(name = "end_time")
    private String endTime;

    public Task(String taskName, String taskDate, String startTime, String endTime) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

}
