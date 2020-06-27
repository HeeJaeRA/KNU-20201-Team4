package com.example.advanced_lms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    public WebLogic w = null;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent recvIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context_main = this;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        recvIntent =  new Intent(MainActivity.this, ScheduleAlarmReceiver.class);
        recvIntent.putExtra("STATE", "alarm on");

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, recvIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        Button imageButton = (Button) findViewById(R.id.btn_login);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailText = (TextView) findViewById(R.id.et_id);
                String email = emailText.getText().toString();

                TextView pwText = (TextView) findViewById(R.id.et_pass);
                String pw = pwText.getText().toString();

                if(email.length() == 0 || pw.length() == 0) {
                    Toast.makeText(getApplicationContext(),"ID 또는 PW를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                w = new WebLogic(email, pw);

                if(!w.attemptLogin()) {
                    Toast.makeText(getApplicationContext(),"로그인 실패!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });
    }
}


