package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupselect);


        Button imageButton = (Button) findViewById(R.id.btn_Grouprecommend);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupRecommendActivity.class);
                startActivity(intent);
            }
        });

//        Button buttonGroup = (Button) findViewById(R.id.btn_Group);
//        buttonGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), GroupSelectActivity.class);
//                startActivity(intent);
//            }
//        });


//        Button imageButton = (Button) findViewById(R.id.btn_Grouprecommend);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), TimetableActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button buttonGroup = (Button) findViewById(R.id.btn_Group);
//
    }
}