package io.github.ankushsinghal.morningalarm;

import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    public static final int ALARM_INTERVAL = 10;
    public static final int MIN_NUM_ALARMS = 1;
    public static final int MAX_NUM_ALARMS = 10;
    public static final int DEFAULT_NUM_ALARMS = 4;
    public static final int DEFAULT_HOUR = 8;
    public static final int DEFAULT_MINUTE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker timePicker = findViewById(R.id.simpleTimePicker);
        if(Build.VERSION.SDK_INT >= 23){
            timePicker.setHour(DEFAULT_HOUR);
            timePicker.setMinute(DEFAULT_MINUTE);
        }
        else{
            timePicker.setCurrentHour(DEFAULT_HOUR);
            timePicker.setCurrentMinute(DEFAULT_MINUTE);
        }

        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(MIN_NUM_ALARMS);
        numberPicker.setMaxValue(MAX_NUM_ALARMS);
        numberPicker.setValue(DEFAULT_NUM_ALARMS);       // Default number of alarms
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
         *  We want to make a number of alarms at 10 minute intervals before the
         *  specified time in the time picker, so that I get up on time
         */

        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        int numAlarms = numberPicker.getValue();

        for(int i=0; i < numAlarms; i++){

            intent.putExtra(AlarmClock.EXTRA_HOUR, intentHour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES, intentMinute);
            startActivity(intent);

            intentMinute -= ALARM_INTERVAL;
            if(intentMinute < 0){
                intentMinute += 60;
                intentHour--;
                intentHour = (intentHour >= 0) ? intentHour : intentHour + 24;
            }
        }
    }
}
