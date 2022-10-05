package com.example.signupandlogin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class doctor_profile4 extends AppCompatActivity {
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
        setContentView(R.layout.activity_doctor_profile4);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(doctor_profile4.this,android.R.layout.simple_list_item_1,myArrayList);
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
        Log.wtf("doctor",username);
        Log.wtf("patient",username1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname= user1.getUid();


        // value = String.valueOf(pname.child("n//ame").get());

        Log.wtf("patientval",pname);
        ref1 = FirebaseDatabase.getInstance().getReference("doctor");



        Query userQuery = FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("name");

        userQuery.equalTo(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                            // result
                            key = foodSnapshot.getKey();


                            //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                            ref2 = FirebaseDatabase.getInstance().getReference();


                            ref2.child("doctor").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {


                                    String a = String.valueOf(task.getResult().child("name").getValue().toString());
                                    Log.wtf("Chk", a);

                                }


                            });
                            //Log.wtf("Chk",chk);


                            //if(chk==username) break;
                            // doc.setUid(key);
                            // doc.setName(chk);
                            // Log.wtf("Chk",chk);
                            //Log.wtf("UID",User);
                            Log.wtf("UID", key);

                        }
                        User = key;
                        Log.wtf("ref", User);
                        ref = FirebaseDatabase.getInstance().getReference("doctor").child(User);

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
                }

        );


    }














}



