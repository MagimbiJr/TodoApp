package com.tana.todoapp;

import android.app.Application;
import android.content.AsyncQueryHandler;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskRepository(Application application) {
        TaskDb db = TaskDb.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert(Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    //Insert new task asynchronously

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        public insertAsyncTask(TaskDao taskDao) {
            mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncTaskDao.insertTask(tasks[0]);
            return null;
        }
    }
}
