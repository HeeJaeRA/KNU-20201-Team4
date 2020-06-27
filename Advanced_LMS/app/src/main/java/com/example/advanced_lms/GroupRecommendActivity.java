package com.example.advanced_lms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class GroupRecommendActivity extends AppCompatActivity {
    int page = 0;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouprecommend);

        context = this;

        ((MainActivity)MainActivity.context_main).w.getGroupList(page); // 1페이지 불러오기.

        ListView listview ;
        final GroupListAdapter adapter;

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


        for(int i = 0; i < 15; i++) {
            String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.logo_knu), ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
        }

        Button btnprev = (Button) findViewById(R.id.btn_pre);
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page -= 1;
                if(page < 0) {
                    page = 0;
                    return;
                }

                ((MainActivity)MainActivity.context_main).w.getGroupList(page); // 1페이지 불러오기.

                ListView listview ;
                final GroupListAdapter adapter;

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


                for(int i = 0; i < 15; i++) {
                    String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
                    adapter.addItem(ContextCompat.getDrawable(GroupRecommendActivity.this, R.drawable.logo_knu), ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
                }
            }
        });
        Button btnnext = (Button) findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page += 1;

                ((MainActivity)MainActivity.context_main).w.getGroupList(page); // 1페이지 불러오기.

                ListView listview ;
                final GroupListAdapter adapter;

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


                for(int i = 0; i < 15; i++) {
                    String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
                    adapter.addItem(ContextCompat.getDrawable(GroupRecommendActivity.this, R.drawable.logo_knu), ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(((MainActivity)MainActivity.context_main).w.CGT.GI == null) {
                    ((MainActivity) MainActivity.context_main).w.getGroupList(page);
                    try { // 페이지 불러올 동안 슬립.
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String str = ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getCLUB_GRP_ID();
                Pattern pattern = Pattern.compile("\'.+\'");
                Matcher Finder = pattern.matcher(str);

                if(Finder.find()) {
                    String ret = Finder.group().replace(" ", "");
                    String tt[] = ret.split(",");

                    String Option = tt[1].substring(1, tt[1].length() - 1);

                    if(Option.length() != 0) {
                        Intent intent = new Intent(getApplicationContext(),GroupBoardActivity.class);
                        intent.putExtra("CLUB", tt[0].substring(1, tt[0].length() - 1));
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(),GroupIntroActivity.class);
                        intent.putExtra("Title", ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getTitle());
                        intent.putExtra("Description", ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getDescription());
                        intent.putExtra("CLUB", tt[0].substring(1, tt[0].length() - 1));
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ((MainActivity)MainActivity.context_main).w.getGroupList(page);

        ListView listview ;
        final GroupListAdapter adapter;

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


        for(int i = 0; i < 15; i++) {
            String Description[] = ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getDescription().split("_");
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.logo_knu), ((MainActivity)MainActivity.context_main).w.CGT.GI[i].getTitle(), Description[1] + "\n" + Description[0]);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getCLUB_GRP_ID();
                Pattern pattern = Pattern.compile("\'.+\'");
                Matcher Finder = pattern.matcher(str);

                if(Finder.find()) {
                    String ret = Finder.group().replace(" ", "");
                    String tt[] = ret.split(",");

                    String Option = tt[1].substring(1, tt[1].length() - 1);

                    if(Option.length() != 0) {
                        Intent intent = new Intent(getApplicationContext(),GroupBoardActivity.class);
                        intent.putExtra("CLUB", tt[0].substring(1, tt[0].length() - 1));
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(),GroupIntroActivity.class);
                        intent.putExtra("Title", ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getTitle());
                        intent.putExtra("Description", ((MainActivity)MainActivity.context_main).w.CGT.GI[position].getDescription());
                        intent.putExtra("CLUB", tt[0].substring(1, tt[0].length() - 1));
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });
    }
}