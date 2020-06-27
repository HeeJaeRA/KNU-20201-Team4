package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupIntroActivity extends AppCompatActivity {
    String CLUB_NUMBER = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupintro);

        TextView title = (TextView)findViewById(R.id.Tv_introName);
        title.setText(getIntent().getStringExtra("Title"));

        String Descs[] = getIntent().getStringExtra("Description").split("_");

        TextView Admin = (TextView)findViewById(R.id.Tv_introGM);
        Admin.setText(Descs[1].split("\\|")[0]);

        TextView Desc = (TextView)findViewById(R.id.Tv_introText);
        Desc.setText(Descs[0]);

        CLUB_NUMBER = getIntent().getStringExtra("CLUB");

        Button buttonGroup = (Button) findViewById(R.id.btn_join);
        buttonGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.context_main).w.setRegisterGroup(Integer.parseInt(CLUB_NUMBER));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), GroupBoardActivity.class);
                startActivity(intent);
                Log.e("tt", "여기");
                finish();
            }
        });
    }

}
