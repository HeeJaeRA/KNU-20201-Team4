package com.example.advanced_lms;

public class BoardListItem {
        private String titleStr ;
        private String descStr ;

        public void setTitle(String title) {
            titleStr = title ;
        }
        public void setDesc(String desc) {
            descStr = desc ;
        }

        public String getTitle() {
            return this.titleStr ;
        }
        public String getDesc() {
            return this.descStr ;
        }
}

