package com.example.advanced_lms;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

class Subject {
    private String subName;
    private String subDATE;
    Subject(String subName, String subDATE) {
        setName(subName);
        setDATE(subDATE);
    }
    Subject() {}
    public String getName() { return subName; }
    public String getDATE() { return subDATE; }
    public void setName(String subName) { this.subName = subName; }
    public void setDATE(String subCode) { this.subDATE = subCode; }
}


public class CrawlingScheduleTask extends AsyncTask<Void, Void, Map<String, String>> {
    public Map<String, String> UserCookie;
    public Subject[] Subject_list = new Subject[8];
    public int SizeofSubject = 0;

    CrawlingScheduleTask(Map<String, String> UserCookie) {
        this.UserCookie = UserCookie;
    }

    @Override
    protected Map<String, String> doInBackground(Void... params) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        try {
            Subject_list = new Subject[8];
            SizeofSubject = 0;

            Connection.Response res = Jsoup.connect("http://lms.knu.kr/ilos/index.acl").header("HOST", "lms.knu.ac.kr").cookies(UserCookie).execute();
            Document doc = res.parse();

            Elements e = doc.select(".sub_open");

            for(Element emp : e) {
                Subject_list[SizeofSubject++] = new Subject(emp.text().substring(0, emp.text().indexOf('(')), emp.parent().select("span").text());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return UserCookie;
    }

    @Override
    protected void onPostExecute(final Map<String, String> success) {
        if (success.toString().length() > 30) {
            ScheduleDBOpener dbOpener = new ScheduleDBOpener(MainActivity.context_main, "Advanced_LMS.db", null, 1);
            SQLiteDatabase database = dbOpener.getWritableDatabase();

            database.delete("Schedule", null, null);

            String sql = "SELECT * FROM Schedule";

            try {
                final Cursor query = database.rawQuery(sql, null);
                query.moveToFirst();
                query.getString(0);
            }
            catch(Exception e) {
                dbOpener.createTable(database, "Schedule");
            }

            for(int i = 0; i < SizeofSubject; ++i) {
                dbOpener.insertTable(database, "Schedule", Subject_list[i].getName(), Subject_list[i].getDATE());
            }

            database.close();

        } else {
            Log.e("log", "Login Fail");
        }
    }

    @Override
    protected void onCancelled() {

    }

    public Subject[] getSchedule() {
        return Subject_list;
    }
}

