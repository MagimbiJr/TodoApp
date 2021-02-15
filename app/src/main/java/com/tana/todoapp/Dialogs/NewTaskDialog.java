package com.tana.todoapp.Dialogs;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tana.todoapp.R;
import com.tana.todoapp.Task;
import com.tana.todoapp.TaskViewModel;


public class NewTaskDialog extends BottomSheetDialogFragment {

    private EditText mAddTaskInput;
    private TaskTimePicker mEndTimePicker;
    private TaskTimePicker mStartTimePicker;
    private TaskDatePicker mDatePicker;
    private final Application mApplication;
    public NewTaskDialog(@NonNull Application application){
        mApplication = application;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.add_task, container, false);

        TextView setDate = itemView.findViewById(R.id.set_date);
        TextView startTime = itemView.findViewById(R.id.start_time);
        TextView endTime = itemView.findViewById(R.id.end_time);
        mAddTaskInput = itemView.findViewById(R.id.editTextAddTask);
        ImageButton saveTask = itemView.findViewById(R.id.save_task);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker = new TaskDatePicker(v);
                mDatePicker.show(getFragmentManager(), "date picker");
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimePicker = new TaskTimePicker(v, null);
                mStartTimePicker.show(getFragmentManager(), "start time");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndTimePicker = new TaskTimePicker(null, v);
                mEndTimePicker.show(getFragmentManager(), "end time");
            }
        });

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTaskToDb();
                dismiss();
            }
        });

        return itemView;
    }

    private void saveTaskToDb() {
        String addedTask = mAddTaskInput.getText().toString();
        String date = mDatePicker.pickedDate();
        String startTime = mStartTimePicker.startTimeValue();
        String endTime = mEndTimePicker.endTimeValue();

        if (addedTask.trim().isEmpty()) {
            Toast.makeText(mApplication, "Can't save an empty task", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(addedTask, date, startTime, endTime);

        TaskViewModel viewModel = new TaskViewModel(mApplication);

        viewModel.insert(task);



        // let viewModel = new ViewModel(this.application)
        //viewModel.insert(addedTask)
    }
}
