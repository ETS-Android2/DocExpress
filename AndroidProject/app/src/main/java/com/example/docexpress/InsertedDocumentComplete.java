package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InsertedDocumentComplete extends AppCompatActivity {
    AppCompatButton back_to_home_button;
    TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Emp_ID,Emp_Name, Department, Applicant_ID, Applicant_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserted_document_complete);
        Doc_ID= findViewById(R.id.inserted_TrackingID);
        Doc_Name = findViewById(R.id.inserted_DocName);
        Start_Date = findViewById(R.id.inserted_StartDate);
        Due_Date = findViewById(R.id.inserted_DueDate);
        //Attachment = findViewById(R.id.inserted_Attachment);
        Status = findViewById(R.id.inserted_Status);
        Emp_ID = findViewById(R.id.inserted_Emp_ID);
        Emp_Name = findViewById(R.id.inserted_EmployeeName);
        Department = findViewById(R.id.inserted_Department);
        Applicant_ID = findViewById(R.id.inserted_ApplicantID);
        Applicant_Name = findViewById(R.id.inserted_ApplicantName);
        String doc_attachment="";
        back_to_home_button=findViewById(R.id.back_to_home_inserted_complete);

        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocInserted();
        while (data.moveToNext())
        {
            Doc_ID.setText(data.getString(0));
            Doc_Name.setText(data.getString(1));
            Start_Date.setText(data.getString(2));
            Due_Date.setText(data.getString(3));
            doc_attachment=data.getString(4);
            Status.setText(data.getString(5));
            Emp_ID.setText(data.getString(6));
            Applicant_ID.setText(data.getString(7));
            Emp_Name.setText(data.getString(8));
            Department.setText(data.getString(9));
            Applicant_Name.setText(data.getString(10));

        }
        //Toast.makeText(getApplicationContext(),Doc_Name.getText().toString(),Toast.LENGTH_SHORT).show();
        back_to_home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsertedDocumentComplete.this,Home.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(InsertedDocumentComplete.this,Home.class));
        finish();
    }
}