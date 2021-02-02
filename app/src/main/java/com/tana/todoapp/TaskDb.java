package com.tana.todoapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDb extends RoomDatabase {
    private static TaskDb INSTANCE;

    public abstract TaskDao taskDao();

    public static synchronized TaskDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskDb.class, "task_database")
                    .addCallback(taskDbCallback)
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback taskDbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mTaskDao;

        public populateDbAsyncTask(TaskDb db) {
            mTaskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.insertTask(new Task("Android: BackgroundTasks", "3/2/2021",
                    "9:00", "11:00"));
            mTaskDao.insertTask(new Task("Java: Classes & Interfaces", "3/2/2021",
                    "11:00", "1:00"));
            mTaskDao.insertTask(new Task("Android: UserInterface", "3/2/2021",
                    "4:00", "7:00"));
            return null;
        }
    }
}
