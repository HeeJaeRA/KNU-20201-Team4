package com.example.advanced_lms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupadd);

        final RadioGroup rg = (RadioGroup)findViewById(R.id.register_check);

        Button rg_btn = (Button) findViewById(R.id.btn_reg);
        rg_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView Name = (TextView) findViewById(R.id.input_groupName);
                TextView groupDesciption = (TextView) findViewById(R.id.input_groupdescription);
                RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String Permission = "";
                if(rb.getText().toString().equals("자동 승인")) {
                    Permission = "0";
                }
                else {
                    Permission = "1";
                }

                ((MainActivity)MainActivity.context_main).w.createGroup(Name.getText().toString(), groupDesciption.getText().toString(), Permission);
                Toast.makeText(MainActivity.context_main, "작업 완료", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
