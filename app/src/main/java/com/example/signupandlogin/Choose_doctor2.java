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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Choose_doctor2 extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    Button update;
    String value,value1;
    ArrayList<String>myArrayList = new ArrayList<>();
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor2);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(Choose_doctor2.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);
        String username = getIntent().getStringExtra("userkey");
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");
        ref = FirebaseDatabase.getInstance().getReference("doctor");

        // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();


        ref.addChildEventListener(new ChildEventListener() {
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
        });
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = myArrayList.get(position);
                Log.wtf("my msg",selected);
                // Toast.makeText(context,"selected",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Choose_doctor2.this, doctor_profile4.class);
                intent.putExtra("userkey1",selected);
                intent.putExtra("userkey",username);
                startActivity(intent);
            }
        });





    }

}
