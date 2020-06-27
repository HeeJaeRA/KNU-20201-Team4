package com.example.advanced_lms;

public class CommentListItem {
        private String WriterStr ;
        private String CommentStr ;

        public void setWriter(String writer) {
            WriterStr = writer ;
        }
        public void setComment(String comment) {
            CommentStr = comment ;
    }

        public String getWriter() {
            return this.WriterStr ;
        }
        public String getComment() {
        return this.CommentStr ;
    }
}

