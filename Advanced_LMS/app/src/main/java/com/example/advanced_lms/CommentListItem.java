package com.example.advanced_lms;

public class CommentListItem {

        private String WriterStr ;
        private String TimeStr ;
        private String CommentStr ;

        public void setWriter(String writer) {
            WriterStr = writer ;
        }
        public void setTime(String time) {
            TimeStr = time ;
        }
        public void setComment(String comment) {
            CommentStr = comment ;
    }

        public String getWriter() {
            return this.WriterStr ;
        }
        public String getTime() {
            return this.TimeStr ;
        }
        public String getComment() {
        return this.CommentStr ;
    }
}

