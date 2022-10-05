package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Mother_profile2 extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref,ref1,ref2,reqref,frndref,pref;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    DoctorMember doc;
    private FirebaseUser user;
    FirebaseDatabase database;
    Button update,connect,decline;
    String keys;
    String User;
    String parent;
    String chk;
    String key,value,pname;
    private EditText name,age,height,weight,temp,pulse,bp,sleep,step,phone,week;
    String mname;
    String mdob;
    String mheight;
    String mweight;
    String mphone,mweek;
    // DatabaseReference pname;
    FirebaseUser user1;
    // DatabaseReference pname;
    String current_state;
    ArrayList<String>myArrayList = new ArrayList<>();
    MotherMember mother = new MotherMember();

    Date dob1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_profile2);
      //  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(Mother_profile2.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
        //myListView.setAdapter(myArrayAdapter);
        doc = new DoctorMember();
        connect=findViewById(R.id.buttonconnect);
        decline=findViewById(R.id.buttondecline);
        name=(EditText) findViewById(R.id.name);
        age=(EditText)findViewById(R.id.age);
        height=(EditText)findViewById(R.id.height);
        week=(EditText)findViewById(R.id.week);
        weight=(EditText)findViewById(R.id.weight);
        phone=(EditText)findViewById(R.id.phone);

        String username = getIntent().getStringExtra("userkey1");
        String username1 = getIntent().getStringExtra("userkey");
        Log.wtf("doctor",username);
        Log.wtf("patient",username1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname= user1.getUid();


        // value = String.valueOf(pname.child("n//ame").get());

        Log.wtf("patientval",pname);
        ref1 = FirebaseDatabase.getInstance().getReference("User");



        Query userQuery = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("name");

        userQuery.equalTo(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot foodSnapshot: dataSnapshot.getChildren()) {
                            // result
                            key = foodSnapshot.getKey();



                            //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                            ref2 = FirebaseDatabase.getInstance().getReference();


                            ref2.child("User").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {


                                    String a  = String.valueOf(task.getResult().child("name").getValue().toString());
                                    Log.wtf("Chk",a);

                                }


                            });
                            //Log.wtf("Chk",chk);


                            //if(chk==username) break;
                            // doc.setUid(key);
                            // doc.setName(chk);
                            // Log.wtf("Chk",chk);
                            //Log.wtf("UID",User);
                            Log.wtf("UID",key);

                        }

                        User = key;
                        Log.wtf("ref",User);
                        ref = FirebaseDatabase.getInstance().getReference("User").child(User);
                        reqref = FirebaseDatabase.getInstance().getReference("requests");
                        frndref = FirebaseDatabase.getInstance().getReference("friends");
                        //checkExistence(User);

                        connect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.wtf("currenty",current_state);

                                { HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
                                    hashMap1.put("status", "friends");
                                    hashMap1.put("patient",key);
                                    hashMap1.put("doc",pname);
                                    frndref.child(key).child(pname).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Mother_profile2.this, "Friends", Toast.LENGTH_SHORT).show();
                                            //reqref.child(username1).child(key);

                                            //reqref.child((username1)).child(key).setValue(username1);

                                            decline.setText("Disconnect");
                                            current_state = "Connected";
                                            connect.setVisibility(View.GONE);
                                            reqref.child(key).child(pname).removeValue();

                                        }
                                    });




                                        }
                                        Log.wtf("msd",current_state);

                                }



                            });

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
                                    dob1 = new SimpleDateFormat("dd/MM/yyyy").parse(mdob);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date c = Calendar.getInstance().getTime();
                                int d = c.getYear()-dob1.getYear();
                                Log.w("age", String.valueOf(d));
                                name.setText("Name: "+ mname);
                                age.setText("Age: "+ d);
                                week.setText("Week:"+mweek);
                                phone.setText("Phone: "+ mphone);
                                height.setText("Height: "+ mheight);
                                weight.setText("Weight: "+mweight);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                    private void checkExistence(String user) {
                        Log.wtf("Userki",user);
                        User=user;

                        frndref.child(username1).child(User).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    current_state = "friend";
                                    connect.setText("Start Chat");
                                    decline.setText("Disconnect");
                                    decline.setVisibility(View.VISIBLE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        frndref.child(User).child(username1).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    current_state = "friend";
                                    connect.setText("Start Chat");
                                    decline.setText("Disconnect");
                                    decline.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        reqref.child(username1).child(User).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    if (snapshot.child("status").getValue().toString().equals("pending")) {
                                        current_state = "pending";
                                        connect.setText("Cancel Request");
                                        decline.setVisibility(View.GONE);
                                    }
                                    if (snapshot.child("status").getValue().toString().equals("decline")) {
                                        current_state = "decline";
                                        connect.setText("Cancel Request");
                                        decline.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if (current_state.equals("not_connect")) {
                            current_state = "not_connect";
                            connect.setText("Connect");
                            decline.setVisibility(View.GONE);
                        }
                    }


                }


        );


    }














}



