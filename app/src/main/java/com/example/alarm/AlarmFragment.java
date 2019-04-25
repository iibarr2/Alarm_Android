package com.example.alarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AlarmFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AlarmFargment";
    Button btnDate, btnTime, btnSetAlarm;
    EditText txtDate, txtTime;
    TextInputEditText message;
    private int year, month, day, hour,minute;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_layout,container,false);
        btnDate = (Button)view.findViewById(R.id.btn_date);
        btnTime = (Button)view.findViewById(R.id.btn_time);
        btnSetAlarm = (Button)view.findViewById(R.id.btn_setAlarm);
        txtDate = (EditText)view.findViewById(R.id.in_date);
        txtTime = (EditText)view.findViewById(R.id.in_time);
        message = (TextInputEditText)view.findViewById(R.id.in_Message);

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnDate){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        }
    }
}
