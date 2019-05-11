package io.github.ankushsinghal.morningalarm;

import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker timePicker = findViewById(R.id.simpleTimePicker);
        if(Build.VERSION.SDK_INT >= 23){
            timePicker.setHour(8);
            timePicker.setMinute(0);
        }
        else{
            timePicker.setCurrentHour(8);
            timePicker.setCurrentMinute(0);
        }
    }

    public void onClick(View view){

        TimePicker timePicker = findViewById(R.id.simpleTimePicker);
        int hour, minute, intentHour, intentMinute;

        if(Build.VERSION.SDK_INT >= 23){
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }
        else{
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        intentHour = hour;
        intentMinute = minute;

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);

        /*
         *  We want to make 4 alarms at 10 minute intervals before the
         *  specified time in the time picker, so that I get up on time
         */

        for(int i=0; i < 4; i++){

            intent.putExtra(AlarmClock.EXTRA_HOUR, intentHour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES, intentMinute);
            startActivity(intent);

            intentMinute -= 10;
            if(intentMinute < 0){
                intentMinute += 60;
                intentHour--;
                intentHour = (intentHour >= 0) ? intentHour : intentHour + 24;
            }
        }
    }
}
