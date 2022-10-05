package com.example.signupandlogin;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NewService extends Service {

    // declaring object of MediaPlayer
   // private MediaPlayer player;

    public static final String CHANNEL_ID = "10001" ;
    private final static String default_channel_id = "default";

    @Override

    // execution of service will start
    // on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {

        // creating a media player which
        // will play the audio of Default
        // ringtone in android device
        //player = MediaPlayer.create( this, Settings.System.DEFAULT_RINGTONE_URI );
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
               // Log.i("tag", "A Kiss every 5 seconds");




                SharedPreferences myPreferences
                        = PreferenceManager.getDefaultSharedPreferences(NewService.this);

                SharedPreferences.Editor myEditor = myPreferences.edit();
                String dataP =  myPreferences.getString("dataP", "Default");
                String dataT =  myPreferences.getString("dataT", "Default");
                String dataD = myPreferences.getString("dataD", "Default");
                String dataS = myPreferences.getString("dataS", "Default");

                int intdataP = Integer.parseInt(dataP);
                int intdataT = Integer.parseInt(dataT);
               int  intdataD = Integer.parseInt(dataD);
               int  intdataS =   Integer.parseInt(dataS);
                String show = null;
                int checker;
                int checkerT;
                int checkerD;
                int checkflag = myPreferences.getInt("checkflag", 0);
                int checkflagT = myPreferences.getInt("checkflagT", 0);
              int checkflagD = myPreferences.getInt("checkflagD", 0);
//

                if(intdataP>=70 && intdataP <=95){

                    show = "Pulse Rate is normal.Well Done!\n";
                    checker = 1;


                }else{

                    show = "Pulse Rate is not ideal.Be Aware!\n";
                    checker = 2;
                }

                if(intdataT >=96 && intdataT <=100){

                    show = "Body Temperature is in ideal range.Feeling Great huh?";
                    checkerT = 1;

                }else{

                    show = "Body Temperature is not good.Take necessary measures";
                    checkerT = 2;
                }


                if(intdataD>=70&&intdataD<=85 && intdataS>=110 &&intdataS<=125)
                {
                    show = "Blood Pressure is in ideal range.All good!!";
                   checkerD = 1;
                }




                else{

                    show = "Blood Pressure is low.Take prescribed suppliments and eat good or consult with your doctor";
                    checkerD = 2;



                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewService.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Test Title")
                        // .setContentText("Much longer text that cannot fit one line...\n 2nd line")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(show))

                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);




                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NewService.this);

             // int notificationId = (int) System.currentTimeMillis();
               int notificationId = 101;
// notificationId is a unique int for each notification that you must define

                if(checkflag != checker || checkflagT != checkerT||checkflagD!= checkerD) {
                    notificationManager.notify(notificationId, builder.build());
                    myEditor.putInt("checkflag", checker);
                    myEditor.putInt("checkflagT", checkerT);
                   myEditor.putInt("checkflagD", checkerD);
                    myEditor.commit();


                }





            }
        },0,5000);

        // providing the boolean
        // value as true to play
        // the audio on loop
      //  player.setLooping( true );

        // starting the process
      //  player.start();

        // returns the status
        // of the program
        return START_STICKY;
    }

    @Override

    // execution of the service will
    // stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
       // player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

