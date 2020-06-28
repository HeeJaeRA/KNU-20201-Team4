package com.example.advanced_lms;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.sql.Time;

import static java.lang.Thread.*;

public class GroupBoardContentActivity extends AppCompatActivity {
    CommentListAdapter adapter;
    int index;

    public static void setListViewHeightBasedOnChildren(@NonNull ListView listView) {
        CommentListAdapter listAdapter = (CommentListAdapter) listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardcontent);

        ListView listview ;

        // Adapter 생성
        adapter = new CommentListAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_comment);
        listview.setVerticalScrollBarEnabled(false);

        listview.setAdapter(adapter);

        try {
            Integer.parseInt(getIntent().getStringExtra("INDEX"));
            index = Integer.parseInt(getIntent().getStringExtra("INDEX"));
        }
        catch (NullPointerException e) {};

        TextView ContentName = (TextView)findViewById(R.id.Tv_contentName);
        ContentName.setText(((MainActivity) MainActivity.context_main).w.CGT.GBI[index].getTitle().split("-")[0]);

        TextView Author = (TextView)findViewById(R.id.Tv_writer);

        Author.setText(((MainActivity) MainActivity.context_main).w.CGT.GBI[index].getTitle().split("-")[1]);

        TextView Content = (TextView)findViewById(R.id.Tv_content);
        Content.setText("  " + ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].getBody() + "\n");


        TextView Timer = (TextView)findViewById(R.id.Tv_contentTime);
        String Time_String = ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].getTimer();
        if(Time_String.contains("수정")){
            ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].setTimer(Time_String.substring(0, Time_String.length() - 5));
        }
        Timer.setText(((MainActivity) MainActivity.context_main).w.CGT.GBI[index].getTimer());

        TextView CommentCount = (TextView)findViewById(R.id.Tv_commentCount);
        CommentCount.setText("  댓글 " + String.valueOf(((MainActivity) MainActivity.context_main).w.CGT.GBI[index].Count_Of_Comment) + "개");

        EditText Comment = (EditText)findViewById(R.id.Et_editComment);
        Comment.setPrivateImeOptions("defaultInputmode=korean;");


        for(int i = 0; i < ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].Count_Of_Comment; ++i) {
            adapter.addItem("  " + ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].Comments[i].getAuthor(), "  " + ((MainActivity) MainActivity.context_main).w.CGT.GBI[index].Comments[i].getBody());
        }

        setListViewHeightBasedOnChildren(listview);

        Button addComment = (Button)findViewById(R.id.btn_commentPush);

        addComment.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Comment = (EditText)findViewById(R.id.Et_editComment);
                if(Comment.getText().length() != 0) {
                    //Comment.getText()
                    adapter.addItem("  나 (1초 전)", "  " +Comment.getText().toString());
                    TextView CommentCount = (TextView)findViewById(R.id.Tv_commentCount);
                    CommentCount.setText("  댓글 " + String.valueOf(((MainActivity) MainActivity.context_main).w.CGT.GBI[index].Count_Of_Comment + 1) + "개");


                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Comment.getWindowToken(), 0);

                    ((MainActivity)MainActivity.context_main).w.writeComment(Integer.parseInt(getIntent().getStringExtra("CLUB")), Integer.parseInt(((MainActivity) MainActivity.context_main).w.CGT.GBI[Integer.parseInt(getIntent().getStringExtra("INDEX"))].getArticleNumber()), Comment.getText().toString());
                    Comment.setText("");
                    Toast.makeText(GroupBoardContentActivity.this, "작성 완료!", Toast.LENGTH_SHORT).show();

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ((MainActivity)MainActivity.context_main).w.getListGroupContent(Integer.parseInt(getIntent().getStringExtra("CLUB")));
                }
                else {
                    Toast.makeText(GroupBoardContentActivity.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
