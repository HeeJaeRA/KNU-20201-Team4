package com.example.advanced_lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();

    Context context;

    public void addItem(ListItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,parent,false);
        }

        TextView sNameText = convertView.findViewById(R.id.Tv_sName);
        TextView sTimeText = convertView.findViewById(R.id.Tv_sTime);

        sNameText.setText(listItem.getsName());
        sTimeText.setText(listItem.getsTime());

        return convertView;
    }
}
