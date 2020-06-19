package com.example.advanced_lms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupRecommendActivity extends AppCompatActivity implements GroupListAdapter.ListBtnClickListener {

    public boolean loadItemsFromDB(ArrayList<GroupListItem> list) {
        GroupListItem item;
        int i;

        item = new GroupListItem();
        item.setName("소모임 이름이지롱");
        item.setInfo("이건 정보지롱");
        list.add(item);

        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouprecommend);

        ListView listview = (ListView) findViewById(R.id.list_group);
        GroupListAdapter adapter;
        ArrayList<GroupListItem>items = new ArrayList<GroupListItem>();
        loadItemsFromDB(items) ;
        adapter = new GroupListAdapter(this, R.layout.group_item, items,this) ;
    }

    @Override
    public void onListBtnClick(int position) {

    }
}