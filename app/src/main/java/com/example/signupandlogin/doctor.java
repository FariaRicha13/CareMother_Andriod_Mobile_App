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


public class doctor extends AppCompatActivity {
    CardView your_profile;
    CardView patient_profile;
    CardView schedule;
    CardView report;
    CardView chat;
    CardView req;

    Button logout;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        your_profile = findViewById(R.id.your_profile);
        patient_profile = findViewById(R.id.patient_profile);
        schedule = findViewById(R.id.schedule);
        report = findViewById(R.id.report);
        chat = findViewById(R.id.chat);
        req=findViewById(R.id.req);

        logout=findViewById(R.id.logout);
        String username;
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("doctor");
        username=user.getUid();


        your_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEdit(username);
            }
        });

        req.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReq();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openScehdule();
            }
        });
        patient_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               openProfile();
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChat();
            }
        });

        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReport();
            }
        });




        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Signout();
            }
        });
    }


    public void Signout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),doctorlogin.class));
        finish();

    }
    public void openEdit(String a){
        Intent intent = new Intent(this,doctor_profile.class);
        intent.putExtra("userkey",a);
        startActivity(intent);
    }
    public void openReq(){
        Intent intent = new Intent(this,doc_request.class);

        startActivity(intent);
    }
    public void openReport(){
        Intent intent = new Intent(this,WeeklyDocHom.class);

        startActivity(intent);
    }
    public void openProfile(){
        Intent intent = new Intent(this,View_patient.class);

        startActivity(intent);
    }
    public void openChat(){
        Intent intent = new Intent(this,ChatRoomDoc.class);

        startActivity(intent);
    }
    public void openScehdule(){
        Intent intent = new Intent(this,schedule.class);

        startActivity(intent);
    }





}

