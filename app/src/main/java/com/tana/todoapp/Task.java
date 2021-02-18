package com.tana.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int taskId;
    @ColumnInfo(name = "task_name")
    private final String taskName;
    @ColumnInfo(name = "task_date")
    private final String taskDate;
    @ColumnInfo(name = "start_time")
    private final String startTime;
    @ColumnInfo(name = "end_time")
    private final String endTime;

    public Task(String taskName, String taskDate, String startTime, String endTime) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Task(Parcel source) {
        taskName = source.readString();
        taskDate = source.readString();
        startTime = source.readString();
        endTime = source.readString();
    }

    public int getTaskId() {
        return taskId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskName);
        dest.writeString(taskDate);
        dest.writeString(startTime);
        dest.writeString(endTime);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
