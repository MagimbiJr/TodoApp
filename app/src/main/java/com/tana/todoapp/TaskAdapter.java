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
    private final OnTaskClickListener mOnTaskClickListener;

    public TaskAdapter(Context context, OnTaskClickListener taskClickListener) {
        this.mContext = context;
        mOnTaskClickListener = taskClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView, mOnTaskClickListener);
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

    public Task getTaskAt(int position) {
        return mTask.get(position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvTaskName, mTvDay, mTvStartTime, mTvEndTime;
        OnTaskClickListener mOnTaskClickListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            mTvTaskName = (TextView) itemView.findViewById(R.id.text_view_task_name);
            mTvDay = (TextView) itemView.findViewById(R.id.text_view_day);
            mTvStartTime = (TextView) itemView.findViewById(R.id.text_view_start_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.text_view_end_time);

            mOnTaskClickListener = onTaskClickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(Task task) {
            mTvTaskName.setText(task.getTaskName());
            mTvDay.setText(task.getTaskDate());
            mTvStartTime.setText(task.getStartTime());
            mTvEndTime.setText(task.getEndTime());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mOnTaskClickListener != null && position != RecyclerView.NO_POSITION) {
                mOnTaskClickListener.onTaskClick(mTask.get(position));
            }
        }
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }
}
