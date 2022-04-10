package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InsetedDocument extends AppCompatActivity {

    TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Emp_ID,Emp_Name, Department, Applicant_ID, Applicant_Name;
    private RadioGroup radioGroup;
    String document_route="1";
    RecyclerView recyclerView;
    List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseted_document);

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
        radioGroup=findViewById(R.id.inserted_rg);
        radioGroup.check(R.id.inserted_default_route_rb);

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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.inserted_default_route_rb:
                        //Toast.makeText(getApplicationContext(), "Defualt Route", Toast.LENGTH_SHORT).show();
                        document_route="1";
                        recyclerView=findViewById(R.id.rv_inserted_document);
                        insertedDocumentRouteDetails=new ArrayList<>();
                        //MyDbHelper helper=new MyDbHelper(getApplicationContext());
                        //Cursor data=helper.getdocOngoing();
                        //while (data.moveToNext())
                        //{
                        //   ongoing_doc.add(new Ongoing_doc_class(data.getString(0),data.getString(1),data.getString(2),data.getString(3),"On Going",data.getString(4),data.getString(5)));
                        //}
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,InsetedDocument.this);
                        RecyclerView.LayoutManager lm=new LinearLayoutManager(InsetedDocument.this);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute);
                        break;
                    case R.id.inserted_custom_route_rb:
                        //Toast.makeText(getApplicationContext(), "Custom Route", Toast.LENGTH_SHORT).show();
                        document_route="2";
                        recyclerView=findViewById(R.id.rv_inserted_document);
                        insertedDocumentRouteDetails=new ArrayList<>();
                        //MyDbHelper helper=new MyDbHelper(getApplicationContext());
                        //Cursor data=helper.getdocOngoing();
                        //while (data.moveToNext())
                        //{
                        //   ongoing_doc.add(new Ongoing_doc_class(data.getString(0),data.getString(1),data.getString(2),data.getString(3),"On Going",data.getString(4),data.getString(5)));
                        //}
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));
                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));
                        MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute2=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,InsetedDocument.this);
                        RecyclerView.LayoutManager lm2=new LinearLayoutManager(InsetedDocument.this);
                        recyclerView.setLayoutManager(lm2);
                        recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute2);
                        break;
                }
            }
        });
        if(document_route.equalsIgnoreCase("1"))
        {
            recyclerView=findViewById(R.id.rv_inserted_document);
            insertedDocumentRouteDetails=new ArrayList<>();
            //MyDbHelper helper=new MyDbHelper(getApplicationContext());
            //Cursor data=helper.getdocOngoing();
            //while (data.moveToNext())
            //{
            //   ongoing_doc.add(new Ongoing_doc_class(data.getString(0),data.getString(1),data.getString(2),data.getString(3),"On Going",data.getString(4),data.getString(5)));
            //}
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,this);
            RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute);
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(InsetedDocument.this,Home.class));
        finish();
    }
}