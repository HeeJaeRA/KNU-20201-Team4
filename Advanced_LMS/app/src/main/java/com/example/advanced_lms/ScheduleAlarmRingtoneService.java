package com.example.advanced_lms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ScheduleAlarmRingtoneService extends Service {
    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;
    public static Context context;

    public ScheduleAlarmRingtoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String getState = intent.getExtras().getString("state");
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        assert getState != null;
        switch (getState) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                onDestroy();
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            mediaPlayer = MediaPlayer.create(this, alert);
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;
        }

        else if(this.isRunning && startId == 0) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            this.isRunning = false;
            this.startId = 0;
        }

        else if(!this.isRunning && startId == 0) {
            this.isRunning = false;
            this.startId = 0;
        }

        else if(this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 1;
        }

        else {
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
