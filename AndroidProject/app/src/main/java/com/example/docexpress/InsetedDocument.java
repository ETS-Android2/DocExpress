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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                        MyDbHelper helper2=new MyDbHelper(getApplicationContext());
                        Cursor data2=helper2.getdocRoute();
                        while (data2.moveToNext())
                        {
                            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails(data2.getString(0),data2.getString(3),data2.getString(4),data2.getString(5),"No"));
                        }
                        //insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
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
                        RequestQueue requestQueue;
                        JsonObjectRequest request;
                        try {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_customized_route.php";
                            JSONObject jsonBody = new JSONObject();
                            try {
                                String user_id_from_table="0";
                                MyDbHelper helperuser=new MyDbHelper(getApplicationContext());
                                Cursor datauser= helperuser.getuserID();
                                while (datauser.moveToNext())
                                {
                                    user_id_from_table=datauser.getString(0);
                                }
                                datauser.close();
                                helperuser.close();
                                jsonBody.put("emp_id", user_id_from_table);
                                request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        JSONArray jsonArray;
                                        try {
                                            jsonArray = response.getJSONArray("doc");
                                            JSONObject received_data_head=jsonArray.getJSONObject(0);
                                            if(received_data_head.getString("reqcode").equalsIgnoreCase("1"))
                                            {
                                                Toast.makeText(getApplicationContext(),"No Route Found",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                MyDbHelper helper_customized=new MyDbHelper(getApplicationContext());
                                                helper_customized.dropdocroutecustomizedTable();
                                                helper_customized.createDocRouteCustomizedTable();
                                                for(int i=1;i<jsonArray.length();i++)
                                                {
                                                    JSONObject docdetail=jsonArray.getJSONObject(i);
                                                    String doc_id_ser=docdetail.getString("doc_step_no");
                                                    //String doc_name_ser=docdetail.getString("doc_name");
                                                    String doc_start_date_ser=docdetail.getString("emp_id");
                                                    String doc_end_date_ser=docdetail.getString("emp_name");
                                                    String doc_app_id_ser=docdetail.getString("emp_rank");
                                                    String doc_app_name_ser=docdetail.getString("dept_name");
                                                    helper_customized.insertDocRouteCustomizedTable(doc_id_ser,"NA",doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                                }
                                                helper_customized.close();
                                                //startActivity(new Intent(InsetedDocument.this, InsetedDocument.class));
                                                MyDbHelper helper_customized2=new MyDbHelper(getApplicationContext());
                                                Cursor data2_customized2=helper_customized2.getdocRouteCustomized();
                                                while (data2_customized2.moveToNext())
                                                {
                                                    insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails(data2_customized2.getString(0),data2_customized2.getString(3),data2_customized2.getString(4),data2_customized2.getString(5),"No"));
                                                }
                                                MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute2=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,InsetedDocument.this);
                                                RecyclerView.LayoutManager lm2=new LinearLayoutManager(InsetedDocument.this);
                                                recyclerView.setLayoutManager(lm2);
                                                recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute2);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast t2 = Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                                            t2.show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                requestQueue.add(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                        //startActivity(new Intent(newApplicationScreen.this, InsetedDocument.class));
                        //Toast.makeText(getApplicationContext(),docdetail.toString(),Toast.LENGTH_SHORT).show();
                        //insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));
                        break;
                }
            }
        });
        if(document_route.equalsIgnoreCase("1"))
        {
            recyclerView=findViewById(R.id.rv_inserted_document);
            insertedDocumentRouteDetails=new ArrayList<>();
            MyDbHelper helper2=new MyDbHelper(getApplicationContext());
            Cursor data2=helper2.getdocRoute();
            while (data2.moveToNext())
            {
               insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails(data2.getString(0),data2.getString(3),data2.getString(4),data2.getString(5),"No"));
            }
            //insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
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