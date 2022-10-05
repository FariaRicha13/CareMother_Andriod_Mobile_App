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


public class Mother extends AppCompatActivity {
    CardView your_profile, choose_doc, activity, schedule, report, music, chat, request;
    Button logout;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);

        your_profile = findViewById(R.id.your_profile);
        choose_doc = findViewById(R.id.choose_doc);
        activity = findViewById(R.id.activity);
        schedule = findViewById(R.id.schedule);
        report = findViewById(R.id.report);
        chat = findViewById(R.id.chat);
        music = findViewById(R.id.music);
        logout=findViewById(R.id.logout);
        request=findViewById(R.id.req);
        String username;
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("User");
        username=user.getUid();
        mAuth=FirebaseAuth.getInstance();


        your_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEdit(username);
            }
        });
       activity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openActivity();
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

        choose_doc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDocInfo(username);
            }
        });

        music.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMusic();
            }
        });
        request.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReq();
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChat();
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              mAuth.signOut();
              Intent intent = new Intent(Mother.this,motherlogin.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
            }
        });
    }

    public void openMusic(){
        Intent intent = new Intent(this, Music.class);
        startActivity(intent);
    }
    public void openReq(){
        Intent intent = new Intent(this, mom_req.class);
        startActivity(intent);
    }

    public void openEdit(String a){
        Intent intent = new Intent(this, Mother_profile.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }
    public void openActivity(){
        Intent intent = new Intent(this,checkHealth.class);
        startActivity(intent);
    }
    public void openSchedule(){
        Intent intent = new Intent(this,schedule.class);
        startActivity(intent);
    }
    public void openReport(){
            Intent intent = new Intent(this,WeeklyReportHome.class);
            startActivity(intent);
    }
    public void openChat(){
        Intent intent = new Intent(this,ChatActMom.class);
        startActivity(intent);
    }
    public void openDocInfo(String a){
        Intent intent = new Intent(this, Choose_doctor.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }




}

