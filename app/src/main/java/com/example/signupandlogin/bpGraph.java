package com.example.signupandlogin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bpGraph extends AppCompatActivity {
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet,barDataSet1;
    ArrayList<BarEntry> barentries = new ArrayList<>();
    ArrayList<BarEntry> barentries1 = new ArrayList<>();

    ArrayList<String> labelsNames;
    ArrayList<String> labelsNames1;
    ArrayList<DataPoint> dataPoints = new ArrayList<>();
    ArrayList<DataPoint> dataPoints1 = new ArrayList<>();
    FirebaseUser user1;
    String pname, weekf, mweek;
    int a,b;
    int n =0;

    ArrayList<Integer> myArrayList = new ArrayList<>();
    ArrayList<Integer> myArrayList1 = new ArrayList<>();




    DatabaseReference refp, ref, ref2, ref3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_graph);
  barChart = findViewById(R.id.bpgraph);
  barentries = new ArrayList<>();
  barentries1 = new ArrayList<>();
  labelsNames = new ArrayList<>();



        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
//        refp = FirebaseDatabase.getInstance().getReference("Dummy").child("BP").child(mweek);
        ref = FirebaseDatabase.getInstance().getReference("User").child(pname);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mweek = String.valueOf(snapshot.child("week").getValue());
                ref2 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("BP").child(mweek);
                ref2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        myArrayList.clear();
                        String value1 = String.valueOf(snapshot.child("s").getValue());



                        a = Integer.valueOf(value1);


                        myArrayList.add(a);



                      n = n+1;

                        int s = myArrayList.get(0);
                        myArrayList1.clear();
                        String value2 = String.valueOf(snapshot.child("d").getValue());
                        b = Integer.valueOf(value2);
                        myArrayList1.add(b);
                        int d = myArrayList1.get(0);
                        dataPoints1.add(new DataPoint(n,d));


                        dataPoints.add(new DataPoint(n,s));

                        int j = 1;
                        int k =0 ;

                        for(int i =0; i <dataPoints.size();i++)
                        {
                            int x = dataPoints.get(i).getX();
                            int x1 = dataPoints1.get(i).getX();
                            Log.w("X val ", String.valueOf(x));
                            Log.w("X val ", String.valueOf(x1));

                            int y = dataPoints.get(i).getY();
                            int y1 = dataPoints1.get(i).getY();
                            Log.w("dia ", String.valueOf(y1));
                            Log.w("sys ", String.valueOf(y));
                            barentries.add(new BarEntry(j,y));
                            barentries1.add(new BarEntry(k,y1));
                            Log.w("day no", String.valueOf(i));
                            labelsNames.add("day "+ i);
                            j= j+2;
                            k = k+2;



                        }



                        BarDataSet barDataSet = new BarDataSet(barentries, "systol");

                        BarDataSet barDataSet1 = new BarDataSet(barentries1, "diastol");
                        barDataSet1.setColor(Color.GREEN);


                        barDataSet.setColor(Color.BLUE);

                        Description description = new Description();
                        description.setText("Blood Pressure");
                        barChart.setDescription(description);
                        BarData barData = new BarData(barDataSet);
                         barData.addDataSet(barDataSet1);
                        barChart.setData(barData);
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));

                        xAxis.setPosition(XAxis.XAxisPosition.TOP);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setDrawGridLines(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(labelsNames.size());
                        labelsNames.clear();
                        xAxis.setLabelRotationAngle(270);

                        barChart.animateY(2000);
                        barChart.animateX(2000);
                        barChart.invalidate();










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


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


}

