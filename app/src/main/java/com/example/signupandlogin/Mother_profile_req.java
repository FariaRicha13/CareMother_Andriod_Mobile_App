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


public class Mother_profile_req extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref,ref1,ref2,reqref,frndref,pref;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    DoctorMember doc;
    private FirebaseUser user;
    FirebaseDatabase database;
    Button update,connect,decline,chat;
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
        setContentView(R.layout.activity_mother_profile_req);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(Mother_profile_req.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);
        doc = new DoctorMember();
        connect=findViewById(R.id.buttonconnect);
        decline=findViewById(R.id.buttondecline);
        //chat=findViewById(R.id.buttonchat);
        //chat.setVisibility(View.GONE);

        String username = getIntent().getStringExtra("userkey1");
        String username1 = getIntent().getStringExtra("userkey");
        Log.wtf("mother",username);
        Log.wtf("caretaker",username1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname= user1.getUid();


        // value = String.valueOf(pname.child("n//ame").get());


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
                        reqref = FirebaseDatabase.getInstance().getReference("caretaker_req");

                        frndref = FirebaseDatabase.getInstance().getReference("caretaker_mother");
                        current_state="not_connect";
                        checkExistence(User);

                        connect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (current_state.equals("not_connect")) {
                                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                    hashMap.put("status", "pending");
                                    hashMap.put("caretaker",username1);
                                    hashMap.put("mother",key);

                                    reqref.child(username1).child(key).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            Log.wtf("key",key);
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Mother_profile_req.this, "Request has been sent", Toast.LENGTH_SHORT).show();
                                                //reqref.child(username1).child(key);
                                                ReqMap2 map= new ReqMap2();
                                                map.setMid1(key.toString());
                                                map.setCid(username1.toString());
                                                //reqref.child((username1)).child(key).setValue(username1);

                                                decline.setVisibility(View.GONE);
                                                current_state = "pending";
                                                connect.setText("Cancel Request");
                                            } else {
                                                Toast.makeText(Mother_profile_req.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                if (current_state.equals("pending") || current_state.equals("declined")) {
                                    reqref.child(username1).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Mother_profile_req.this, "Request Cancelled", Toast.LENGTH_SHORT).show();

                                                current_state = "not_connect";
                                                connect.setText("Connect");
                                                decline.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(Mother_profile_req.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                if (current_state.equals("connected")) {
                                    HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
                                    hashMap1.put("status", "friends");
                                    hashMap1.put("patient",username1);
                                    hashMap1.put("doc",key);
                                    frndref.child(username1).child(key).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Mother_profile_req.this, "Friends", Toast.LENGTH_SHORT).show();
                                            //reqref.child(username1).child(key);

                                            //reqref.child((username1)).child(key).setValue(username1);

                                            decline.setText("Disconnect");
                                            current_state = "connected";
                                            connect.setText("Start Chat");
                                            connect.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(Mother_profile_req.this, ChatAct.class);

                                                    intent.putExtra("userkey",key);
                                                    startActivity(intent);
                                                }
                                            });

                                            //chat.setVisibility(View.VISIBLE);
                                        }
                                    });



                                }
                            }
                        });

                        decline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.wtf("username",username1);
                                Log.wtf("key",key);



                                frndref.child(username1).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Mother_profile_req.this, "Disconnected", Toast.LENGTH_SHORT).show();
                                        decline.setText("Disconnected");
                                        current_state = "not_connect";
                                        connect.setText("Connect");
                                        // chat.setVisibility(View.VISIBLE);
                                    }
                                });


                            }
                        });
                        Log.wtf("cuar",current_state);

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
                                    connect.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Mother_profile_req.this, ChatAct.class);

                                            intent.putExtra("userkey", key);
                                            startActivity(intent);
                                        }
                                    });

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
                                    connect.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Mother_profile_req.this, ChatAct.class);

                                            intent.putExtra("userkey", key);
                                            startActivity(intent);
                                        }
                                    });
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



