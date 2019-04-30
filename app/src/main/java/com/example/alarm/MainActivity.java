package com.example.alarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager viewPager;

    private EditText mEditText;
    private EditText timePickerText;
    private EditText datePickerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_alarm);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_timer);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_location);

        mEditText = findViewById(R.id.in_Message);
        timePickerText = findViewById(R.id.in_time);
        datePickerText = findViewById(R.id.in_date);

       final Button buttonTimePicker = findViewById(R.id.btn_selecttime);

        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DialogFragment timePicker = new AlarmFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button cancelButton = findViewById(R.id.btn_Cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlarmFragment());
        adapter.addFragment(new TimerFragment());
        adapter.addFragment(new LocationFragment());
        viewPager.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c){
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timePickerText.setText(timeText);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
        timePickerText.setText("Alarm Cancelled");
        datePickerText.setText("Alarm Cancelled");
    }
}
