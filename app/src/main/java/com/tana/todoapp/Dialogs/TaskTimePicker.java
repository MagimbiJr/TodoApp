package com.tana.todoapp.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TaskTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    TextView mStartTime, mEndTime;
    private String mTime;
    private String mPickedEndTime;

    public TaskTimePicker(View startTime, View endTime) {
        mStartTime = (TextView) startTime;
        mEndTime = (TextView) endTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), this, hour, min, DateFormat.is24HourFormat(getContext()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        mTime = java.text.DateFormat.getTimeInstance().format(calendar.getTime());

        if (mStartTime != null) {
            mStartTime.setText(mTime);
            startTimeValue();
        }
        if (mEndTime != null) {
            mEndTime.setText(mTime);
            endTimeValue();
        }
    }

    public String startTimeValue() {
        String pickedStartTime = mStartTime.getText().toString();
        return pickedStartTime;
    }

    public String endTimeValue() {
        String pickedEndTime = mEndTime.getText().toString();
        return pickedEndTime;
    }

}
