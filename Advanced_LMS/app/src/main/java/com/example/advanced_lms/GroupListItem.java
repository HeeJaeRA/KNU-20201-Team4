package com.example.advanced_lms;

public class GroupListItem {

    private String nameStr ;

    private String infoStr ;

    public void setName(String text) {
        nameStr = text ;
    }

    public void setInfo(String text) {
        infoStr = text ;
    }


    public String getName() {
        return this.nameStr ;
    }

    public String getInfo() {
        return this.infoStr ;
    }

}
