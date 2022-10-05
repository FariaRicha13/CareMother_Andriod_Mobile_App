package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.DateTime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Mother_profile extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    EditText name;
    EditText dob;
    EditText week;
    EditText height;
    EditText weight;
    EditText phone;
    Button update;
    Calendar cal1,cal2;
    String mname,mdob,mweek,mheight,mweight,mphone;
    Date d;
    Date date1,date2;
    ArrayList<String>myArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_profile);
      //  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(Mother_profile.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
       // myListView.setAdapter(myArrayAdapter);
        name=(EditText) findViewById(R.id.name);
        dob=(EditText)findViewById(R.id.dob);
        height=(EditText)findViewById(R.id.height);
        week=(EditText)findViewById(R.id.week);
        weight=(EditText)findViewById(R.id.weight);
        phone=(EditText)findViewById(R.id.phone);
        String username = getIntent().getStringExtra("userkey");
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");
        ref = FirebaseDatabase.getInstance().getReference("User").child(username);
               // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();
         /* Date c = Calendar.getInstance().getTime();
        int d =c.getDay();
        int m =c.getMonth();
        int y = c.getYear();
        Log.w("date", String.valueOf(d));
        calendar2.set(y,m,d);
        int week1 = calendar1.get(Calendar.WEEK_OF_YEAR);
        int week2 = calendar2.get(Calendar.WEEK_OF_YEAR);
        int calendarWeekDifference = week2 - week1;*/

     ref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             mname = String.valueOf(snapshot.child("name").getValue());
             mdob  = String.valueOf(snapshot.child("dob").getValue());
             mweek  = String.valueOf(snapshot.child("week").getValue());
             mheight = String.valueOf(snapshot.child("height").getValue());
             mweight = String.valueOf(snapshot.child("weight").getValue());
             mphone = String.valueOf(snapshot.child("phone").getValue());

             try {
                 d = new SimpleDateFormat("dd/MM/yyyy").parse(mweek);
             } catch (ParseException e) {
                 e.printStackTrace();
             }
             Date c = Calendar.getInstance().getTime();
             /*int d1 = c.getDate();
             int m1 = c.getMonth();
             int y1 = c.getYear();
             int d2 = d.getDate();
             int m2 = d.getMonth();
             int y2 = d.getYear();
  int dw= (int) d.getTime();
             cal1.set(Calendar.YEAR,c.getYear());
             cal1.set(Calendar.MONTH,m1);
             cal1.set(Calendar.DATE,d1);
             cal2.set(Calendar.YEAR,y2);
             cal2.set(Calendar.MONTH,m2);
             cal2.set(Calendar.DATE,d2);*/




             name.setText("Name: "+ mname);
             dob.setText("DOB: "+mdob);
             week.setText("Week: "+ mweek);
             phone.setText("Phone: "+ mphone);
             height.setText("Height: "+ mheight);
             weight.setText("Weight: "+mweight);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });

    /*    ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/




        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEdit();
            }
        });

    }
    public void openEdit(){
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

}
