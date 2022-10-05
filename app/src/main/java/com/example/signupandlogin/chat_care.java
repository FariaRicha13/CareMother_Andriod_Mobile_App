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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chat_care extends AppCompatActivity {
    ListView myListView;
    ListView myListView2;
    FirebaseUser user1;
    String username,key,User;
    DatabaseReference ref,ref2,refuser,refc,refu;
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
        setContentView(R.layout.activity_chat_care);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>( chat_care.this,android.R.layout.simple_list_item_1,myArrayList);

        myListView = (ListView)findViewById(R.id.listview_01);
       update=findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);

        user1 = FirebaseAuth.getInstance().getCurrentUser();
        username= user1.getUid();
        // String username = getIntent().getStringExtra("userkey");
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");

        Query userQuery = FirebaseDatabase.getInstance().getReference().child("caretaker_mother").child(username).orderByChild("caretaker");
        userQuery.equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    // result
                    key = foodSnapshot.getKey();
                    Log.w("kEY",key);

                    //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                    refuser = FirebaseDatabase.getInstance().getReference();


                    refuser.child("User").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

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
                Intent intent = new Intent( chat_care.this, Mother_profile5.class);
                intent.putExtra("userkey1",selected);
                //intent.putExtra("userkey",username);
                startActivity(intent);
            }
        });




    }

}
