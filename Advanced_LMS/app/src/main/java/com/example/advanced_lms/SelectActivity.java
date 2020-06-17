package com.example.advanced_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        Button imageButton = (Button) findViewById(R.id.btn_Timetable);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent);
            }
        });

        Button buttonGroup = (Button) findViewById(R.id.btn_Group);

        buttonGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity)MainActivity.context_main).w.getGroupList(1); // 소모임 확인 테스트
                //((MainActivity)MainActivity.context_main).w.setRegisterGroup(1683); // 소모임 가입 테스트
                //((MainActivity)MainActivity.context_main).w.writeGroup(1683, "Test", "from advanced LMS"); // 소모임 게시글 작성 테스트
                //((MainActivity)MainActivity.context_main).w.getListGroupContent(1683); // 소모임 게시글 확인 테스트
                //((MainActivity)MainActivity.context_main).w.writeComment(1683, 902726, "안녕하세요"); // 댓글 작성 테스트
                //((MainActivity)MainActivity.context_main).w.RemoveComment(1683, 1002209, 135777); // 댓글 삭제 테스트
                //((MainActivity)MainActivity.context_main).w.DropGroup(1683); // 탈퇴 테스트
            }
        });
    }
}