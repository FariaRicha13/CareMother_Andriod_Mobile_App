package com.example.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Options extends AppCompatActivity {
    ImageButton imageButton;
    ImageButton imageButton_doctor;
    ImageButton imageButton_caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        imageButton = (ImageButton) findViewById(R.id.mother);
        imageButton_doctor = (ImageButton) findViewById(R.id.doctor);
        imageButton_caretaker = (ImageButton) findViewById(R.id.caretaker);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlogin();
            }
        });


        imageButton_doctor.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                doctor_openlogin();


            }
        });


        imageButton_caretaker.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                caretaker_openlogin();


            }
        });





    }

    public void openlogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
    public void doctor_openlogin(){
        Intent intent = new Intent(this, doctor_login.class);
        startActivity(intent);
    }

    public void caretaker_openlogin(){
        Intent intent = new Intent(this, caretaker_login.class);
        startActivity(intent);
    }








}