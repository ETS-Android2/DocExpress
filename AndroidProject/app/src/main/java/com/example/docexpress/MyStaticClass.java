package com.example.docexpress;

public class MyStaticClass {
    public Employee emp=new Employee();

    public void setEmp(Employee emp,int id) {
        this.emp = emp;
        emp.setId(id);
    }
}
