package com.example.signupandlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


public class ChatActMom extends AppCompatActivity {
    ListView myListView;
    ListView myListView2;
FirebaseUser user1;
String username,key,User;
    DatabaseReference ref,ref2,refuser,refc;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    Button update;
    String value,value1;
    ArrayList<String>myArrayList = new ArrayList<>();
    ArrayList<String>myArrayList1 = new ArrayList<>();
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_act_mom);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>( ChatActMom.this,android.R.layout.simple_list_item_1,myArrayList);
        ArrayAdapter<String> myArrayAdapter1 = new ArrayAdapter<String>( ChatActMom.this,android.R.layout.simple_list_item_1,myArrayList1);
        myListView = (ListView)findViewById(R.id.listview_01);
        myListView2 = (ListView)findViewById(R.id.listview_02);
        update=findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);
        myListView2.setAdapter(myArrayAdapter1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        username= user1.getUid();
        // String username = getIntent().getStringExtra("userkey");
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");
        ref = FirebaseDatabase.getInstance().getReference("friends");
        Query userQuery = FirebaseDatabase.getInstance().getReference().child("friends").child(username).orderByChild("patient");
        userQuery.equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    // result
                    key = foodSnapshot.getKey();
Log.w("kEY",key);

                    //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                    refuser = FirebaseDatabase.getInstance().getReference();


                    refuser.child("doctor").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {


                            String a = String.valueOf(task.getResult().child("name").getValue().toString());
                            Log.wtf("Chk", a);

                            myArrayList.add(a);
                            myArrayAdapter.notifyDataSetChanged();

                        }


                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref = FirebaseDatabase.getInstance().getReference("caretaker_mother");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    value1 = snapshot.getKey();
                    ref2=FirebaseDatabase.getInstance().getReference("caretaker").child(value1);
                    ref2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            value = String.valueOf(snapshot.child("name").getValue());
                            myArrayList1.add(value);
                            myArrayAdapter1.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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


        // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();


      /*  ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                value = String.valueOf(snapshot.child("name").getValue());
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
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = myArrayList.get(position);
                Log.wtf("my msg",selected);
                // Toast.makeText(context,"selected",Toast.LENGTH_LONG).show();
                Intent intent = new Intent( ChatActMom.this, DocProfile3.class);
                intent.putExtra("userkey1",selected);
                //intent.putExtra("userkey",username);
                startActivity(intent);
            }
        });
        myListView2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = myArrayList1.get(position);
                Log.wtf("my msg",selected);
                // Toast.makeText(context,"selected",Toast.LENGTH_LONG).show();
                Intent intent = new Intent( ChatActMom.this, caretaker_profile3.class);
                intent.putExtra("userkey1",selected);
                //intent.putExtra("userkey",username);
                startActivity(intent);
            }
        });





    }

}
