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
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class ShowPulse extends AppCompatActivity {
    public static final String CHANNEL_ID = "10001" ;
    private final static String default_channel_id = "default";
    ListView myListView;
    DatabaseReference refp,reft,ref1,ref2,ref,refstore,refw,refu;
    FirebaseDatabase database;
    int sumP = 0;
    int countP =0;
    //StorageReference firebaseStorage;
    int m1,d1,y1,min,a;
    Button update,sleep,step;
    TextView p1,t1;
    String value,valueP,v;
    String curd;
    String pulse,temp,s;
    FirebaseUser user1;
    Date cur_d = Calendar.getInstance().getTime();
    StoreDB st,st1;
    int maxid,maxid2;
    String pname;
    ArrayList<Integer>myArrayList1 = new ArrayList<>();
    ArrayList<String>myArrayList2 = new ArrayList<>();
    dataappend mother = new dataappend();
    FirebaseAuth fAuth;
public String lmp,mweek;
  Date d,db;
            Date l;
    int w1,w2,w,day1,day2,day;
    Calendar calendarFrom = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pulse);
        //   ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(health_activity.this,android.R.layout.simple_list_item_1,myArrayList);

        p1 =(TextView) findViewById(R.id.pulseshow);
       // t1 =(TextView) findViewById(R.id.t);
        st = new StoreDB();
        st1 = new StoreDB();       //final String[] value = new String[ 1 ];
        // String value;
        // myListView.setAdapter(myArrayAdapter);
        String username = getIntent().getStringExtra("userkey");
        user1 = FirebaseAuth.getInstance().getCurrentUser();
        pname = user1.getUid();
        //database=FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("User").child("1");
        Date d = Calendar.getInstance().getTime();
        int c = d.getDate();
        String k = String.valueOf(c);
        ref = FirebaseDatabase.getInstance().getReference("Store").child("Pulse");
        refu = FirebaseDatabase.getInstance().getReference("User").child(pname);

        Calendar calendarFrom= Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        //ref.removeValue();
        ref1 =FirebaseDatabase.getInstance().getReference("Store").child("P");
        ref2 =FirebaseDatabase.getInstance().getReference("Store").child("T");
        refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        reft = FirebaseDatabase.getInstance().getReference("Sensor").child("T");
        y1 = cur_d.getYear()-100;
        m1 = cur_d.getMonth()+1;
        d1 = cur_d.getDate();
        String valueP;

        min = cur_d.getMinutes();
        curd = String.valueOf(d1+"-"+m1+"-"+y1);
        Log.w("date cur",curd);
        Log.wtf("day", String.valueOf(d1));
        Log.wtf("mon", String.valueOf(m1));
        Log.wtf("year", String.valueOf(y1));
        Log.wtf("min", String.valueOf(min));

        refstore =  database.getInstance().getReference().child("Store").child("Pulse").child(curd);
        // refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();



        myArrayList1.clear();
        myArrayList2.clear();
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
     //Log.w("mweek",lmp);
     //
     ref.addChildEventListener(new ChildEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             String valued = String.valueOf(snapshot.getValue());
             Log.wtf("val", valued);
             int x  = Integer.valueOf(valued);
             Log.wtf("x", String.valueOf(x));
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
             refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("P").child(mweek);

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

        refp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myArrayList1.clear();
                Date d = Calendar.getInstance().getTime();
                int t = d.getHours();
                int min = d.getMinutes();
              if(t==0&&min==0)
                {
                    pulse ="0";

                }

                String value = String.valueOf(snapshot.getValue());
                Log.wtf("val", value);
                a = Integer.valueOf(value);

                p1.setText("Pulse: " + a);
                v = snapshot.getValue(Long.class).toString();


                int mm = d.getDate();
                int mon = d.getMonth()+1;
                int yy = d.getYear()-100;

                Log.w("Time", String.valueOf(t));
                Log.w("mint", String.valueOf(min));
                String chd;
                chd = String.valueOf(mm+"-"+mon+"-"+yy);
                SharedPreferences myPreferences
                        = PreferenceManager.getDefaultSharedPreferences(ShowPulse.this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString("dataP",v);
              // myEditor.putString("dataP",value);
                myEditor.commit();

                Log.w("child date",chd);
                if(curd.equals(chd))
                {  myArrayList1.add(a);

                    for (int i = 0; i < myArrayList1.size(); i++) {
                        countP = countP +1;
                        sumP = myArrayList1.get(i) + sumP;
                    }
                    a = (sumP / countP);
                    Log.wtf("a", String.valueOf(a));


                    st.setData(String.valueOf(a));
                    refstore.setValue(st.getData().toString());
                    refw.child(String.valueOf(day2)).setValue(a);
                    Log.w("mweek", String.valueOf(mweek));
                    Log.w("st", String.valueOf(st.getData()));

                }


                //refw.child(String.valueOf(day2)).setValue(st.getData().toString());
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
                = PreferenceManager.getDefaultSharedPreferences(ShowPulse.this);

        SharedPreferences.Editor myEditor = myPreferences.edit();
        String dataP =  myPreferences.getString("dataP", "Default");
        int intdataP = Integer.parseInt(dataP);





        String show = null;



        if(intdataP>=70 && intdataP <=95){

            show = "Pulse Rate is normal.Well Done!\n";



        }else{

            show = "Pulse Rate is not ideal.Be Aware!\n";

        }




        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Test Title")
                // .setContentText("Much longer text that cannot fit one line...\n 2nd line")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(show))

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);




        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        //int notificationId = (int) System.currentTimeMillis();
        int notificationId = 101;
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Log.i("tag","A Kiss after 5 seconds");

                        startService(new Intent( ShowPulse.this, NewService.class ) );
                    }
                }, 5000);





    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


}
