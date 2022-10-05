package com.example.signupandlogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import android.app.DatePickerDialog;



public class caretaker_signup extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    private Button buttonsignup;
    private TextView logintext;
    String userid;
    DatePickerDialog datePickerDialog;
    private FirebaseUser user;
    EditText Name,Email,Phone,Relation;
    // Iterable<DataSnapshot> maxid=0;
    MotherMember mother;
    int maxid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_signup);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Phone=findViewById(R.id.phn_no);
        Relation=findViewById(R.id.rltn);


        buttonsignup=findViewById(R.id.buttonsave);
        logintext=findViewById(R.id.logintext);
        CaretakerMember caretaker = new CaretakerMember();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("caretaker");
        userid=user.getUid();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    maxid = (int) snapshot.getChildrenCount();



                }
                else {//
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caretaker.setName(Name.getText().toString());
                caretaker.setEmail(Email.getText().toString());
                caretaker.setPhone(Phone.getText().toString());
                caretaker.setRelation(Relation.getText().toString());

                ref.child(String.valueOf(userid)).setValue(caretaker);


            }
        });
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCaretker();

            }
        });

    }
    public void openCaretker(){
        Intent intent = new Intent(this, Caretaker_profile.class);
        intent.putExtra("key",userid);
        startActivity(intent);
    }
}
