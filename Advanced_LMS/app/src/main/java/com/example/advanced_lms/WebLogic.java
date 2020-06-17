package com.example.advanced_lms;

import android.util.Log;

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
        return CST.getSchedule();
    }

    public void getGroupList(int number) {
        //CGT.execute("list", Integer.toString(number * 15 + 1));
    }

    public void setRegisterGroup(int number) {
        CGT.execute("Register", Integer.toString(1683));
    }
}
