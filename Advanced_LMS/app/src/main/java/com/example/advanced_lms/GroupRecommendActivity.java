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

public class GroupRecommendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouprecommend);

        ListView listview ;
        GroupListAdapter adapter;

        // Adapter 생성
        adapter = new GroupListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_group);
        listview.setAdapter(adapter);

        for(int i = 0 ; i < 50; i++) {
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.logo_knu),
                    "소모임 이름" + i, "소모임 설명" + i);
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