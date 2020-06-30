package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

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

        Button buttonAddGroup = (Button) findViewById(R.id.btn_Groupadd);
        buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupAddActivity.class);
                startActivity(intent);
            }
        });
        ImageView banner = (ImageView) findViewById(R.id.banner2);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(banner);
        Glide.with(this).load(R.drawable.banner).into(gifImage);


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