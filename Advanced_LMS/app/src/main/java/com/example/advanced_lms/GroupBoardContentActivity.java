package com.example.advanced_lms;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupBoardContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardcontent);

        ListView listview ;
        CommentListAdapter adapter;

        // Adapter 생성
        adapter = new CommentListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_comment);
        listview.setAdapter(adapter);
    }

}
