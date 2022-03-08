package com.example.docexpress;

public class Ongoing_doc_class {
    private String tracking_id,doc_name,start_date,end_date,status,applicant_id,applicant_name;

    public Ongoing_doc_class(String tracking_id, String doc_name, String start_date, String end_date, String status, String applicant_id, String applicant_name) {
        this.tracking_id = tracking_id;
        this.doc_name = doc_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.applicant_id = applicant_id;
        this.applicant_name = applicant_name;
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
}
