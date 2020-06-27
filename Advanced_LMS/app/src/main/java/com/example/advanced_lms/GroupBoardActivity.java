package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static java.lang.Thread.sleep;

public class GroupBoardActivity extends AppCompatActivity {
    int page = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ((MainActivity)MainActivity.context_main).w.getListGroupContent(Integer.parseInt(getIntent().getStringExtra("CLUB")));

        ListView listview ;
        BoardListAdapter adapter;

        // Adapter 생성
        adapter = new BoardListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_board);
        listview.setAdapter(adapter);

        try { // 페이지 불러올 동안 슬립.
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < 100; i++) {
            try {
                adapter.addItem(((MainActivity) MainActivity.context_main).w.CGT.GBI[i].getTitle(), ((MainActivity) MainActivity.context_main).w.CGT.GBI[i].getBody());
            }
            catch (NullPointerException e) {}
            catch (IndexOutOfBoundsException e) { break; }
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GroupBoardContentActivity.class);
                intent.putExtra("CLUB", getIntent().getStringExtra("CLUB"));
                intent.putExtra("INDEX", String.valueOf(position));
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setContentView(R.layout.activity_board);
        ((MainActivity)MainActivity.context_main).w.getListGroupContent(Integer.parseInt(getIntent().getStringExtra("CLUB")));

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


        for(int i = 0; i < 100; i++) {
            try {
                adapter.addItem(((MainActivity) MainActivity.context_main).w.CGT.GBI[i].getTitle(), ((MainActivity) MainActivity.context_main).w.CGT.GBI[i].getBody());
            }
            catch (NullPointerException e) {}
            catch (IndexOutOfBoundsException e) { break; }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GroupBoardContentActivity.class);
                intent.putExtra("CLUB", getIntent().getStringExtra("CLUB"));
                intent.putExtra("INDEX", String.valueOf(position));
                startActivityForResult(intent, 1);
            }
        });
    }
}
