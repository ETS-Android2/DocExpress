package com.example.docexpress;

public class ReceivedDocumentClass {
    private String tracking_id,doc_name,start_date,end_date,status,applicant_id,applicant_name,
            starting_emp_name,starting_emp_rank,starting_emp_dept,sender_emp_name,sender_emp_rank,sender_emp_dept,sender_emp_note;

    public ReceivedDocumentClass(String tracking_id, String doc_name, String start_date, String end_date, String status,
                                 String applicant_id, String applicant_name, String starting_emp_name,
                                 String starting_emp_rank, String starting_emp_dept,
                                 String sender_emp_name, String sender_emp_rank, String sender_emp_dept, String sender_emp_note) {
        this.tracking_id = tracking_id;
        this.doc_name = doc_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.applicant_id = applicant_id;
        this.applicant_name = applicant_name;
        this.starting_emp_name = starting_emp_name;
        this.starting_emp_rank = starting_emp_rank;
        this.starting_emp_dept = starting_emp_dept;
        this.sender_emp_name = sender_emp_name;
        this.sender_emp_rank = sender_emp_rank;
        this.sender_emp_dept = sender_emp_dept;
        this.sender_emp_note = sender_emp_note;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getStarting_emp_name() {
        return starting_emp_name;
    }

    public void setStarting_emp_name(String starting_emp_name) {
        this.starting_emp_name = starting_emp_name;
    }

    public String getStarting_emp_rank() {
        return starting_emp_rank;
    }

    public void setStarting_emp_rank(String starting_emp_rank) {
        this.starting_emp_rank = starting_emp_rank;
    }

    public String getStarting_emp_dept() {
        return starting_emp_dept;
    }

    public void setStarting_emp_dept(String starting_emp_dept) {
        this.starting_emp_dept = starting_emp_dept;
    }

    public String getSender_emp_name() {
        return sender_emp_name;
    }

    public void setSender_emp_name(String sender_emp_name) {
        this.sender_emp_name = sender_emp_name;
    }

    public String getSender_emp_rank() {
        return sender_emp_rank;
    }

    public void setSender_emp_rank(String sender_emp_rank) {
        this.sender_emp_rank = sender_emp_rank;
    }

    public String getSender_emp_dept() {
        return sender_emp_dept;
    }

    public void setSender_emp_dept(String sender_emp_dept) {
        this.sender_emp_dept = sender_emp_dept;
    }

    public String getSender_emp_note() {
        return sender_emp_note;
    }

    public void setSender_emp_note(String sender_emp_note) {
        this.sender_emp_note = sender_emp_note;
    }
}