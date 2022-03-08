package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewApplicationScreen extends AppCompatActivity {
    RecyclerView rv;
    List<tracked_document> track_doc_list;

    TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Emp_Name, Department, Applicant_ID, Applicant_Name;
    String strDoc_ID, strDoc_Name, strStart_Date, strDue_Date, strAttachment, strStatus, strEmp_Name, strDepartment, strApplicant_ID, strApplicant_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_application_screen);
        rv=findViewById(R.id.rv);
        track_doc_list=new ArrayList<>();
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdoctracking();
        while (data.moveToNext())
        {
            track_doc_list.add(new tracked_document(data.getString(0),data.getString(1),data.getString(2),data.getString(3)));
        }

        MyRvAdapter adapter=new MyRvAdapter(track_doc_list,this);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        Doc_ID= findViewById(R.id.review_TrackingID);
        Doc_Name = findViewById(R.id.review_DocName);
        Start_Date = findViewById(R.id.review_StartDate);
        Due_Date = findViewById(R.id.review_DueDate);
        //Attachment = findViewById(R.id.review_Attachment);
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
        //Attachment.setText(strAttachment);
        if(strStatus.equalsIgnoreCase("1"))
        {
            Status.setText("Completed");
        }
        else if(strStatus.equalsIgnoreCase("2"))
        {
            Status.setText("On Going");
        }
        else if(strStatus.equalsIgnoreCase("3"))
        {
            Status.setText("Returned");
        }
        else if(strStatus.equalsIgnoreCase("4"))
        {
            Status.setText("Rejected");
        }
        Emp_Name.setText(strEmp_Name);
        Department.setText(strDepartment);
        Applicant_ID.setText(strApplicant_ID);
        Applicant_Name.setText(strApplicant_Name);
    }
}