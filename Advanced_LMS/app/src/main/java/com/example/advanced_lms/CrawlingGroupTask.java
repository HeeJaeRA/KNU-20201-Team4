package com.example.advanced_lms;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

public class CrawlingGroupTask extends AsyncTask<String, Void, Map<String, String>>  {
    public Map<String, String> UserCookie;

    public CrawlingGroupTask(Map<String, String> UserCookie) { this.UserCookie = UserCookie; }

    @Override
    protected Map<String, String> doInBackground(String... voids) {
        Log.e("Tt", String.valueOf(voids.length));
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        switch(voids[0]) {
            case "list" : // 목록 뽑기
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.kr/ilos/community/share_group_list.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("start", voids[1],
                                    "display", "15",
                                    "encoding", "utf-8",
                                    "SCH_CLUB_VAL", "")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Elements e = doc.select(".grp_list");

                    for(int i = 0; i < e.size(); ++i) {
                        Log.e("TT", e.get(i).getElementsByAttribute("onclick").get(0).attr("onclick"));
                        String Title = e.get(i).select(".grp_title").text();
                        Element element = e.get(i).select(".grp_txt").first();
                        String Description = element.text() + "_" + element.nextElementSibling().text();
                        Log.e("Title", Title);
                        Log.e("Description", Description);
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
                break;
            case "Register" : // 가입
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.kr/ilos/community/share_group_register.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("CLUB_GRP_ID", voids[1],
                                    "encoding", "utf-8")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Log.e("result", doc.text());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Map<String, String> success) {  }

    @Override
    protected void onCancelled() {

    }
    
}
