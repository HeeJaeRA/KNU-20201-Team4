package com.example.advanced_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    public WebLogic w = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context_main = this;

        Button imageButton = (Button) findViewById(R.id.btn_login);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailText = (TextView) findViewById(R.id.et_id);
                String email = emailText.getText().toString();

                TextView pwText = (TextView) findViewById(R.id.et_pass);
                String pw = pwText.getText().toString();

                if(email.length() == 0 || pw.length() == 0) {
                    Toast.makeText(getApplicationContext(),"ID 또는 PW를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                w = new WebLogic(email, pw);

                if(!w.attemptLogin()) {
                    Toast.makeText(getApplicationContext(),"로그인 실패!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });
    }
}


