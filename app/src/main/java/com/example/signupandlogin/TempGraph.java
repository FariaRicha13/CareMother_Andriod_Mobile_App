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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TempGraph extends AppCompatActivity {
    LineChart temp;
    FirebaseUser user1;
    String pname,mweek;
    DatabaseReference ref,ref2;

    //LineDataSet lineDataSet = new LineDataSet(null,null);
//ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    ArrayList<Entry>dataVals = new ArrayList<Entry>();
    ArrayList<DataPoint> dataPoints = new ArrayList<>();
    //public  ArrayList<Entry>dataVals ;
    int a;
    int n =0;
    ArrayList<Integer> myArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph);
        temp = findViewById(R.id.tempchart);


        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
//        refp = FirebaseDatabase.getInstance().getReference("Dummy").child("BP").child(mweek);
        ref = FirebaseDatabase.getInstance().getReference("User").child(pname);




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



                        n = n+1;

                        int s = myArrayList.get(0);





                        dataPoints.add(new DataPoint(n,s));


                        for(int i =0; i <dataPoints.size();i++)
                        {

                            int x = dataPoints.get(i).getX();


                            int y = dataPoints.get(i).getY();
                            Log.w("X VAL", String.valueOf(y));

                            Log.w("day no", String.valueOf(i));

                            dataVals.add(new Entry(i+0,y+0));





                        }
                        LineDataSet lineDataSet = new LineDataSet(dataVals,"Temperature");
                        ArrayList<ILineDataSet>iLineDataSets = new ArrayList<>();
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
}