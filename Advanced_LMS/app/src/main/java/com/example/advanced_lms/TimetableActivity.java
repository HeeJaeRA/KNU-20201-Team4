package com.example.advanced_lms;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimetableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        GridView gridView = findViewById(R.id.grid_timetable);

        GridListAdapter adapter = new GridListAdapter();


        for(int i = 0; i < 120 ; i++){
            if(i%6 == 0 && i/6%2 == 0){
                adapter.addItem(new ListItem(((i/6+1)/2+1) + "A",((i/6+1)/2+9)  + ":00" + "~" +  ((i/6+1)/2+9)  + ":30"));
            }
            else if(i%6 == 0 && i/6%2 == 1){
                adapter.addItem(new ListItem(((i/6+1)/2) + "B",((i/6+1)/2+8)  + ":30" + "~" +  ((i/6+1)/2+9)  + ":00"));
            }
            else
                adapter.addItem(new ListItem("과목이름\n" + "index :" + i,"0800-0900"));
        }
        gridView.setAdapter(adapter);

    }
}