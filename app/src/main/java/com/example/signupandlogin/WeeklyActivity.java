package com.example.signupandlogin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
import java.util.Formatter;

public class WeeklyActivity extends AppCompatActivity {
    private TextView name, age, height, weight, temp, pulse, bp, sleep, step, phone, week;
    String cname, cage, cweek, cphone, cheight, cweight, cpulse, ctemp, cstep, csleep, cbp;
    View v1;
    Button pdf;
    double s;
    double sumS =0;
    double sumP=0;
    double sumT=0;
    double sumSl=0;
    double sumD=0;
    double sumB=0;
    String pname;
    String mname;
    String mdob;
    int countS =0;
    int countP=0;
    int countT=0;
    int countSl =0;
    int countD =0;
    int countB=0;

    String mheight;
    String mweight;
    String mphone, mweek;
    String mbp;
    String mpulse;
    String mtemp;
    String mstep;
    String msleep;
   Double x;
    FirebaseUser user1;
    Date dob1;

    DatabaseReference ref, ref2, ref3, ref4, ref5, ref6,refuser;
    Integer a,b;
    Bitmap bmp, scaledbmp;
    ArrayList<Double> myArrayList = new ArrayList<>();
    ArrayList<Double> myArrayList1 = new ArrayList<>();
    ArrayList<Double> myArrayList2= new ArrayList<>();
    ArrayList<Integer> myArrayList3 = new ArrayList<>();
    ArrayList<Integer> myArrayList4 = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        v1 = findViewById(R.id.view);

        name =(TextView) findViewById(R.id.name);
        age = (TextView)findViewById(R.id.age);
        height = (TextView)findViewById(R.id.height);
        week = (TextView)findViewById(R.id.week);
        weight = (TextView) findViewById(R.id.weight);
        sleep = (TextView) findViewById(R.id.age);
        pulse = (TextView) findViewById(R.id.pulse);
        bp = (TextView)findViewById(R.id.bp);
        sleep = (TextView) findViewById(R.id.sleep);
        step =(TextView) findViewById(R.id.step);
        temp = (TextView)findViewById(R.id.temp);
        //pdf = findViewById(R.id.pdf);

        phone = (TextView) findViewById(R.id.phone);
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();

        ref = FirebaseDatabase.getInstance().getReference("User").child(pname);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mname = String.valueOf(snapshot.child("name").getValue());
                mdob = String.valueOf(snapshot.child("dob").getValue());
                mweek = String.valueOf(snapshot.child("week").getValue());
                mheight = String.valueOf(snapshot.child("height").getValue());
                mweight = String.valueOf(snapshot.child("weight").getValue());
                mphone = String.valueOf(snapshot.child("phone").getValue());

