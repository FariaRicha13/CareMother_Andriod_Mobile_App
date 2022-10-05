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


public class ShowTemp extends AppCompatActivity {
    ListView myListView;
    DatabaseReference refp,reft,ref1,ref2,ref,refstore,refu,refw,refws,refs;
    FirebaseDatabase database;
FirebaseUser user1;
    Date current;
    int sumT=0;
    int countT=0;
    String curDate;
String pname;
int cday,cmon,cyr;
String dist;
    //StorageReference firebaseStorage;
    int m1,d1,y1,min,a;
    Button update,sleep,step;
    TextView p1,t1;
    String value,v1;
    String curd;
    int flag =0;
    String pulse,temp,s,lmp,mweek;
    SharedPreferences.Editor myEditor;
    Date cur_d = Calendar.getInstance().getTime();
    StoreDB st,st1;
Date db,l,stepd;
int w,w1,w2,day1,day2,day;
    int maxid,maxid2;
    ArrayList<Integer>myArrayList1 = new ArrayList<>();
    ArrayList<String>myArrayList2 = new ArrayList<>();
    dataappend mother = new dataappend();
    FirebaseAuth fAuth;
    public static final String CHANNEL_ID = "10001" ;
    private final static String default_channel_id = "default";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_temp);

        //   ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(health_activity.this,android.R.layout.simple_list_item_1,myArrayList);

        t1 =(TextView) findViewById(R.id.tempshow);
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
        ref = FirebaseDatabase.getInstance().getReference("Store").child("Temp");

        refu = FirebaseDatabase.getInstance().getReference("User").child(pname);

        //ref.removeValue();
        ref1 =FirebaseDatabase.getInstance().getReference("Store").child("P");
        refs =FirebaseDatabase.getInstance().getReference("Store").child("Step");
        ref2 =FirebaseDatabase.getInstance().getReference("Store").child("T");
        refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        reft = FirebaseDatabase.getInstance().getReference("Sensor").child("T");
        y1 = cur_d.getYear()-100;
        m1 = cur_d.getMonth()+1;
        d1 = cur_d.getDate();
        min = cur_d.getMinutes();
        curd = String.valueOf(d1+"-"+m1+"-"+y1);
        refstore =  database.getInstance().getReference().child("Store").child("Temp").child(curd);
        Log.w("date cur",curd);
        Log.wtf("day", String.valueOf(d1));
        Log.wtf("mon", String.valueOf(m1));
        Log.wtf("year", String.valueOf(y1));
        Log.wtf("min", String.valueOf(min));

        refstore =  database.getInstance().getReference().child("Store").child("Temp").child(curd);
        // refp = FirebaseDatabase.getInstance().getReference("Sensor").child("P");
        // getReference().child("User");
        //firebaseStorage = FirebaseStorage.getInstance().getReference();

        Calendar calendarFrom= Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();

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
                refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("T").child(mweek);
                refws = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S").child(mweek);


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


        current = Calendar.getInstance().getTime();
        cday = current.getDate();
        cmon = current.getMonth()+1;
        cyr = current.getYear()-100;
        Log.w("vals", String.valueOf(cday));
        Log.w("valsmon", String.valueOf(cmon));
        Log.w("valsyear", String.valueOf(cyr));
        curDate = String.valueOf(cday+ "-"+cmon+"-"+cyr);
        Log.w("Stepd", String.valueOf(curDate));
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dist = String.valueOf(snapshot.child(String.valueOf(curDate)).getValue());
                Log.w("dist",dist);
                Double d = Double.valueOf(dist);
                 refws.child(String.valueOf(day2)).setValue(d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reft.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myArrayList1.clear();
                Date d = Calendar.getInstance().getTime();
                int t = d.getHours();
                int min = d.getMinutes();
                if(t==0&&min==0)
                {
                    temp ="0";

                }

                value = String.valueOf(snapshot.getValue());
                Log.wtf("val", value);
                a = Integer.valueOf(value);

                t1.setText("Temp: " + a);
                v1 = snapshot.getValue(Long.class).toString();


                int mm = d.getDate();
                int mon = d.getMonth()+1;
                int yy = d.getYear()-100;

                Log.w("Time", String.valueOf(t));
                Log.w("mint", String.valueOf(min));
                String chd;
                chd = String.valueOf(mm+"-"+mon+"-"+yy);



                // System.out.println(valueP);
                SharedPreferences myPreferences
                        = PreferenceManager.getDefaultSharedPreferences(ShowTemp.this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString("dataT",v1);
                // myEditor.putString("dataP",value);
                myEditor.commit();
                Log.w("child date",chd);
                if(curd.equals(chd))
                {  myArrayList1.add(a);

                    for (int i = 0; i < myArrayList1.size(); i++) {
                        countT = countT+1;
                        sumT = myArrayList1.get(i) + sumT;
                    }
                    a = (sumT / countT);
                    Log.wtf("a", String.valueOf(a));

                   st.setData(String.valueOf(a));
                    refstore.setValue(st.getData().toString());
                    //  stepsum.clear();
                    refw = FirebaseDatabase.getInstance().getReference("WeeklyData").child("T").child(mweek);
                    //refws = FirebaseDatabase.getInstance().getReference("WeeklyData").child("S").child(mweek);
                    refw.child(String.valueOf(day2)).setValue(a);
                    Log.w("mweek", String.valueOf(mweek));
                    Log.w("st", String.valueOf(st.getData()));
                }

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
                = PreferenceManager.getDefaultSharedPreferences(ShowTemp.this);

        SharedPreferences.Editor myEditor = myPreferences.edit();
        String dataT =  myPreferences.getString("dataT", "Default");
        int intdataT = Integer.parseInt(dataT);




        String show = null;



        if(intdataT>=96 && intdataT <=100){

            show = "Body Temperature is in ideal range.Feeling Great huh?";



        }else{

            show = "Body Temperature is not good.Take necessary measures";

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

                        startService(new Intent( ShowTemp.this, NewService.class ) );
                    }
                }, 5000);







    }



}
