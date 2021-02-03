package com.tana.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final Context mContext;
    private List<Task> mTask = new ArrayList<>();

    public TaskAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = mTask.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return mTask.size();
    }

    public void setTask(List<Task> tasks) {
        this.mTask = tasks;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTaskName, mTvDay, mTvStartTime, mTvEndTime;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTaskName = (TextView) itemView.findViewById(R.id.text_view_task_name);
            mTvDay = (TextView) itemView.findViewById(R.id.text_view_day);
            mTvStartTime = (TextView) itemView.findViewById(R.id.text_view_start_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.text_view_end_time);
        }

        public void bind(Task task) {
            mTvTaskName.setText(task.getTaskName());
            mTvDay.setText(task.getTaskDate());
            mTvStartTime.setText(task.getStartTime());
            mTvEndTime.setText(task.getEndTime());
        }
    }
}
