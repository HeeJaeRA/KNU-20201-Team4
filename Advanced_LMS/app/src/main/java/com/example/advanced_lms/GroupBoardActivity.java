package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class GroupBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ((MainActivity)MainActivity.context_main).w.getGroupList(0); // 1페이지 불러오기.

        ListView listview ;
        BoardListAdapter adapter;

        // Adapter 생성
        adapter = new BoardListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_board);
        listview.setAdapter(adapter);

        try { // 페이지 불러올 동안 슬립.
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < 15; i++) {
            String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
            adapter.addItem(((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
        }

        Button btnprev = (Button) findViewById(R.id.btn_pre);
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button btnnext = (Button) findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GroupBoardContentActivity.class);
                startActivity(intent);
            }
        });
    }
}
