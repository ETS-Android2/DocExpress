package com.example.docexpress;

public class tracked_document {
    private String doc_step_no,doc_rec_date,doc_emp_id,doc_comments;

    public tracked_document(String doc_step_no, String doc_rec_date, String doc_emp_id, String doc_comments) {
        this.doc_step_no = doc_step_no;
        this.doc_rec_date = doc_rec_date;
        this.doc_emp_id = doc_emp_id;
        this.doc_comments = doc_comments;
    }


    public String getDoc_step_no() {
        return doc_step_no;
    }

    public void setDoc_step_no(String doc_step_no) {
        this.doc_step_no = doc_step_no;
    }

    public String getDoc_rec_date() {
        return doc_rec_date;
    }

    public void setDoc_rec_date(String doc_rec_date) {
        this.doc_rec_date = doc_rec_date;
    }

    public String getDoc_emp_id() {
        return doc_emp_id;
    }

    public void setDoc_emp_id(String doc_emp_id) {
        this.doc_emp_id = doc_emp_id;
    }

    public String getDoc_comments() {
        return doc_comments;
    }

    public void setDoc_comments(String doc_comments) {
        this.doc_comments = doc_comments;
    }
}
