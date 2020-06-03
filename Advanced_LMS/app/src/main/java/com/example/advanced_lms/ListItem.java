package com.example.advanced_lms;

public class ListItem {
    String sName;
    String sTime;

    public ListItem(String sName, String sTime){
        this.sName = sName;
        this.sTime = sTime;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }
}
