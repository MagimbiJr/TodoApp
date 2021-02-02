package com.tana.todoapp;

import android.app.Application;
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

    public void insert(Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    public void update(Task task) {
        new updateAsyncTask(mTaskDao).execute(task);
    }

    public void delete(Task task) {
        new deleteAsyncTask(mTaskDao).execute(task);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mTaskDao).execute();
    }

    LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }


    //Asynchronously Room database operation

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncInsertTask;

        public insertAsyncTask(TaskDao taskDao) {
            mAsyncInsertTask = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncInsertTask.insertTask(tasks[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncUpdateTask;

        public updateAsyncTask(TaskDao taskDao) {
            mAsyncUpdateTask = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncUpdateTask.update(tasks[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncDeleteTask;

        public deleteAsyncTask(TaskDao taskDao) {
            mAsyncDeleteTask = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncDeleteTask.delete(tasks[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mAsyncDeleteAllTask;

        public deleteAllAsyncTask(TaskDao taskDao) {
            mAsyncDeleteAllTask = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncDeleteAllTask.deleteAll();
            return null;
        }
    }
}
