package com.example.advanced_lms;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    int CLUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        CLUB = Integer.parseInt(getIntent().getStringExtra("CLUB"));
    }

    public void sendPost(View view) {
        EditText Title = (EditText)findViewById(R.id.Et_title);
        EditText Content = (EditText)findViewById(R.id.Et_post);
        ((MainActivity)MainActivity.context_main).w.writeGroup(CLUB, Title.getText().toString(), Content.getText().toString());
        Toast.makeText(PostActivity.this, "게시글 업로드 완료", Toast.LENGTH_SHORT);
        finish();
    }
}
