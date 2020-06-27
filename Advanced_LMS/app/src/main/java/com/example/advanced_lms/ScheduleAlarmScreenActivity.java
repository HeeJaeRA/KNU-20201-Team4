package com.example.advanced_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;

public class ScheduleAlarmScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schdule_alarm_screen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        TextView t = findViewById(R.id.time);
        String myTime = String.format("%02d:%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY) , Calendar.getInstance().get(Calendar.MINUTE));

        t.setText(myTime);
    }

    public void clear(View v) { // 기상 버튼
        //Intent missionIntent = new Intent(this, MissionActivity.class);
        //startActivity(missionIntent);

//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmScreenActivity.this, 0, recvIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.cancel(pendingIntent);


    }

    public void late(View v) { // 미루기 버튼
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);;
        Intent recvIntent = new Intent(this, ScheduleAlarmScreenActivity.class);;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ScheduleAlarmScreenActivity.this, 0, recvIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.cancel(pendingIntent);
        recvIntent.putExtra("STATE", "late");
        sendBroadcast(recvIntent);
        finish();
    }
}