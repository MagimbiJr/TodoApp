package com.tana.todoapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDb extends RoomDatabase {
    private static TaskDb INSTANCE;

    public abstract TaskDao taskDao();

    public static TaskDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskDb.class,
                            "task_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
