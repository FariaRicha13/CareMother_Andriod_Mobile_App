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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class caretaker_profile2 extends AppCompatActivity {
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
    // DatabaseReference pname;
    FirebaseUser user1;
    // DatabaseReference pname;
    String current_state;
    ArrayList<String>myArrayList = new ArrayList<>();
    MotherMember mother = new MotherMember();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_profile2);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(caretaker_profile2.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);
        doc = new DoctorMember();
        connect=findViewById(R.id.buttonconnect);
        decline=findViewById(R.id.buttondecline);

        String username = getIntent().getStringExtra("userkey1");
        String username1 = getIntent().getStringExtra("userkey");
        Log.wtf("mother",username);
        Log.wtf("caretaker",username1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname= user1.getUid();


        // value = String.valueOf(pname.child("n//ame").get());

        Log.wtf("patientval",pname);
        ref1 = FirebaseDatabase.getInstance().getReference("caretaker");



        Query userQuery = FirebaseDatabase.getInstance().getReference().child("caretaker").orderByChild("name");

        userQuery.equalTo(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot foodSnapshot: dataSnapshot.getChildren()) {
                            // result
                            key = foodSnapshot.getKey();



                            //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                            ref2 = FirebaseDatabase.getInstance().getReference();


                            ref2.child("caretaker").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

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
                        ref = FirebaseDatabase.getInstance().getReference("caretaker").child(User);
                        reqref = FirebaseDatabase.getInstance().getReference("caretaker_req");
                        frndref = FirebaseDatabase.getInstance().getReference("caretaker_mother");
                        //checkExistence(User);

                        connect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.wtf("currenty",current_state);

                                { HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
                                    hashMap1.put("status", "friends");
                                    hashMap1.put("caretaker",key);
                                    hashMap1.put("mother",pname);
                                    frndref.child(key).child(pname).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText( caretaker_profile2.this, "Friends", Toast.LENGTH_SHORT).show();
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

                        ref.addChildEventListener(new ChildEventListener() {
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



