package com.example.advanced_lms;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

class Group_Item {
    private String title;
    private String Description;
    private String CLUB_GRP_ID;

    Group_Item(String title, String Description, String CLUB_GRP_ID) {
        setTitle(title);
        setDescription(Description);
        setCLUB_GRP_ID(CLUB_GRP_ID);
    }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String Description) { this.Description = Description; }
    public void setCLUB_GRP_ID(String CLUB_GRP_ID) { this.CLUB_GRP_ID = CLUB_GRP_ID; }
    public String getTitle() { return title; }
    public String getDescription() { return Description; }
    public String getCLUB_GRP_ID() { return CLUB_GRP_ID; }
}

class Group_Board_Comment_Item {
    public String author;
    public String body;

    Group_Board_Comment_Item(String author, String body) {
        setAuthor(author);
        setBody(body);
    }

    public void setAuthor(String author) { this.author = author; }
    public void setBody(String body) { this.body = body; }

    public String getAuthor() { return author; }
    public String getBody() { return body; }
}

class Group_Board_Item {
    private String ArticleNumber;
    private String title;
    private String body;
    private String Timer;
    public Group_Board_Comment_Item[] Comments;
    public int Count_Of_Comment = 0;

    Group_Board_Item(String ArticleNumber, String title, String body, String Timer) {
        setArticleNumber(ArticleNumber);
        setTitle(title);
        setBody(body);
        setTimer(Timer);
    }

    public void setArticleNumber(String author) { this.ArticleNumber = author; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
    public void setTimer(String Timer) { this.Timer = Timer; }

    public String getArticleNumber() { return ArticleNumber; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getTimer() { return Timer; }
}

public class CrawlingGroupTask extends AsyncTask<String, Void, Map<String, String>>  {
    public Group_Item GI[];
    public Group_Board_Item GBI[];
    public Map<String, String> UserCookie;
    public CrawlingGroupTask(Map<String, String> UserCookie) { this.UserCookie = UserCookie; }
    public boolean isUse = false;

    @Override
    protected Map<String, String> doInBackground(String... voids) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        isUse = true;
        switch(voids[0]) {
            case "create":
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_group_insert.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("GRP_NM", voids[1],
                                    "TXT", voids[2],
                                    "JOIN_DIV", voids[3],
                                    "encoding", "utf-8")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }

                break;
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

                    GI = new Group_Item[15];

                    Document doc = res.parse();

                    Elements e = doc.select(".grp_list");

                    for(int i = 0; i < e.size(); ++i) {
                        String Title = e.get(i).select(".grp_title").text();
                        Element element = e.get(i).select(".grp_txt").first();
                        String Description = element.text() + "_" + element.nextElementSibling().text();
                        GI[i] = new Group_Item(Title, Description, e.get(i).getElementsByAttribute("onclick").get(0).attr("onclick"));
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

            case "writeGroup" :
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_insert.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("CLUB_GRP_ID", voids[1],
                                    "SBJT", voids[2],
                                    "TXT", voids[3],
                                    "FILE_SEQS", "",
                                    "D_FILE_SEQS", "",
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
            case "ListGroupContent" : // 게시글 확인
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_list.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("CLUB_GRP_ID", voids[1],
                                    "member_div", "1",
                                    "SCH_VALUE", "",
                                    "startL", "1",
                                    "encoding", "utf-8")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Elements e = doc.select(".view_art");
                    GBI = new Group_Board_Item[e.size()];

                    for(int i = 0; i < e.size(); ++i) {
                        String Title = e.get(i).select(".list_title").text();
                        String Description = e.get(i).select(".list_cont").text();
                        Elements Comments = e.get(i).parent().select(".comment-list");
                        String ArticleNumber = e.get(i).parent().select(".comment_wrap").attr("num");

                        Log.e("Title", Title + " _ " + ArticleNumber);
                        Log.e("Description", Description);

                        GBI[i] = new Group_Board_Item(ArticleNumber, Title, Description, e.get(i).select(".list_title").parents().select(".viewBtn").get(i).text());
                        GBI[i].Comments = new Group_Board_Comment_Item[Comments.size()];
                        GBI[i].Count_Of_Comment = Comments.size();

                        for(int j = 0; j < Comments.size(); ++j) {
                            GBI[i].Comments[j] = new Group_Board_Comment_Item(Comments.get(j).select(".comment-name").text(), Comments.get(j).select(".comment-addr").text());
                        }
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
            case "AddComment" :
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_comment_insert.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("CLUB_GRP_ID", voids[1],
                                    "ARTL_NUM", voids[2],
                                    "CMT", voids[3],
                                    "i", "1",
                                    "encoding", "utf-8")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Log.e("Result", doc.text());

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
            case "RemoveComment" :
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_comment_delete.acl")
                            .header("HOST", "lms.knu.ac.kr")
                            .data("CLUB_GRP_ID", voids[1],
                                    "ARTL_NUM", voids[2],
                                    "CMMT_NUM", voids[3],
                                    "i", "1",
                                    "encoding", "utf-8")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Log.e("Result", doc.text());

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
            case "DropGroup" :
                try {
                    Connection.Response res = Jsoup.connect("http://lms.knu.ac.kr/ilos/community/share_auth_drop_me.acl")
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

                    Log.e("Result", doc.text());

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
    protected void onPostExecute(final Map<String, String> success) {
        isUse = false;
    }

    @Override
    protected void onCancelled() {

    }
    
}
