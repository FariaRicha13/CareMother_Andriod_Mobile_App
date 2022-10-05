package com.example.signupandlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class step_count extends AppCompatActivity {
private double prevmag = 0;
DatabaseReference ref,refs,refstore,ref2,refst,refu,refw,refd;
FirebaseDatabase database;
FirebaseUser user1;
    Date  db1;
String pname;
public  String lmpd;
public  String weekcn;
TextView step,Tstep,cal;
ArrayList<Float>stepsum = new ArrayList<>();
    ArrayList<Float>sums = new ArrayList<>();
    Date cur_d = Calendar.getInstance().getTime();
    int y1,m1,d1,min;
    Date db,l;
private Integer stepcount = 0;
    private Integer stepinc = 0;
    public Integer prevcnt = 0;
    SensorManager sensorManager ;
    Sensor sensor ;
    int p =0;
    String mweek;
    Double Val;
    String lmp;
    private float dist = 0;
    private float cals = 0;
    StepHold s;
StepHold shold;
    String curd;
    int maxid2;
    float sum =0;
    int w1,w2,w,day1,day2,day;
    float a;
Boolean run = false;
private boolean running = false;
private float totalstep = 0f;

    private float prevsteps = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        step =(TextView) findViewById(R.id.steps);
        Tstep =(TextView) findViewById(R.id.TVstep);
        cal =(TextView) findViewById(R.id.TV_CALORIES);
shold = new StepHold();
        s = new StepHold();
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
        refs = database.getInstance().getReference().child("Store");
        refst = FirebaseDatabase.getInstance().getReference("Store").child("Step");
Log.w("Pname",pname);
        refu = FirebaseDatabase.getInstance().getReference("User").child(pname);
        y1 = cur_d.getYear()-100;
        m1 = cur_d.getMonth()+1;
        d1 = cur_d.getDate();
        min = cur_d.getMinutes();
        curd = String.valueOf(d1+"-"+m1+"-"+y1);
        Log.w("date cur",curd);
        Log.wtf("day", String.valueOf(d1));
        Log.wtf("mon", String.valueOf(m1));
        Log.wtf("year", String.valueOf(y1));
        Log.wtf("min", String.valueOf(min));

        Calendar calendarFrom= Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();

        refstore =  database.getInstance().getReference().child("Store").child("Step").child(curd);

        ref = database.getInstance().getReference().child("PhoneSensor");
        ref2 = database.getInstance().getReference().child("PhoneSensor").child("dist");


        refu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lmp = String.valueOf(snapshot.child("lmp").getValue());
                mweek =  String.valueOf(snapshot.child("week").getValue());
                refd = FirebaseDatabase.getInstance().getReference("Store").child("Step").child(curd);
                refd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //  Val = (Double) snapshot.getValue();
                       // refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S");
                        try {
                            db = new SimpleDateFormat("dd-MM-yy").parse(lmp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int y = db.getYear();
                        int m = db.getMonth();
                        int dt = db.getDate();
                        calendarFrom.set(Calendar.YEAR,(y-100));
                        calendarFrom.set(Calendar.MONTH,(m+1));
                        calendarFrom.set(Calendar.DAY_OF_MONTH,dt);
                        l = Calendar.getInstance().getTime();
                        int yy = l.getYear()-100;
                        Log.w("whattt", String.valueOf(yy));
                        calendarTo.set(Calendar.YEAR,yy);
                        calendarTo.set(Calendar.MONTH,l.getMonth()+1);
                        calendarTo.set(Calendar.DAY_OF_MONTH,l.getDate());
                        w1 = calendarFrom.get(Calendar.WEEK_OF_YEAR);
                        w2 = calendarTo.get(Calendar.WEEK_OF_YEAR);
                        w= (w2-w1)-5;
                        Log.w("lmp", String.valueOf(calendarFrom));
                        int dif =calendarFrom.get(Calendar.DAY_OF_WEEK);
                        day1 = calendarTo.get(Calendar.DAY_OF_WEEK);
                        // Date d2 =  calendarFrom.getTime();
                        Log.w("curday", String.valueOf(calendarTo));
                        if(day1<dif)
                        {
                            day2= (8-dif)+day1;
                        }
                        if(day>dif)
                        {
                            day2 = (8-day1)+dif;
                        }
                        if(day1==dif)
                        {
                            day2 =1;
                        }
                        refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S").child(mweek).child(String.valueOf(day2));

                        refw.setValue(dist);

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


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event!=null)
                {stepsum.clear();
                    Date d = Calendar.getInstance().getTime();
                    int t = d.getHours();
                    int min = d.getMinutes();
                    float x_acc = event.values[0];
                    float y_acc = event.values[1];
                    float z_acc = event.values[2];

                    double magnitude = Math.sqrt( x_acc* x_acc+y_acc*y_acc+z_acc*z_acc);
                    double magdel = magnitude-prevmag;
                    prevmag = magnitude;

                    if(magdel>3)
                    {
                       stepcount++;
                    }
                  // stepcount = prevcnt+stepinc;

                           if(t==0&&min==0)
                             {
    dist =0;
    stepcount =0;
    cals =0;
                           }
                    dist = (float) (stepcount*.67);
                    cals = (float)(stepcount*.04);
                    stepsum.add(dist);

                    a = stepsum.get(0);
                    Log.w("step sum",String.valueOf(a));

                    int mm = d.getDate();
                    int mon = d.getMonth()+1;
                    int yy = d.getYear()-100;

                    Log.w("Time", String.valueOf(t));
                    Log.w("mint", String.valueOf(min));
                    String chd;
                    chd = String.valueOf(mm+"-"+mon+"-"+yy);
                    Log.w("child date",chd);
                    if(curd.equals(chd))
                    {
                        Tstep.setText(stepcount.toString());
                        step.setText(String.valueOf(dist)+" Meters");
                        cal.setText(String.valueOf(cals)+" cal");
                       s.setDist(String.valueOf(dist));
                      float data = dist;
                        refstore.setValue(String.valueOf(data));
                       ref.setValue(s);
                       // refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S").child(String.valueOf(3));
                        //refw.child(String.valueOf(day2)).setValue(s);
                       // Log.w("st", String.valueOf(st.getData()));
                        //  stepsum.clear();
                    }







                  /* */
p=p+1;










                }


            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };


        //
        Log.w("biare", String.valueOf(a));
        /*ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                    stepcount = stepcount+prevcnt;
                    dist = (float) (stepcount*.67);
                    cals = (float)(stepcount*.04);

                    //Log.w("sum step", String.valueOf(sum));
                    //float s1 = sum/(60*60*24);




                }
                else {//
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        //prevcnt = stepcount;
        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount",stepcount);
        editor.apply();
    }
    protected void onStop()
    {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount",stepcount);
        editor.apply();
    }
    protected  void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepcount = sharedPreferences.getInt("stepCount",prevcnt);



    }

}