package com.example.advanced_lms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WebLogic {
    public UserLoginTask mAuthTask = null;
    public CrawlingScheduleTask CST = null;
    public CrawlingGroupTask CGT = null;

    public Map<String, String> UserCookie = null;
    private String email;
    private String password;

    WebLogic(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean attemptLogin() {
        if (mAuthTask != null) {
            return true;
        }

        boolean cancel = false;

        if (!cancel) {
            mAuthTask = new UserLoginTask(email, password);
            try {
                UserCookie = mAuthTask.execute((Void) null).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String arg = UserCookie.toString();

            if (arg.length() > 30) {
                //Log.e("msg", UserCookie.toString());
            } else {
                Log.e("msg", "로그인 실패!");
                return false;
            }

            CST = new CrawlingScheduleTask(UserCookie);
            CST.execute((Void) null);

            CGT = new CrawlingGroupTask(UserCookie);
        }
        return true;
    }

    public Subject[] getSchedule() {
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

        for(Subject sub : CST.getSchedule()) {
            dbOpener.insertTable(database, "Schedule", sub.getName(), sub.getDATE());
        }

        database.close();

        return CST.getSchedule();
    }

    public void createGroup(String Name, String Description, String Permission) {
        CGT.execute("create", Name, Description, Permission);
    }

    public void getGroupList(int PageNumber) {
        CGT.execute("list", Integer.toString(PageNumber * 15 + 1));
    }

    public void setRegisterGroup(int GroupNumber) {
        CGT.execute("Register", Integer.toString(GroupNumber));
    }

    public void writeGroup(int GroupNumber, String Title, String Content) {
        CGT.execute("writeGroup", Integer.toString(GroupNumber), Title, Content);
    }

    public void getListGroupContent(int GroupNumber) {
        CGT.execute("ListGroupContent", Integer.toString(GroupNumber));
    }

    public void writeComment(int GroupNumber, int ArticleNumber, String Content) {
        CGT.execute("AddComment", Integer.toString(GroupNumber), Integer.toString(ArticleNumber), Content);
    }

    public void RemoveComment(int GroupNumber, int ArticleNumber, int CommentNumber) {
        CGT.execute("RemoveComment", Integer.toString(GroupNumber), Integer.toString(ArticleNumber), Integer.toString(CommentNumber));
    }

    public void DropGroup(int GroupNumber) {
        CGT.execute("DropGroup", Integer.toString(GroupNumber));
    }
}
