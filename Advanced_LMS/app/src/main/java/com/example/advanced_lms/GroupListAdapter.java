package com.example.advanced_lms;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class GroupListAdapter extends ArrayAdapter implements View.OnClickListener  {
    public GroupListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    GroupListAdapter(Context context, int resource, ArrayList<GroupListItem> list, GroupRecommendActivity clickListener) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;
        this.listBtnClickListener = clickListener ;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        final TextView textGroupName = (TextView) convertView.findViewById(R.id.Tv_GroupName);
        final TextView textGroupInfo = (TextView) convertView.findViewById(R.id.Tv_GroupInfo);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final GroupListItem GroupListItem = (GroupListItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영

        textGroupName.setText(GroupListItem.getName());
        textGroupInfo.setText(GroupListItem.getInfo());

        // button1 클릭 시 TextView(textView1)의 내용 변경.
        Button button1 = (Button) convertView.findViewById(R.id.btn_join);
        button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                textGroupName.setText("클릭 테스트");
            }
        });

        return convertView;
    }


    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }

}