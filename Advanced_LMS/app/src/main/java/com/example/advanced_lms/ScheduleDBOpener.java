package com.example.advanced_lms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScheduleDBOpener extends SQLiteOpenHelper {
    public ScheduleDBOpener(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ScheduleDBOpener(Activity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void insertTable(SQLiteDatabase db, String Table_Name, String Subject_Name, String Subject_Time) {
        if(Subject_Time == "") return;
        //db.execSQL("DELETE FROM " + Table_Name);
        String sql = "INSERT OR REPLACE INTO " + Table_Name + " values ('" + Subject_Name +"','" + Subject_Time + "')";
        try { db.execSQL(sql);  }
        catch (SQLException e)  { e.printStackTrace(); }
    }

    public void createTable(SQLiteDatabase db, String Table_Name) {
        String sql = "CREATE TABLE IF NOT EXISTS " + Table_Name + "(Subject_Name text, Subject_Time text)";
        try { db.execSQL(sql); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public String selectTable(SQLiteDatabase db, String Table_Name) {
        createTable(db, Table_Name);
        String sql = "SELECT * FROM " + Table_Name;


        try {
            String result = "";
            final Cursor query = db.rawQuery(sql, null);

            query.moveToFirst();
            do {
                result += query.getString(0) + "/" + (query.getString(1)) + "||";
            } while(query.moveToNext());

            return result;
        }
        catch(Exception e) {
            return "";
        }
    }
}
