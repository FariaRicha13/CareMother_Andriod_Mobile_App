package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class checkHealth extends AppCompatActivity {
    CardView pulse;
    CardView temp;
    CardView bp;
    CardView sleep;
    CardView step;



    Button logout;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_health);


        pulse = findViewById(R.id.pulse);
        temp = findViewById(R.id.temp);
        sleep= findViewById(R.id.sleep);
        step = findViewById(R.id.step);
        bp = findViewById(R.id.bp);




        pulse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPulse();
            }
        });

        temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTemp();
            }
        });
        bp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openBp();
            }
        });
        step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStep();
            }
        });

        sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSleep();
            }
        });



    }



    public void openPulse(){
        Intent intent = new Intent(this, ShowPulse.class);

       startActivity(intent);
    }
    public void openTemp(){
       Intent intent = new Intent(this, ShowTemp.class);

       startActivity(intent);
    }
    public void openStep(){
        Intent intent = new Intent(this, step_count.class);

        startActivity(intent);
    }
    public void openSleep(){
        Intent intent = new Intent(this, sleep.class);

        startActivity(intent);
    }
    public void openBp(){
        Intent intent = new Intent(this, BpShow.class);

        startActivity(intent);
    }



}

