package com.example.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class sleep extends AppCompatActivity {

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    TextView textshow;
    EditText hourinput;
    EditText mininput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        alarmTimePicker = (TimePicker) findViewById(R.id.timepicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        textshow = (TextView) findViewById(R.id.textshow);
        hourinput = (EditText) findViewById(R.id.hourinput);
        mininput = (EditText) findViewById(R.id.mininput);

        hourinput.setText("0");
        mininput.setText("0");


    }

    public void OnToggleClicked(View view)
    {
        long time;



        if ((( ToggleButton ) view).isChecked())
        {

            //Hardcoded time for 8 hours in milliseconds
            //  int hour = 8 * 3600000;
            // int min = 0 * 60000;

            //converting input number/string to Integers
            int  hour = Integer.valueOf(hourinput.getText().toString()) * 3600000;
            int min = Integer.valueOf(mininput.getText().toString()) * 60000;



            Toast.makeText(sleep.this, "ALARM ON",Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();

            //fetching current time in ms

            long currentTime = System.currentTimeMillis();

            // setting alarm time in ms
            long  timeInMs = currentTime + hour + min;

            //converting alarm time to Calender format
            calendar.setTimeInMillis(timeInMs);




            alarmTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            alarmTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

            //Accessing local storage to store Alarm start time , Input Hour , Input Minute data ....Needed to track sleep time

            SharedPreferences myPreferences
                    = PreferenceManager.getDefaultSharedPreferences(sleep.this);
            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putLong("Starttime", currentTime);
            myEditor.putInt("Hour", hour);
            myEditor.putInt("Minutes", min);

            myEditor.commit();


            //Alarm mechannism..done earlier ;untouched except alarm manager cancel part

            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class );
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time =(calendar.getTimeInMillis()-(calendar.getTimeInMillis() % 60000));
            if(System.currentTimeMillis() >time) {
                if(calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);

                else
                    time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);

        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(sleep.this, "ALARM OFF", Toast.LENGTH_SHORT).show();


            //Accessing Local storage to fetch Alarm start time , Input Hour , Input Minute data..match them by storage KEY

            SharedPreferences myPreferences
                    = PreferenceManager.getDefaultSharedPreferences(sleep.this);
            SharedPreferences.Editor myEditor = myPreferences.edit();
            long starttime = myPreferences.getLong("Starttime", 0);
            int Hourinput = myPreferences.getInt("Hour", 0);
            int minutesinput = myPreferences.getInt("Minutes", 0);


            //Fetching current time (ms) again when Alarm turned off

            long alarmbreakTime = System.currentTimeMillis();

            // substracting Alarm start time from current time or alarm break time

            long dif = alarmbreakTime - starttime;

            //converting ms to Minutes > then Minutes to Hour:Minute form

            long difmin = TimeUnit.MILLISECONDS.toMinutes(dif);
            long difhour = difmin/60;
            long minleft = difmin % 60;

            //Setting Hours:Minutes in Strings to use in Dialogues

            String str = Long.toString(difhour);
            String str2 = Long.toString(minleft);

            //Dialogues based on different circumstances..Not reviewed thoroughly..Check Again!
            //Nesting if/else arguments in layers might not work...as it already has may layers.

            textshow.setText("You slept for "+str+" Hours and "+str2+" Minutes");

            if (difhour < Hourinput &&  minleft < minutesinput){

                textshow.append("\nYou haven't slept enough.It's not wake up time yet");


            }else if(difhour == Hourinput && minleft == minutesinput){

                textshow.append("\nYou are doing just fine");

            }else if(difhour == Hourinput && minleft < minutesinput) {

                textshow.append("\nYou haven't slept enough.It's not wake up time yet");

            }else{

                textshow.append("\nLooks like you are having some extra sleep");

            }



            //Alram break activity for 8 hours hardcoded
            /* if (difhour < 8){

                textshow.append("\nYou haven't slept enough.It's not good for your Health");

            } */

        }
    }
}