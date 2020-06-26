package com.example.advanced_lms;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ScheduleAlarmReceiver extends BroadcastReceiver {
    Context context;
    private static PowerManager.WakeLock sCpuWakeLock;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        this.context = context;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        wakeLock.acquire();

        PendingIntent pendingIntent;

        wakeLock.release();

        String recvString = intent.getExtras().getString("STATE");
        Intent servcIntent = new Intent(context, ScheduleAlarmRingtoneService.class);
        servcIntent.putExtra("state", recvString);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            this.context.startService(servcIntent);
        }else{
            this.context.startService(servcIntent);
        }

        if(recvString.equals("alarm off") || recvString.equals("late")) return;

        try {
            intent = new Intent(context, ScheduleAlarmScreenActivity.class);

            pendingIntent = PendingIntent.getActivity(context, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            pendingIntent.send();

        } catch (PendingIntent.CanceledException e) {

            e.printStackTrace();

        }

        notification();
    }

    void notification() {
        Intent intent = new Intent(context, ScheduleAlarmScreenActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 111, intent, PendingIntent.FLAG_ONE_SHOT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "channel_id")
                    .setSmallIcon(R.drawable.clock)
                    .setContentTitle("알람") // 푸시의 타이틀이다.
                    .setContentText("수업 30분 전!")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
