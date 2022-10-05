package com.example.signupandlogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Random;



public class doctor_signup2 extends AppCompatActivity {
    FirebaseDatabase database;
    String userid;
    private FirebaseUser user;

    DatabaseReference ref;
    private Button buttonsignup;
    private TextView logintext;
    EditText Name,Phone,Hospital,BMDC;
    // Iterable<DataSnapshot> maxid=0;
    DoctorMember doctor;



    int maxid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup2);
        Name=findViewById(R.id.name);
        Phone=findViewById(R.id.phn_no);
        Hospital=findViewById(R.id.working_hospital);
        BMDC=findViewById(R.id.bmdc_number);
        buttonsignup=findViewById(R.id.savebutton);
        logintext=findViewById(R.id.logintext);
        doctor = new DoctorMember();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("doctor");
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
                doctor.setName(Name.getText().toString());
                doctor.setPhone(Phone.getText().toString());
                doctor.setHospital(Hospital.getText().toString());
                doctor.setBMDC(BMDC.getText().toString());

                ref.child(String.valueOf(userid)).setValue(doctor);

            }
        });
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDoctor();

            }
        });



    }
    public void openDoctor(){
        Intent intent = new Intent(this, doctor.class);
        intent.putExtra("key",userid);
        startActivity(intent);
    }
}
