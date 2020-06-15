package com.example.advanced_lms;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WebLogic {
    UserLoginTask mAuthTask = null;
    public Map<String, String> UserCookie;
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
                Log.e("log", e.toString());
                e.printStackTrace();
            }
            String arg = UserCookie.toString();

            if (arg.length() > 30) {
                Log.e("msg", UserCookie.toString());
            } else {
                Log.e("msg", "로그인 실패!");
                return false;
            }

            CrawlingScheduleTask CST = new CrawlingScheduleTask(UserCookie);
            CST.execute((Void) null);
        }
        return true;
    }
}
