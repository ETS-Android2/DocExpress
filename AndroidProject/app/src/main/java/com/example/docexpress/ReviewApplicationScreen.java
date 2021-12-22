package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

public class ReviewApplicationScreen extends AppCompatActivity {

    TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Emp_Name, Department, Applicant_ID, Applicant_Name;
    String strDoc_ID, strDoc_Name, strStart_Date, strDue_Date, strAttachment, strStatus, strEmp_Name, strDepartment, strApplicant_ID, strApplicant_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_application_screen);

        Doc_ID= findViewById(R.id.review_TrackingID);
        Doc_Name = findViewById(R.id.review_DocName);
        Start_Date = findViewById(R.id.review_StartDate);
        Due_Date = findViewById(R.id.review_DueDate);
        Attachment = findViewById(R.id.review_Attachment);
        Status = findViewById(R.id.review_Status);
        Emp_Name = findViewById(R.id.review_EmployeeName);
        Department = findViewById(R.id.review_Department);
        Applicant_ID = findViewById(R.id.review_ApplicantID);
        Applicant_Name = findViewById(R.id.review_ApplicantName);

        Intent intent = getIntent();

        strDoc_ID = intent.getStringExtra("Doc_ID");
        strDoc_Name = intent.getStringExtra("Doc_Name");
        strStart_Date = intent.getStringExtra("Start_Date");
        strDue_Date = intent.getStringExtra("Due_Date");
        strAttachment = intent.getStringExtra("Attachment");
        strStatus = intent.getStringExtra("Status");
        strEmp_Name = intent.getStringExtra("Emp_Name");
        strDepartment = intent.getStringExtra("Department");
        strApplicant_ID = intent.getStringExtra("Applicant_ID");
        strApplicant_Name = intent.getStringExtra("Applicant_Name");

        Doc_ID.setText(strDoc_ID);
        Doc_Name.setText(strDoc_Name);
        Start_Date.setText(strStart_Date);
        Due_Date.setText(strDue_Date);
        Attachment.setText(strAttachment);
        Status.setText(strStatus);
        Emp_Name.setText(strEmp_Name);
        Department.setText(strDepartment);
        Applicant_ID.setText(strApplicant_ID);
        Applicant_Name.setText(strApplicant_Name);
    }
}