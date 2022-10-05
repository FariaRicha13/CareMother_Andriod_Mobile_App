package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GraphHome extends AppCompatActivity {
    CardView BP;
    CardView pulse;
    CardView temp;
    CardView sleep;
    CardView step;


    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_home);


        BP = findViewById(R.id.BP);
        pulse = findViewById(R.id.Pulse);
        sleep = findViewById(R.id.Sleep);
       step = findViewById(R.id.Step);
        temp = findViewById(R.id.Temp);

        String username;


        BP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openBP();
            }
        });
        step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStep();
            }
        });

       pulse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPulse();
            }
        });
      /*  step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStep();
            }
        });*/

        sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSleep();
            }
        });
        temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTemp();
            }
        });

    }



    public void openBP(){
        Intent intent = new Intent(this, bpGraph.class);

        startActivity(intent);
    }
    public void openPulse(){
        Intent intent = new Intent(this, PulseGraph.class);

        startActivity(intent);
    }
    public void openSleep(){
        Intent intent = new Intent(this, SleepGraph.class);

        startActivity(intent);
    }
    public void openTemp(){
        Intent intent = new Intent(this, TempGraph.class);

        startActivity(intent);
    }
    public void openStep(){
        Intent intent = new Intent(this, StepGraph.class);

        startActivity(intent);
    }



}

