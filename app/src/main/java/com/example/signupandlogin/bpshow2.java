package com.example.signupandlogin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class bpshow2 extends AppCompatActivity {
    ListView myListView;
    DatabaseReference refp,reft,ref1,ref2,ref,refstore1,refstore2,reffrnd,refu,refw;
    FirebaseDatabase database;
FirebaseUser user1;
    //StorageReference firebaseStorage;
    int m1,d1,y1,min,a,b;
    Button update,sleep,step;
    TextView p1,t1,bp;
    String pname;
    int sumP=0;
    int countP=0;
    int sumD=0;
    int countD=0;
    String value;
    String curd;
    Date db,l;
    int w,w2,w1,day1,day2,day;
    String pulse,temp,s;
    Date cur_d = Calendar.getInstance().getTime();
    StoreDB st,st1;
    int maxid,maxid2;
    String lmp,mweek;
    Date current;
    String curDate;
    String value1,v1,v2;
    int a1;
    String mom,val;
    ArrayList<Integer>myArrayList1 = new ArrayList<>();
    ArrayList<Integer>myArrayList2 = new ArrayList<>();
    dataappend mother = new dataappend();
    FirebaseAuth fAuth;
    public static final String CHANNEL_ID = "10001" ;
    private final static String default_channel_id = "default";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpshow2);
        //   ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(health_activity.this,android.R.layout.simple_list_item_1,myArrayList);

        bp =(TextView) findViewById(R.id.bp);
        // t1 =(TextView) findViewById(R.id.t);
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
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Store");
        reffrnd =  FirebaseDatabase.getInstance().getReference("caretaker_mother").child(pname);
        Query userQuery = FirebaseDatabase.getInstance().getReference().child("caretaker_mother").child(pname).orderByChild("mother");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                                                             // result
                                                             mom = foodSnapshot.getKey();
                                                             Log.wtf("key", mom);
                                                             val = (String) foodSnapshot.child("mother").getValue();
                                                             Log.w("Mother", val);
                                                             //ref1 =FirebaseDatabase.getInstance().getReference("Store").child("Dia");
                                                            // ref2 =FirebaseDatabase.getInstance().getReference("Store").child("Sys");
                                                             refp = FirebaseDatabase.getInstance().getReference("Sensor");
                                                             reft = FirebaseDatabase.getInstance().getReference("Sensor");
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

                                                             refstore1 =  database.getInstance().getReference().child("Store").child("BP").child("D").child(curd);
                                                             refstore2 =  database.getInstance().getReference().child("Store").child("BP").child("S").child(curd);
                                                             Calendar calendarFrom= Calendar.getInstance();
                                                             Calendar calendarTo = Calendar.getInstance();

                                                             myArrayList1.clear();
                                                             myArrayList2.clear();

                                                             refu = FirebaseDatabase.getInstance().getReference("User").child(val);

                                                             refu.addValueEventListener(new ValueEventListener() {
                                                                 @Override
                                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                     lmp = String.valueOf(snapshot.child("lmp").getValue());
                                                                     mweek =  String.valueOf(snapshot.child("week").getValue());
                                                                 }

                                                                 @Override
                                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                                 }
                                                             });

                                                             ref.addChildEventListener(new ChildEventListener() {
                                                                 @RequiresApi(api = Build.VERSION_CODES.O)
                                                                 @Override
                                                                 public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                                  //   String valued = String.valueOf(snapshot.getValue());
                                                                  //   Log.wtf("val", valued);
                                                                   //  int x  = Integer.valueOf(valued);
                                                                    // Log.wtf("x", String.valueOf(x));
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
                                                                     refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("BP").child(mweek);

                                                                     Log.w("wekday", String.valueOf(dif));
                                                                     Log.w("firstday of cur week", String.valueOf(day2));

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



                                                             reft.addValueEventListener(new ValueEventListener() {
                                                                 @Override
                                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                     myArrayList1.clear();
                                                                     myArrayList2.clear();
                                                                     Date d = Calendar.getInstance().getTime();
                                                                     int t = d.getHours();
                                                                     int min = d.getMinutes();


                                                                     value = String.valueOf(snapshot.child("Dia").getValue());
                                                                     value1 = String.valueOf(snapshot.child("Sys").getValue());
                                                                     Log.wtf("val", value); Log.wtf("val", value1);

                                                                     a = Integer.valueOf(value);
                                                                     a1 = Integer.valueOf(value1);

                                                                     bp.setText("BP: " + a+"/"+a1);
                                                                     Log.w("Hello","Star");
                                                                     v1 = snapshot.child("Dia").getValue(Long.class).toString();
                                                                     v2 = snapshot.child("Sys").getValue(Long.class).toString();
                                                                     SharedPreferences myPreferences
                                                                             = PreferenceManager.getDefaultSharedPreferences(bpshow2.this);
                                                                     SharedPreferences.Editor myEditor = myPreferences.edit();
                                                                     myEditor.putString("dataD",v1);
                                                                     myEditor.commit();
                                                                     SharedPreferences myPreferences1
                                                                             = PreferenceManager.getDefaultSharedPreferences(bpshow2.this);
                                                                     SharedPreferences.Editor myEditor1 = myPreferences1.edit();

                                                                     myEditor1.putString("dataS",v2);
                                                                     // myEditor.putString("dataP",value);

                                                                     myEditor1.commit();
                                                                   //  Log.w("child date",chd);

                                                                     int mm = d.getDate();
                                                                     int mon = d.getMonth()+1;
                                                                     int yy = d.getYear()-100;

                                                                     Log.w("Time", String.valueOf(t));
                                                                     Log.w("mint", String.valueOf(min));
                                                                     String chd;
                                                                     chd = String.valueOf(mm+"-"+mon+"-"+yy);
                                                                     Log.w("chd", String.valueOf(chd));
                                                                     if(curd.equals(chd))
                                                                     { myArrayList2.clear();
                                                                     myArrayList1.clear();

                                                                         myArrayList1.add(a);
                                                                         myArrayList2.add(a1);

                                                                         for (int i = 0; i < myArrayList1.size(); i++) {
                                                                             countP = countP +1;
                                                                             sumP = myArrayList1.get(i) + sumP;
                                                                         }
                                                                         for (int i = 0; i < myArrayList2.size(); i++) {
                                                                             countD = countD +1;
                                                                             sumD = myArrayList2.get(i) + sumD;
                                                                         }
                                                                         a = (sumP / countP);
                                                                         a1 = (sumD / countD);
                                                                         Log.wtf("a", String.valueOf(a));


                                                                         st.setD(String.valueOf(a));
                                                                         st.setS(String.valueOf(a1));
                                                                         refstore1.setValue(st.getD().toString());
                                                                         refstore2.setValue(st.getS().toString());
                                                                         refw.child(String.valueOf(day2)).child("d").setValue(a);
                                                                         refw.child(String.valueOf(day2)).child("s").setValue(a1);
                                                                        // refw.child(String.valueOf(day2)).setValue(a);
                                                                         Log.w("mweek", String.valueOf(mweek));
                                                                         Log.w("st", String.valueOf(st.getD()));

                                                                     }



                                                                     // System.out.println(valueP);



              /*  myArrayList1.add(pulse);
                Date c = Calendar.getInstance().getTime();
                int d = c.getDate();
                int sum =0;
                for(int i =0 ; i<myArrayList1.size();i++)
                { int x = Integer.parseInt(myArrayList1.get(i));
                    sum = sum+i;

                }
                sum = sum/(12*60);
                st.setD(String.valueOf(c));
                st.setData(String.valueOf(sum));


                ref2.child(String.valueOf(maxid)).setValue(st);*/
                                                                 }

                                                                 @Override
                                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                                     System.out.println("The read failed: " + error.getCode());

                                                                 }
                                                             });
                                                             String channel_name = "Health Alert";



                                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                 CharSequence name = channel_name;
                                                                 String description = "Your current health stats";
                                                                 int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                                                 NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                                                                 channel.setDescription(description);
                                                                 // Register the channel with the system; you can't change the importance
                                                                 // or other notification behaviors after this
                                                                 NotificationManager notificationManager = getSystemService(NotificationManager.class);
                                                                 notificationManager.createNotificationChannel(channel);
                                                             }
                                                             SharedPreferences myPreferences
                                                                     = PreferenceManager.getDefaultSharedPreferences(bpshow2.this);


                                                             SharedPreferences.Editor myEditor = myPreferences.edit();

                                                             String dataD =  myPreferences.getString("dataD", "Default");
                                                             Log.w("DataD",dataD);
                                                             int intdataD = Integer.parseInt(dataD);
                                                             SharedPreferences myPreferences1
                                                                     = PreferenceManager.getDefaultSharedPreferences(bpshow2.this);
                                                             SharedPreferences.Editor myEditor1 = myPreferences1.edit();
                                                             String dataS =  myPreferences1.getString("dataS", "Default");

                                                           int intdataS = Integer.parseInt(dataS);




                                                             String show = null;


                                                            if(intdataD>=70&&intdataD<=85 && intdataS>=110 &&intdataS<=125)
                                                            {
                                                                show = "Blood Pressure is in ideal range.All good!!";

                                                            }




                                                             else{

                                                                 show = "Blood Pressure is low.Take prescribed suppliments and eat good or consult with your doctor";




                                                            }





                                                             NotificationCompat.Builder builder = new NotificationCompat.Builder(bpshow2.this, CHANNEL_ID)
                                                                     .setSmallIcon(R.drawable.notification_icon)
                                                                     .setContentTitle("Test Title")
                                                                     // .setContentText("Much longer text that cannot fit one line...\n 2nd line")
                                                                     .setStyle(new NotificationCompat.BigTextStyle()
                                                                             .bigText(show))

                                                                     .setPriority(NotificationCompat.PRIORITY_DEFAULT);




                                                             NotificationManagerCompat notificationManager = NotificationManagerCompat.from(bpshow2.this);


                                                             //int notificationId = (int) System.currentTimeMillis();
                                                             int notificationId = 101;
// notificationId is a unique int for each notification that you must define
                                                             notificationManager.notify(notificationId, builder.build());



                                                             new android.os.Handler().postDelayed(
                                                                     new Runnable() {
                                                                         public void run() {
                                                                             // Log.i("tag","A Kiss after 5 seconds");

                                                                             startService(new Intent( bpshow2.this, NewService.class ) );
                                                                         }
                                                                     }, 5000);







                                                         }
                                                     }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        //ref.removeValue();


    }



}
