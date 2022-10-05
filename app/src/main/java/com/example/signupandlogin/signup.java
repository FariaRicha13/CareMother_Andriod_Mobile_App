package com.example.signupandlogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;



public class signup extends AppCompatActivity {
    FirebaseDatabase database;
    String userid,mweek;
    private FirebaseUser user;
    DatePickerDialog datePickerDialog;
    DatabaseReference ref,ref1;
    private Button buttonsignup;
    private TextView logintext;
    EditText Phone,DOB,Height,Weight,Name;

    // Iterable<DataSnapshot> maxid=0;
    MotherMember mother;

    int maxid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Phone=findViewById(R.id.phn_no);
        //DOB=findViewById(R.id.dob);
        Height=findViewById(R.id.height);
        Weight=findViewById(R.id.weight);
        DOB =  findViewById(R.id.dob);
        Name=findViewById(R.id.name);
        // perform click event on edit text
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(signup.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                DOB.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        buttonsignup=findViewById(R.id.buttonsignup);
        logintext=findViewById(R.id.logintext);
        mother = new MotherMember();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("User");

        userid=user.getUid();
        ref1 = database.getInstance().getReference().child("User").child(userid);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mweek = String.valueOf(snapshot.child("week").getValue());
                mother.setWeek(mweek);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                mother.setName(Name.getText().toString());
                mother.setPhone(Phone.getText().toString());
                mother.setDOB(DOB.getText().toString());
                mother.setHeight(Height.getText().toString());
                mother.setWeight(Weight.getText().toString());
                Log.w("week",mweek);
                mother.setWeek(mweek);
                ref.child(String.valueOf(userid)).setValue(mother);

            }
        });
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMother_profile();

            }
        });



    }
    public void openMother_profile(){
        Intent intent = new Intent(this, Mother_profile.class);
        intent.putExtra("key",userid);
        startActivity(intent);
    }
}