                try {
                    dob1 = new SimpleDateFormat("dd/MM/yyyy").parse(mdob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date c = Calendar.getInstance().getTime();
                Log.wtf("c year", String.valueOf(mdob));
                int d = c.getYear() - dob1.getYear();
                name.setText("Name: " + mname);
                age.setText("Age: " + d);
                week.setText("Weeks: " + mweek);
                phone.setText("Phone: " + mphone);
                height.setText("Height: " + mheight);
                weight.setText("Weight: " + mweight);
                ref2 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("P").child(mweek);
                ref3 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("T").child(mweek);
                ref4 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("BP").child(mweek);
                ref5 = FirebaseDatabase.getInstance().getReference("Dummy").child("Sleep").child(mweek);
                ref6 = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S").child(mweek);


                ref2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        myArrayList.clear();
                        String value = String.valueOf(snapshot.getValue());
                        Log.wtf("val", value);
                       double p = Integer.valueOf(value);
                        myArrayList.add(p);

                        for (int i = 0; i < myArrayList.size(); i++) {
                            countP= countP+1;
                            sumP = myArrayList.get(i) + sumP;
                        }
                      p = (sumP / countP);
                        Log.wtf("a", String.valueOf(p));
                        pulse.setText("Pulse: " +String.format("%.2f",p));


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
                ref3.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        myArrayList.clear();
                        String value = String.valueOf(snapshot.getValue());
                        Log.wtf("val", value);
                        double t = Integer.valueOf(value);
                        myArrayList.add(t);

                        for (int i = 0; i < myArrayList.size(); i++) {
                            countT = countT+1;
                            sumT = myArrayList.get(i) + sumT;
                        }
                        t = (sumT / countT);
                       // double t1 = Math.round(t,2);
                        Formatter formatter = new Formatter();

                        Log.wtf("a", String.valueOf(t));
                        temp.setText("Temperature: " + String.format("%.2f",t));

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
                ref4.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        myArrayList.clear();
                        myArrayList1.clear();
                        String value1 = String.valueOf(snapshot.child("s").getValue());
                        String value2 = String.valueOf(snapshot.child("d").getValue());

                        double s1 = Double.parseDouble(value1);
                        double d1 = Double.parseDouble(value2);

                        myArrayList.add(s1);
                        myArrayList1.add(d1);

                        for (int i = 0; i < myArrayList.size(); i++) {
                            countB = countB+1;
                            sumB = myArrayList.get(i) + sumB;
                        }

                        for (int i = 0; i < myArrayList1.size(); i++) {
                                countD =countD+1;
                            sumD = myArrayList1.get(i) + sumD;
                        }
                        s1 = (sumB / countB);
                        d1=(sumD / countD);
                        Log.wtf("bp", String.valueOf(a));
                        bp.setText("Blood Pressure: " + String.format("%.2f",s1)+"/"+String.format("%.2f",d1));

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
                ref5.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        myArrayList3.clear();
                        myArrayList4.clear();
                        String value1 = String.valueOf(snapshot.child("hour").getValue());
                        String value2 = String.valueOf(snapshot.child("min").getValue());
                        a = Integer.valueOf(value1);
                        b=Integer.valueOf(value2);

                        myArrayList3.add(a);
                        myArrayList4.add(b);
                        int sum = 0;

                        int sum1 =0;
                        for (int i = 0; i < myArrayList3.size(); i++) {

                            sum = myArrayList3.get(i) + sum;
                        }

                        for (int i = 0; i < myArrayList4.size(); i++) {

                            sum1 = myArrayList4.get(i) + sum1;
                        }
                        a = (sum / myArrayList3.size());
                        b=(sum1 / myArrayList4.size());
                        Log.wtf("bp", String.valueOf(a));
                        sleep.setText("Sleep: " + a+" hours "+b + " minutes");

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
                sumS =0;

              ref6.addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    myArrayList2.clear();

                      x = (Double) snapshot.getValue();
                      myArrayList2.add(x);

                      for(int i=0;i<myArrayList2.size();i++)
                      {countS = countS+1;
                          sumS = myArrayList2.get(i)+sumS;
                      }
                     x =(sumS/countS);
                      step.setText("Movement: " + String.format("%.2f",x) +" Meters");


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



       /* pdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PdfDocument mypdf = new PdfDocument();
                Paint mypaint = new Paint();
                PdfDocument.PageInfo mypageinfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page mypage = mypdf.startPage(mypageinfo);

                cname = name.getText().toString();
                cage = age.getText().toString();
                cphone = phone.getText().toString();
                cweek = week.getText().toString();
                cheight = height.getText().toString();
                cweight = weight.getText().toString();
                cpulse = pulse.getText().toString();
                ctemp = temp.getText().toString();
                int x = 10, y = 25;
                mypage.getCanvas().drawText(cname, x, y, mypaint);
               /* mypage.getCanvas().drawText(cphone, x, y, mypaint);
                mypage.getCanvas().drawText(cage, x, y, mypaint);
                mypage.getCanvas().drawText(cweek, x, y, mypaint);
                mypage.getCanvas().drawText(cheight, x, y, mypaint);
                mypage.getCanvas().drawText(cweight, x, y, mypaint);
                mypage.getCanvas().drawText(ctemp, x, y, mypaint);
                mypage.getCanvas().drawText(cpulse, x, y, mypaint);*/
            /*    mypdf.finishPage(mypage);
                String myfilepath = (Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/report.pdf");

                File file = new File(myfilepath);
                try {
                    mypdf.writeTo(new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mypdf.close();


            }
        });*/

    }
}
