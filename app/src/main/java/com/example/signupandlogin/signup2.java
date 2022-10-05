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
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class signup2 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    private Button buttonsignup;
    private TextView logintext;
    String userid;
    DateTime dateTime1,dateTime2;
    DatePickerDialog datePickerDialog;
    private FirebaseUser user;
    EditText Phone,DOB,Height,Weight,Name,week,weekm,LMP;
    Date LPM;
    Calendar calendar1,calendar2;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
int  weeks;
    // Iterable<DataSnapshot> maxid=0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
    MotherMember mother;
    Date date1;
    Date date2;
    int maxid ;
    int w1,w2,w;
    Calendar calendarFrom = Calendar.getInstance();
    Date c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        Phone=findViewById(R.id.phn_no);
        DOB=findViewById(R.id.dob);
        Height=findViewById(R.id.height);
        Weight=findViewById(R.id.weight);
        DOB =  findViewById(R.id.dob);
        Name=findViewById(R.id.name);
        week=findViewById(R.id.weeks);
        LMP=findViewById(R.id.weeks);

        Calendar calendarFrom= Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        // perform click event on edit text
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar p = Calendar.getInstance();
                int mYear = p.get(Calendar.YEAR); // current year
                int mMonth = p.get(Calendar.MONTH); // current month
                int mDay = p.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(signup2.this,
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
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar p = Calendar.getInstance();
                int mYear =p.get(Calendar.YEAR); // current year
                int mMonth = p.get(Calendar.MONTH); // current month
                int mDay = p.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(signup2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                               // calendar1.set(year,monthOfYear,dayOfMonth);


                                //calendar1.set(year,monthOfYear,dayOfMonth);
                                LMP.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                                calendarFrom.set(Calendar.YEAR,year);
                                calendarFrom.set(Calendar.MONTH,monthOfYear);
                                calendarFrom.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                c = Calendar.getInstance().getTime();


                                calendarTo.add(Calendar.YEAR, c.getYear());
                                calendarTo.set(Calendar.MONTH,c.getMonth()+1);
                                calendarTo.set(Calendar.DAY_OF_MONTH,c.getDate());
                                w1 = calendarFrom.get(Calendar.WEEK_OF_YEAR);
                                w2 = calendarTo.get(Calendar.WEEK_OF_YEAR);
                                 w= w2-w1-4;
                                Log.wtf("w", String.valueOf(w-3));




                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });





       // week.setText(""+w);


        buttonsignup=findViewById(R.id.buttonsave);
        logintext=findViewById(R.id.logintext);
        mother = new MotherMember();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getInstance().getReference().child("User");
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
                mother.setName(Name.getText().toString());
                mother.setPhone(Phone.getText().toString());
                mother.setDOB(DOB.getText().toString());
                mother.setWeek(String.valueOf(w));
                mother.setHeight(Height.getText().toString());
                mother.setWeight(Weight.getText().toString());
                mother.setLMP(LMP.getText().toString());


                ref.child(String.valueOf(userid)).setValue(mother);


            }
        });
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMother();

            }
        });

    }
    public void openMother(){
        Intent intent = new Intent(this, Mother.class);
        intent.putExtra("key",userid);
        startActivity(intent);
    }

}
