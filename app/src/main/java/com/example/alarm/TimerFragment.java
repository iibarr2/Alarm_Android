package com.example.alarm;

import android.app.Dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class TimerFragment extends Fragment {

    private static final String TAG = "TimerFragment";

    private EditText mEditTextInput;
    private EditText editMessage;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private String message = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.timer_layout,container,false);


        mEditTextInput = view.findViewById(R.id.edit_text_input);
        mEditTextInput.setText("");
        editMessage = view.findViewById(R.id.editText_userMessage);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonSet = view.findViewById(R.id.button_set);
        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditTextInput.getText().toString().equalsIgnoreCase("")) {
                    message = getMessage();
                }
                else
                {
                    String input = mEditTextInput.getText().toString();
                    long millisInput = Long.parseLong(input) * 60000;
                    setTime(millisInput);
                    mEditTextInput.setText("");
                    message = getMessage();
                    editMessage.setText("");
                }


            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });


        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        return view;

    }

    private String getMessage(){
        String message = editMessage.getText().toString();
        //String message1 = message;
        return message;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
        if(mTimerRunning)
        {
           pauseTimer();
        }
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void updateWatchInterface() {
        if (mTimerRunning) {
            mButtonStartPause.setText("Pause");
        } else {

            mButtonStartPause.setText("Start");


        }
    }



}




