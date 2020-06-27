package com.example.advanced_lms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static java.lang.Thread.*;

public class GroupRecommendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouprecommend);

        ((MainActivity)MainActivity.context_main).w.getGroupList(0); // 1페이지 불러오기.

        ListView listview ;
        GroupListAdapter adapter;

        // Adapter 생성
        adapter = new GroupListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_group);
        listview.setAdapter(adapter);

        try { // 페이지 불러올 동안 슬립.
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0 ; i < 15; i++) {
            String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.logo_knu), ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GroupWebViewActivity.class);
                startActivity(intent);
            }
        });
    }
}