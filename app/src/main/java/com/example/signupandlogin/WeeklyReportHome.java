package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class WeeklyReportHome extends AppCompatActivity {
    CardView weeklyr,graphicalr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report_home);

        weeklyr = findViewById(R.id.weekly);
        graphicalr = findViewById(R.id.graphical);


       weeklyr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openWeekly();
            }
        });
        graphicalr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGraphical();
            }
        });



    }

    public void openWeekly(){
        Intent intent = new Intent(this, WeeklyActivity.class);
        startActivity(intent);
    }
    public void openGraphical(){
        Intent intent = new Intent(this, GraphHome.class);
        startActivity(intent);

    }




}

