package com.example.signupandlogin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class scheduler extends AppCompatActivity {
    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.scheduler);
        customCalendarView = (CustomCalendarView) findViewById(R.id.custom_calendar);
    }
}
