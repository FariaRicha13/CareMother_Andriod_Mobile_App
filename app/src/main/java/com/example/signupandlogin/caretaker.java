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


public class caretaker extends AppCompatActivity {
    CardView your_profile;
    CardView doc_profile;
    CardView schedule;
    CardView report;
    CardView chat;
    CardView ambulance;
    CardView location;
    CardView choose_mother;
    CardView mom_act;

    Button logout;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);


        your_profile = findViewById(R.id.your_profile);
        doc_profile = findViewById(R.id.doc_profile);
        schedule = findViewById(R.id.schedule);
        report = findViewById(R.id.report);
        chat = findViewById(R.id.chat);
        ambulance = findViewById(R.id.ambulance);
        location = findViewById(R.id.location);
        logout=findViewById(R.id.logout);
        choose_mother=findViewById(R.id.choose_mom);
        mom_act = findViewById(R.id.momact);
        String username;
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("caretaker");
        username=user.getUid();
        mAuth=FirebaseAuth.getInstance();


        your_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEdit(username);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               openAmbulance();
            }
        });
        doc_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDocInfo(username);
            }
        });

        choose_mother.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMomInfo(username);
            }
        });
        location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openLocation();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSchedule();
            }
        });
        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReport();
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChat();
            }
        });
        mom_act.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openActivity();
            }
        });



        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAuth.signOut();
                Intent intent = new Intent(caretaker.this,caretaker_login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    public void openEdit(String a){
        Intent intent = new Intent(this,Caretaker_profile.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }
    public void openDocInfo(String a){
        Intent intent = new Intent(this, Choose_doctor2.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }
    public void openMomInfo(String a){
        Intent intent = new Intent(this, Choose_mother.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }
    public void openAmbulance(){
        Intent intent = new Intent(this, ambulance.class);

        startActivity(intent);
    }
    public void openLocation(){
        Intent intent = new Intent(this, testmap.class);

        startActivity(intent);
    }
    public void openChat(){
        Intent intent = new Intent(this, chat_care.class);

        startActivity(intent);
    }
    public void openSchedule(){
        Intent intent = new Intent(this, schedule.class);

        startActivity(intent);
    }
    public void openReport(){
        Intent intent = new Intent(this,WeeklyAct2.class);

        startActivity(intent);
    }
    public void openActivity(){
        Intent intent = new Intent(this,checkHealth2.class);

        startActivity(intent);
    }



}

