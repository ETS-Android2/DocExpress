package com.example.docexpress;

public class InsertedDocumentRouteDetails {
    private String serial_no,employee_name,employee_rank,department_name,already_sent;

    public InsertedDocumentRouteDetails(String serial_no, String employee_name,String employee_rank, String department_name, String already_sent) {
        this.serial_no = serial_no;
        this.employee_name = employee_name;
        this.employee_rank = employee_rank;
        this.department_name = department_name;
        this.already_sent = already_sent;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }
    public String getEmployee_rank() {
        return employee_rank;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    public void setEmployee_rank(String employee_rank) {
        this.employee_rank = employee_rank;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getAlready_sent() {
        return already_sent;
    }

    public void setAlready_sent(String already_sent) {
        this.already_sent = already_sent;
    }
}
