package com.example.signupandlogin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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


public class TempGraph2 extends AppCompatActivity {
    LineChart temp;
    FirebaseUser user1;
    String pname,mweek;
    DatabaseReference ref,ref2,reffrnd,refp,refuser;
    ArrayList<String> labelsNames;
    //LineDataSet lineDataSet = new LineDataSet(null,null);
//ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    ArrayList<Entry>dataVals = new ArrayList<Entry>();
    ArrayList<DataPoint> dataPoints = new ArrayList<>();
    //public  ArrayList<Entry>dataVals ;
    int a;
    int n =0;
    String key,User;
    String mom,val;
    ArrayList<Integer> myArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph2);
        temp = findViewById(R.id.tempchart);


        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
//        refp = FirebaseDatabase.getInstance().getReference("Dummy").child("BP").child(mweek);

        labelsNames = new ArrayList<>();

        refp = FirebaseDatabase.getInstance().getReference("Store").child("Pulse");
        reffrnd =  FirebaseDatabase.getInstance().getReference("caretaker_mother").child(pname);
        String username = getIntent().getStringExtra("userkey1");
        String username1 = getIntent().getStringExtra("userkey");
        Log.wtf("doctor",username);
        Log.wtf("patient",username1);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname= user1.getUid();

        Query userQuery = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("name");
        userQuery.equalTo(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                            // result
                            key = foodSnapshot.getKey();


                            //ref2 = FirebaseDatabase.getInstance().getReference("doctor").child(User).child("name");
                            refuser = FirebaseDatabase.getInstance().getReference();


                            refuser.child("User").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {


                                    String a = String.valueOf(task.getResult().child("name").getValue().toString());
                                    Log.wtf("Chk", a);

                                }


                            });
                        }
                        User = key;
                        Log.wtf("ref", User);
                        ref = FirebaseDatabase.getInstance().getReference("User").child(User);


                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                mweek = String.valueOf(snapshot.child("week").getValue());
                                ref2 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("T").child(mweek);
                                ref2.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        myArrayList.clear();
                                        dataVals.clear();

                                        String value1 = String.valueOf(snapshot.getValue());


                                        a = Integer.valueOf(value1);
                                        Log.w("Val from firebase", value1);

                                        myArrayList.add(a);


                                        n = n + 1;

                                        int s = myArrayList.get(0);


                                        dataPoints.add(new DataPoint(n, s));


                                        for (int i = 0; i < dataPoints.size(); i++) {

                                            int x = dataPoints.get(i).getX();


                                            int y = dataPoints.get(i).getY();
                                            Log.w("X VAL", String.valueOf(y));

                                            Log.w("day no", String.valueOf(i));

                                            dataVals.add(new Entry(i + 0, y + 0));


                                        }
                                        LineDataSet lineDataSet = new LineDataSet(dataVals, "Temperature");
                                        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
                                        iLineDataSets.add(lineDataSet);

                                        LineData lineData = new LineData(iLineDataSets);
                                        Log.w("Val", String.valueOf(lineData.getDataSets()));

                                        temp.setData(lineData);


                                        temp.invalidate();
                                        temp.setNoDataText("No Tempearture Found");
                                        lineDataSet.setColor(Color.BLUE);
                                        lineDataSet.setCircleColor(Color.GREEN);
                                        lineDataSet.setDrawCircleHole(true);
                                        lineDataSet.setDrawCircles(true);
                                        lineDataSet.setLineWidth(5);
                                        lineDataSet.setCircleRadius(10);
                                        lineDataSet.setCircleHoleRadius(10);
                                        lineDataSet.setValueTextSize(10);
                                        lineDataSet.setValueTextColor(Color.BLACK);


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
                                temp.clear();

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


                }






