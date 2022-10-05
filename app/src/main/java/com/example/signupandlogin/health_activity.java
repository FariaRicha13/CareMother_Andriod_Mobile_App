package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class health_activity extends AppCompatActivity {
    ListView myListView;
    DatabaseReference refp,reft,ref1,ref2,ref;
    //FirebaseDatabase database;
    //StorageReference firebaseStorage;
    Button update,sleep,step;
    TextView p1,t1;
    String value;
    String pulse,temp,s;
    StoreDB st,st1;
    int maxid,maxid2;
    ArrayList<String>myArrayList1 = new ArrayList<>();
    ArrayList<String>myArrayList2 = new ArrayList<>();
    dataappend mother = new dataappend();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_activity);
     //   ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(health_activity.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView)findViewById(R.id.listview_01);
        update=findViewById(R.id.update);
        sleep=findViewById(R.id.sleep);
        step=findViewById(R.id.step);
        p1 =(TextView) findViewById(R.id.p);
        t1 =(TextView) findViewById(R.id.t);
        st = new StoreDB();
        st1 = new StoreDB();       //final String[] value = new String[ 1 ];
       // String value;
       // myListView.setAdapter(myArrayAdapter);
        String username = getIntent().getStringExtra("userkey");
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");
        Date d = Calendar.getInstance().getTime();
        int c = d.getDate();
        String k = String.valueOf(c);
        ref = FirebaseDatabase.getInstance().getReference("Store");
        //ref.removeValue();
        ref1 =FirebaseDatabase.getInstance().getReference("Store").child("P");
       ref2 =FirebaseDatabase.getInstance().getReference("Store").child("T");
        refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        reft = FirebaseDatabase.getInstance().getReference("Sensor").child("T");
       // refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();

        sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSleep();
            }
        });
        step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStep();
            }
        });
        myArrayList1.clear();
        myArrayList2.clear();

        ref1.addValueEventListener(new ValueEventListener() {
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
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    maxid2 = (int) snapshot.getChildrenCount();



                }
                else {//
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      refp.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              pulse =  String.valueOf(snapshot.getValue());
              Log.w("Val",pulse);
              p1.setText("Pulse : "+pulse);
              myArrayList1.add(pulse);
              Date c = Calendar.getInstance().getTime();
              int d = c.getDate();
              int sum =0;
              for(int i =0 ; i<myArrayList1.size();i++)
              { int x = Integer.parseInt(myArrayList1.get(i));
                  sum = sum+i;

              }
              sum = sum/(12*60);

              st.setData(String.valueOf(sum));


              ref2.child(String.valueOf(maxid)).setValue(st);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {



          }
      });

        reft.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp =  String.valueOf(snapshot.getValue());
               myArrayList2.add(temp);
                t1.setText("Temperature : " + temp);
                Date c = Calendar.getInstance().getTime();

                int d = c.getDate();
                int sum =0;
                for(int i =0 ; i<myArrayList2.size();i++)
                { int x = Integer.parseInt(myArrayList2.get(i));
                    sum = sum+i;

                }
                sum = sum/(12*60);

                st1.setData(String.valueOf(sum));


                ref2.child(String.valueOf(maxid)).setValue(st1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

    }
    public void openSleep(){
        Intent intent = new Intent(this, sleep.class);
        startActivity(intent);
    }
    public void openStep(){
        Intent intent = new Intent(this, step_count.class);
        startActivity(intent);
    }


}
