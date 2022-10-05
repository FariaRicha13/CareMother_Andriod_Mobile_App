package com.example.signupandlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;


public class doc_request extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref;
    DatabaseReference ref2;
    Query ref3;
    Task<DataSnapshot> ref4;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    FirebaseUser user1;
    Button update;
    static  String m;
    int i;
    long a;
    String value, value1, docid, key,pid,docid1,v,key2;
    String pending = "pending";
    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<String> myArrayList1 = new ArrayList<>();
    Context context;
    setuid suid=new setuid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_request);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(doc_request.this, android.R.layout.simple_list_item_1, myArrayList);
        myListView = (ListView) findViewById(R.id.listview_01);
        update = findViewById(R.id.update);
        myListView.setAdapter(myArrayAdapter);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        docid = user1.getUid();
        Log.wtf("doc",docid);
        ReqMap map = new ReqMap();
        docid1=map.getDocid();
        ref= FirebaseDatabase.getInstance().getReference("requests");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {

                    v = snapshot.getKey();
                    //String s = myArrayList1.get(i);
                    //ref3=  FirebaseDatabase.getInstance().getReference("requests").child(myArrayList1.get(i)).orderByChild("patient");

                    Log.wtf("pif", v);
                    Query userQuery = FirebaseDatabase.getInstance().getReference().child("requests").child(v).orderByChild("doc");
                    ref3=FirebaseDatabase.getInstance().getReference("requests").child(v).child(docid).child("patient");


                    userQuery.equalTo(docid);
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.wtf("M", "K");
                            for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                                // result
                                key = foodSnapshot.getKey();
                                Log.wtf("key", key);
                                m = (String) foodSnapshot.child("patient").getValue();
                                ref2 = FirebaseDatabase.getInstance().getReference();






                            }
                            String l = suid.getUid();

                            Log.wtf("baire", l);

                            ref2 = FirebaseDatabase.getInstance().getReference("User").child(m);
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String value = (String) snapshot.child("name").getValue();
                                    myArrayList.add(value);
                                    myArrayAdapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }



                //Log.wtf("vals",myArrayList1.get(0));

                Log.wtf("pidfinal",m);


                //Log.wtf("m",pid);
            }



             // ref3=FirebaseDatabase.getInstance().getReference();






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

        /*Query userQuery = FirebaseDatabase.getInstance().getReference().child("requests").orderByChild("patient");
        userQuery.equalTo(v).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    // result
                    key = foodSnapshot.getKey();

                    Log.wtf("key", key);
                    Log.wtf("pid", v);

                    // ref3=FirebaseDatabase.getInstance().getReference("requests").child()




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


      /*  Log.wtf("child",v);

         {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                    ref2 = FirebaseDatabase.getInstance().getReference();


                    ref2.child("requests").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {


                            String a  = String.valueOf(task.getResult().child("name").getValue().toString());
                            Log.wtf("Chk",a);

                        }


                    });

                    Log.wtf("UID",key);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
      /*  ref2=FirebaseDatabase.getInstance().getReference("User").child(v);
        ref2.addChildEventListener(new ChildEventListener() {
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
                Intent intent = new Intent(doc_request.this, Mother_profile2.class);
                intent.putExtra("userkey1",selected);
                //intent.putExtra("userkey",username);
                startActivity(intent);
            }
        });

    }
}








