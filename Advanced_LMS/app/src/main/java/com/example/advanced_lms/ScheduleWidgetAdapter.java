package com.example.advanced_lms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ScheduleWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    public Context context = null;
    public ArrayList<ScheduleWidgetItem> arrayList;

    public ScheduleWidgetAdapter(Context context) {
        this.context = context;
    }

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

    public void setData() {
        ScheduleDBOpener dbOpener = new ScheduleDBOpener(MainActivity.context_main, "Advanced_LMS.db", null, 1);
        SQLiteDatabase database = dbOpener.getWritableDatabase();

        String sql = "SELECT * FROM Schedule";

        try {
            final Cursor query = database.rawQuery(sql, null);
            query.moveToFirst();
            query.getString(0);
        }
        catch(Exception e) {
            dbOpener.createTable(database, "Schedule");
        }

        arrayList = new ArrayList<>();

        for(int i = 0; i < 180 ; i++){
            if(i % 6 == 0 && i / 6 % 2 == 0){
                arrayList.add(new ScheduleWidgetItem(((i/6+1)/2+1) + "A" + "\n" + ((i/6+1)/2+9)  + ":00" + "~" +  ((i/6+1)/2+9)  + ":30"));
            }
            else if(i % 6 == 0 && i / 6 % 2 == 1){
                arrayList.add(new ScheduleWidgetItem(((i/6+1)/2) + "B" + "\n" + ((i/6+1)/2+8)  + ":30" + "~" +  ((i/6+1)/2+9)  + ":00"));
            }
            else {
                arrayList.add(new ScheduleWidgetItem(""));
            }
        }

        String ret = dbOpener.selectTable(database, "Schedule");

        database.close();

        String rets[] = ret.split("\\|\\|");

        for(int cnt = 0; cnt < rets.length - 1; ++cnt) {
            try {
                for (int i[] : getIdx(rets[cnt].split("/")[1])) {
                    for (int j : i) {
                        setItem(j, rets[cnt].split("/")[0], rets[cnt].split("/")[1]);
                    }
                }
            } catch(NullPointerException e) {}
            catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public void setItem(int idx, String Title, String Time) {
        if(Title == "" || Time == "") return;
        ScheduleWidgetItem l = arrayList.get(idx);
        l.setContent(Title);
        arrayList.set(idx, l);
    }


    @Override
    public void onCreate() {
        setData();
    }

    @Override
    public void onDataSetChanged() {
        setData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews listviewWidget = new RemoteViews(context.getPackageName(), R.layout.schedule_widgetitem);
        listviewWidget.setTextViewText(R.id.text1, arrayList.get(position).content);

        Intent dataIntent = new Intent();
        dataIntent.putExtra("item_data", arrayList.get(position).content);
        listviewWidget.setOnClickFillInIntent(R.id.text1, dataIntent);

        return listviewWidget;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
