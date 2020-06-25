package com.example.advanced_lms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimetableActivity extends AppCompatActivity {
    public GridListAdapter adapter;
    public Subject subs[] = null;

    public static int[][] getIdx(String str) {
        int[][] retIdx = null;
        int loopcounter = 0;

        str = str.replace("B", "5");
        str = str.replace("A", "0");

        String[] tempStr = str.split(",");
        retIdx = new int[tempStr.length][];

        for (String temp : tempStr) {
            int dayOfweek = 0;
            switch (temp.charAt(0)) {
                case '월':
                    dayOfweek = 1;
                    break;
                case '화':
                    dayOfweek = 2;
                    break;
                case '수':
                    dayOfweek = 3;
                    break;
                case '목':
                    dayOfweek = 4;
                    break;
                case '금':
                    dayOfweek = 5;
                    break;
                default:
                    retIdx[0] = new int[0];
                    return retIdx;
            }
            temp = (String) (temp.substring(2, temp.length() - 1));

            int NumberofBlocks = (Integer.parseInt(temp.split("-")[1]) - Integer.parseInt(temp.split("-")[0])) / 5;

            retIdx[loopcounter] = new int[NumberofBlocks + 1];
            for (int i = 0; i <= NumberofBlocks; ++i) {
                retIdx[loopcounter][i] = 6 * ((Integer.parseInt(temp.split("-")[0]) - 10) / 5) + 6 * i + dayOfweek;
            }
            loopcounter++;
        }
        return retIdx;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        subs = ((MainActivity)MainActivity.context_main).w.CST.getSchedule();

        GridView gridView = findViewById(R.id.grid_timetable);

        adapter = new GridListAdapter();

        for(int i = 0; i < 180 ; i++){
            if(i % 6 == 0 && i / 6 % 2 == 0){
                adapter.addItem(new ListItem(((i/6+1)/2+1) + "A" + "\n" + ((i/6+1)/2+9)  + ":00" + "~" +  ((i/6+1)/2+9)  + ":30"));
            }
            else if(i % 6 == 0 && i / 6 % 2 == 1){
                adapter.addItem(new ListItem(((i/6+1)/2) + "B" + "\n" + ((i/6+1)/2+8)  + ":30" + "~" +  ((i/6+1)/2+9)  + ":00"));
            }
            else {
                adapter.addItem(new ListItem(""));
            }

        }

        // 과목 등록
        if(subs != null) {
            for(Subject sub : subs) {
                try {
                    for (int i[] : getIdx(sub.getDATE())) {
                        for (int j : i) {
                            setItem(j, sub);
                        }
                    }
                } catch(NullPointerException e) {}
            }
        }
        gridView.setAdapter(adapter);
    }

    public void setItem(int idx, Subject sub) {
        if(sub.getDATE() == "" || sub.getName() == "") return;
        ListItem l = adapter.items.get(idx);
        l.setsName(sub.getName());
        adapter.items.set(idx, l);
    }
}