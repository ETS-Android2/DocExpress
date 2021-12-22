package com.example.docexpress;

public class TrackDocument_Class {
    public int doc_id;
    public String doc_name;
    public String doc_start_date;
    public String doc_due_date;
    public int doc_attachment;
    public int doc_status;
    public String emp_name;
    public int app_id;
    public TrackDocument_Class()
    {

    }

    public TrackDocument_Class(int doc_id, String doc_name, String doc_start_date, String doc_due_date, int doc_attachment, int doc_status, String emp_name, int app_id) {
        this.doc_id = doc_id;
        this.doc_name = doc_name;
        this.doc_start_date = doc_start_date;
        this.doc_due_date = doc_due_date;
        this.doc_attachment = doc_attachment;
        this.doc_status = doc_status;
        this.emp_name = emp_name;
        this.app_id = app_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_start_date() {
        return doc_start_date;
    }

    public void setDoc_start_date(String doc_start_date) {
        this.doc_start_date = doc_start_date;
    }

    public String getDoc_due_date() {
        return doc_due_date;
    }

    public void setDoc_due_date(String doc_due_date) {
        this.doc_due_date = doc_due_date;
    }

    public int getDoc_attachment() {
        return doc_attachment;
    }

    public void setDoc_attachment(int doc_attachment) {
        this.doc_attachment = doc_attachment;
    }

    public int getDoc_status() {
        return doc_status;
    }

    public void setDoc_status(int doc_status) {
        this.doc_status = doc_status;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }
}
