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


public class GraphHome2 extends AppCompatActivity {
    CardView BP;
    CardView pulse;
    CardView temp;
    CardView sleep;
    CardView report;

String username;

    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_home2);

       username = getIntent().getStringExtra("userkey1");
        BP = findViewById(R.id.BP);
        pulse = findViewById(R.id.Pulse);
        sleep = findViewById(R.id.Sleep);
       report = findViewById(R.id.report);
        temp = findViewById(R.id.Temp);




        BP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openBP(username);
            }
        });
        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReport(username);
            }
        });


        pulse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPulse(username);
            }
        });
      /*  step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStep();
            }
        });*/


        temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTemp(username);
            }
        });

    }



    public void openBP(String selected){
        Intent intent = new Intent(this, bpgraph2.class);
        intent.putExtra("userkey1",selected);
        //intent.putExtra("userkey",username);
        startActivity(intent);
    }
    public void openPulse(String selected){
        Intent intent = new Intent(this, PulseGraph2.class);

        intent.putExtra("userkey1",selected);
        //intent.putExtra("userkey",username);
        startActivity(intent);
    }

    public void openTemp(String selected){
        Intent intent = new Intent(this, TempGraph2.class);
        intent.putExtra("userkey1",selected);
     //intent.putExtra("userkey",username);
        startActivity(intent);
    }

    public void openReport(String selected){
        Intent intent = new Intent(this, ReportForDoc.class);
        intent.putExtra("userkey1",selected);
        //intent.putExtra("userkey",username);
        startActivity(intent);
    }



}

