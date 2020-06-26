package com.example.advanced_lms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.ScaleAnimation;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Calendar;
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

//            database.delete("Schedule", null, null);

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
                if(((String)(Subject_list[i].getDATE().substring(0, 1))).charAt(0) != '2') {
                    for(Calendar calander : getTime(Subject_list[i].getDATE()) ) {
                        try {
                            ((MainActivity) MainActivity.context_main).alarmManager.set(AlarmManager.RTC_WAKEUP, calander.getTimeInMillis(), ((MainActivity) MainActivity.context_main).pendingIntent);
                        }catch  (NullPointerException e ) {}
                    }
                }
             }

            /* Video Version
            Calendar calander = Calendar.getInstance();
            calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            calander.set(Calendar.HOUR_OF_DAY, 18);
            calander.set(Calendar.MINUTE, 8);
            calander.set(Calendar.SECOND, 35);
            ((MainActivity)MainActivity.context_main).alarmManager.set(AlarmManager.RTC_WAKEUP, calander.getTimeInMillis(), ((MainActivity)MainActivity.context_main).pendingIntent);
            */


            database.close();

        } else {
            Log.e("log", "Login Fail");
        }
    }

    public static Calendar[] getTime(String time) {
        String[] tempStr = time.split(",");

        Calendar calendar[] = new Calendar[tempStr.length];
        int idx = 0;

        for (String temp : tempStr) {
            calendar[idx] = Calendar.getInstance();
            switch(temp.charAt(0)) {
                case '월': calendar[idx].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); break;
                case '화': calendar[idx].set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY); break;
                case '수': calendar[idx].set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY); break;
                case '목': calendar[idx].set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY); break;
                case '금': calendar[idx].set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); break;
                default : return calendar;
            }
            temp = (String) (temp.substring(2, temp.length() - 1));

            temp = temp.split("-")[0];

            calendar[idx].set(Calendar.HOUR_OF_DAY, 8 + Integer.parseInt(temp.substring(0, temp.length() - 1)));
            String c = (String) (temp.substring(temp.length() - 1));
            if(c.charAt(0) == 'A') {
                calendar[idx].set(Calendar.MINUTE, 0);
            }
            else {
                calendar[idx].set(Calendar.MINUTE, 30);
            }
        }

        return calendar;
    }


    @Override
    protected void onCancelled() {

    }

    public Subject[] getSchedule() {
        return Subject_list;
    }
}

