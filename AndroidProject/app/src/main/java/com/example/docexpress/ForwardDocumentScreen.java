package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ForwardDocumentScreen extends AppCompatActivity {
    private long mLastClickTime = 0;
    LinearLayout send_to_owner_ll,send_to_sender_ll,track_ll;
    TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Applicant_ID, Applicant_Name,
            starting_emp_name,starting_emp_rank,starting_emp_dept,sender_emp_name,sender_emp_rank,sender_emp_dept,sender_emp_note;
    EditText forwad_commetns;
    private RadioGroup radioGroup;
    String document_route="1";
    String comments="None";
    RecyclerView recyclerView;
    List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_document_screen);

        Intent intent = getIntent();
        String strDoc_ID = intent.getStringExtra("Doc_ID");
        String starting_emp_id = intent.getStringExtra("starting_emp_id");
        String sender_emp_id = intent.getStringExtra("sender_emp_id");
        Doc_ID = findViewById(R.id.forward_TrackingID);
        Doc_Name = findViewById(R.id.forward_DocName);
        Start_Date = findViewById(R.id.forward_StartDate);
        Due_Date = findViewById(R.id.forward_DueDate);
        //Attachment = itemView.findViewById(R.id.ongoing_Attachment);
        Status = findViewById(R.id.forward_Status);
        Applicant_ID = findViewById(R.id.forward_ApplicantID);
        Applicant_Name = findViewById(R.id.forward_ApplicantName);
        starting_emp_name = findViewById(R.id.forward_starting_emp_name);
        starting_emp_rank = findViewById(R.id.forward_starting_emp_rank);
        starting_emp_dept = findViewById(R.id.forward_starting_emp_dept);
        sender_emp_name = findViewById(R.id.forward_sender_emp_name);
        sender_emp_rank = findViewById(R.id.forward_sender_emp_rank);
        sender_emp_dept = findViewById(R.id.forward_sender_emp_dept);
        sender_emp_note = findViewById(R.id.forward_sender_emp_note);
        forwad_commetns = findViewById(R.id.forward_comments_text);
        send_to_owner_ll=findViewById(R.id.forward_send_to_owner_button);
        send_to_sender_ll=findViewById(R.id.foward_sendback_button);
        track_ll=findViewById(R.id.forward_track_button);
        String doc_attachment = "1";
        radioGroup = findViewById(R.id.forward_rg);
        radioGroup.check(R.id.forward_default_route_rb);
        MyDbHelper helper4=new MyDbHelper(getApplicationContext());
        Cursor data4=helper4.getdocReceivedDocId(strDoc_ID);
        while (data4.moveToNext())
        {
            Doc_ID.setText(data4.getString(0));
            Doc_Name.setText(data4.getString(1));
            Start_Date.setText(data4.getString(2));
            Due_Date.setText(data4.getString(3));
            //Attachment = itemView.findViewById(R.id.ongoing_Attachment);
            Status.setText(data4.getString(5));
            Applicant_ID.setText(data4.getString(6));
            Applicant_Name.setText(data4.getString(7));
            starting_emp_name.setText(data4.getString(9));
            starting_emp_rank.setText(data4.getString(10));
            starting_emp_dept.setText(data4.getString(12));
            sender_emp_name.setText(data4.getString(14));
            sender_emp_rank.setText(data4.getString(15));
            sender_emp_dept.setText(data4.getString(17));
            sender_emp_note.setText(data4.getString(18));
        }
        MyDbHelper helper6=new MyDbHelper(getApplicationContext());
        helper6.dropdocInsertedTable();
        helper6.createDocInsertedTable();
        String doc_id_ser=Doc_ID.getText().toString();
        String doc_name_ser=Doc_Name.getText().toString();
        String doc_start_date_ser=Start_Date.getText().toString();
        String doc_end_date_ser=Due_Date.getText().toString();
        String doc_attach_ser=doc_attachment;
        String doc_staus_ser = Status.getText().toString();
        String doc_emp_id_ser ="0";// .getText().toString();
        String doc_app_id_ser = Applicant_ID.getText().toString();
        String doc_emp_name_ser = starting_emp_name.getText().toString();
        String doc_dept_name_ser = starting_emp_dept.getText().toString();
        String doc_app_name_ser = Applicant_Name.getText().toString();
        helper6.insertDocInsertedDetails(doc_id_ser,doc_name_ser,doc_start_date_ser,
                doc_end_date_ser,doc_attach_ser,doc_staus_ser,doc_emp_id_ser,doc_app_id_ser,
                doc_emp_name_ser,doc_dept_name_ser,doc_app_name_ser);
        helper6.close();
        send_to_owner_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String user_id_from_table="0";
                MyDbHelper helperuser3=new MyDbHelper(getApplicationContext());
                Cursor datauser3= helperuser3.getuserID();
                while (datauser3.moveToNext())
                {
                    user_id_from_table=datauser3.getString(0);
                }
                datauser3.close();
                helperuser3.close();
                //Toast.makeText(c,doc_id,Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/insert_doc_status.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("doc_id", Doc_ID.getText().toString());
                        jsonBody.put("emp_id_sender", user_id_from_table);
                        jsonBody.put("emp_id_receiver", starting_emp_id);
                        checkCredentials();
                        jsonBody.put("comments", comments);
                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = response.getJSONArray("doc");
                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
                                    if(received_data_head.getString("reqcode").equalsIgnoreCase("2"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Document Sent Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForwardDocumentScreen.this, Home.class);
                                        v.getContext().startActivity(intent);
                                    }
                                    else if(received_data_head.getString("reqcode").equalsIgnoreCase("3"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Document Sending Failed",Toast.LENGTH_SHORT).show();
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

            }
        });
        send_to_sender_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String user_id_from_table="0";
                MyDbHelper helperuser3=new MyDbHelper(getApplicationContext());
                Cursor datauser3= helperuser3.getuserID();
                while (datauser3.moveToNext())
                {
                    user_id_from_table=datauser3.getString(0);
                }
                datauser3.close();
                helperuser3.close();
                //Toast.makeText(c,doc_id,Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/insert_doc_status.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("doc_id", Doc_ID.getText().toString());
                        jsonBody.put("emp_id_sender", user_id_from_table);
                        jsonBody.put("emp_id_receiver", sender_emp_id);
                        checkCredentials();
                        jsonBody.put("comments", comments);
                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = response.getJSONArray("doc");
                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
                                    if(received_data_head.getString("reqcode").equalsIgnoreCase("2"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Document Sent Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForwardDocumentScreen.this, Home.class);
                                        v.getContext().startActivity(intent);
                                    }
                                    else if(received_data_head.getString("reqcode").equalsIgnoreCase("3"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Document Sending Failed",Toast.LENGTH_SHORT).show();
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
            }
        });
        track_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_detail.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("doc_id", Doc_ID.getText().toString());
                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = response.getJSONArray("doc");
                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
                                    MyDbHelper helper=new MyDbHelper(v.getContext());
                                    helper.dropdoctrackTable();
                                    helper.createDocTrackTable();
                                    for(int i=1;i<jsonArray.length();i++)
                                    {
                                        JSONObject docdetail=jsonArray.getJSONObject(i);
                                        String step=docdetail.getString("step");
                                        String rec_date=docdetail.getString("rec_date");
                                        String emp_id=docdetail.getString("emp_id");
                                        String comments=docdetail.getString("comments");
                                        helper.insertDocTrackDetails(step,rec_date,emp_id,comments);
                                    }
                                    helper.close();
                                    Intent intent = new Intent(ForwardDocumentScreen.this, ReviewApplicationScreen.class);
                                    intent.putExtra("Doc_ID",received_data_head.getString("doc_id"));
                                    intent.putExtra("Doc_Name",received_data_head.getString("doc_name"));
                                    intent.putExtra("Start_Date",received_data_head.getString("doc_start_date") );
                                    intent.putExtra("Due_Date", received_data_head.getString("doc_due_date"));
                                    intent.putExtra("Attachment", received_data_head.getString("doc_attachment"));
                                    intent.putExtra("Status", received_data_head.getString("doc_status"));
                                    intent.putExtra("Emp_Name", received_data_head.getString("emp_name"));
                                    intent.putExtra("Department", received_data_head.getString("dept_name"));
                                    intent.putExtra("Applicant_ID", received_data_head.getString("app_id"));
                                    intent.putExtra("Applicant_Name", received_data_head.getString("app_name"));
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast t2 = Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT);
                                    t2.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(v.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.forward_default_route_rb:
                        //Toast.makeText(getApplicationContext(), "Defualt Route", Toast.LENGTH_SHORT).show();
                        document_route="1";
                        String doc_name_selected=Doc_Name.getText().toString();
                        RequestQueue requestQueue;
                        JsonObjectRequest request;
                        try {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_route.php";
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
                                jsonBody.put("doc_name", doc_name_selected);
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
                                                Toast.makeText(getApplicationContext(),"No Default Route Found",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                                helper.dropdocrouteTable();
                                                helper.createDocRouteTable();
                                                for(int i=1;i<jsonArray.length();i++)
                                                {
                                                    JSONObject docdetail=jsonArray.getJSONObject(i);
                                                    String doc_id_ser=docdetail.getString("doc_step_no");
                                                    String doc_name_ser=docdetail.getString("doc_name");
                                                    String doc_start_date_ser=docdetail.getString("emp_id");
                                                    String doc_end_date_ser=docdetail.getString("emp_name");
                                                    String doc_app_id_ser=docdetail.getString("emp_rank");
                                                    String doc_app_name_ser=docdetail.getString("dept_name");
                                                    helper.insertDocRouteTable(doc_id_ser,doc_name_ser,doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                                }
                                                helper.close();
                                                //startActivity(new Intent(newApplicationScreen.this, InsetedDocument.class));
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
                        recyclerView=findViewById(R.id.rv_forward_document);
                        insertedDocumentRouteDetails=new ArrayList<>();
                        MyDbHelper helper2=new MyDbHelper(getApplicationContext());
                        Cursor data2=helper2.getdocRoute();
                        while (data2.moveToNext())
                        {
                            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails(data2.getString(0),data2.getString(3),data2.getString(4),data2.getString(5),"No"));
                        }
//                        insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
                        MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,comments,ForwardDocumentScreen.this);
                        RecyclerView.LayoutManager lm=new LinearLayoutManager(ForwardDocumentScreen.this);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute);
                        break;
                    case R.id.forward_custom_route_rb:
                        //Toast.makeText(getApplicationContext(), "Custom Route", Toast.LENGTH_SHORT).show();
                        document_route="2";
                        recyclerView=findViewById(R.id.rv_forward_document);
                        insertedDocumentRouteDetails=new ArrayList<>();
                        RequestQueue requestQueue2;
                        JsonObjectRequest request2;
                        try {
                            requestQueue2 = Volley.newRequestQueue(getApplicationContext());
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
                                request2= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
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
                                                MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute2=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,comments,ForwardDocumentScreen.this);
                                                RecyclerView.LayoutManager lm2=new LinearLayoutManager(ForwardDocumentScreen.this);
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
                                requestQueue2.add(request2);
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
                        //insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("2","Talat Naeem","Flex Team","Flex","No"));;
                        break;
                }
            }
        });
        if(document_route.equalsIgnoreCase("1"))
        {
            String doc_name_selected=Doc_Name.getText().toString();
            RequestQueue requestQueue;
            JsonObjectRequest request;
            try {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_route.php";
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
                    jsonBody.put("doc_name", doc_name_selected);
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
                                    Toast.makeText(getApplicationContext(),"No Default Route Found",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                    helper.dropdocrouteTable();
                                    helper.createDocRouteTable();
                                    for(int i=1;i<jsonArray.length();i++)
                                    {
                                        JSONObject docdetail=jsonArray.getJSONObject(i);
                                        String doc_id_ser=docdetail.getString("doc_step_no");
                                        String doc_name_ser=docdetail.getString("doc_name");
                                        String doc_start_date_ser=docdetail.getString("emp_id");
                                        String doc_end_date_ser=docdetail.getString("emp_name");
                                        String doc_app_id_ser=docdetail.getString("emp_rank");
                                        String doc_app_name_ser=docdetail.getString("dept_name");
                                        helper.insertDocRouteTable(doc_id_ser,doc_name_ser,doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                    }
                                    helper.close();
                                    //startActivity(new Intent(newApplicationScreen.this, InsetedDocument.class));
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

            recyclerView=findViewById(R.id.rv_forward_document);
            insertedDocumentRouteDetails=new ArrayList<>();
            MyDbHelper helper2=new MyDbHelper(getApplicationContext());
            Cursor data2=helper2.getdocRoute();
            while (data2.moveToNext())
            {
                insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails(data2.getString(0),data2.getString(3),data2.getString(4),data2.getString(5),"No"));
            }
//            insertedDocumentRouteDetails.add(new InsertedDocumentRouteDetails("1","Khilat Mehdi","Academics Officer","CS Academics","No"));
            MyRvAdapterInsertedDocumentRoute myRvAdapterInsertedDocumentRoute=new MyRvAdapterInsertedDocumentRoute(insertedDocumentRouteDetails,comments,this);
            RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(myRvAdapterInsertedDocumentRoute);
        }
    }
    public void checkCredentials() {
        if (isEmpty(forwad_commetns)) {
            comments="None";
        }
        else
        {
            comments=forwad_commetns.getText().toString();
        }
    }
    public boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}