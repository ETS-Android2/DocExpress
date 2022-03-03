package com.example.docexpress;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class TrackApplication extends AppCompatActivity {
    String strServer_msg,strServer_code,strDoc_ID, strDoc_Name, strStart_Date, strDue_Date, strAttachment, strStatus, strEmp_Name, strDepartment, strApplicant_ID, strApplicant_Name;
    boolean isValid3=true;
    AppCompatButton trackApp_Button;
    AppCompatButton trackApplication_backButton;
    EditText track;
    String server_response;
    JSONObject jo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_application);
        trackApp_Button = findViewById(R.id.trackApp_Button);
        track=findViewById(R.id.trackApp_track);
        //int track_id=Integer.parseInt(track.getText().toString());
        trackApp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid3 = true;
                checkCredentials();
                if (isValid3)
                {
                    /*HttpParams params= new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(params,5000);
                    //HttpConnectionParams.setSoTimeout(params,5000);
                    HttpClient httpclient = new DefaultHttpClient(params);
                    //HttpPost httppost=new HttpPost( "http://192.168.18.83/fyp/loginget.php?email="+user_name+"pass="+pass_word);
                    HttpPost httppost=new HttpPost( "http://192.168.1.13/fyp/mobile/get_doc_detail.php");
                    BasicNameValuePair usernameBasicNameValuePair2 = new BasicNameValuePair("doc_id", track.getText().toString());
                    //List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                    List<NameValuePair> nameValuePairList2 = new ArrayList<NameValuePair>();
                    nameValuePairList2.add(usernameBasicNameValuePair2);
                    try {
                        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList2);
                        //UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
                        httppost.setEntity(urlEncodedFormEntity);
                        HttpResponse response=httpclient.execute(httppost);
                        try {
                            InputStream inputStream=response.getEntity().getContent();
                            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder stringBuilder=new StringBuilder();
                            while((server_response=bufferedReader.readLine())!=null)
                            {
                                stringBuilder.append(server_response+"\n");
                            }
                            try{
                                server_response=stringBuilder.toString();
                                //nodeRoot  = new JSONObject(server_response);
                                //JSONObject nodeStats = nodeRoot.getJSONObject("id");
                                //sSDR = nodeStats.toString();
                                jo=new JSONObject(server_response);
                                strServer_msg=jo.getString("reqmsg");
                                strServer_code=jo.getString("reqcode");
                                if(strServer_code.equalsIgnoreCase("1"))
                                {
                                    strDoc_ID=jo.getString("doc_id");
                                    strDoc_Name= jo.getString("doc_name");
                                    strStart_Date=jo.getString("doc_start_date");
                                    strDue_Date=jo.getString("doc_due_date");
                                    strAttachment=jo.getString("doc_attachment");
                                    strStatus=jo.getString("doc_status");
                                    if(strStatus.equalsIgnoreCase("1"))
                                    { strStatus="Completed"; }
                                    else if(strStatus.equalsIgnoreCase("0")) {
                                        strStatus="On Going"; }
                                    strEmp_Name=jo.getString("emp_name");
                                    strDepartment=jo.getString("dept_name");
                                    strApplicant_ID=jo.getString("app_id");
                                    strApplicant_Name=jo.getString("app_name");
                                    Intent intent = new Intent(TrackApplication.this, ReviewApplicationScreen.class);
                                    intent.putExtra("Doc_ID", strDoc_ID);
                                    intent.putExtra("Doc_Name", strDoc_Name);
                                    intent.putExtra("Start_Date", strStart_Date);
                                    intent.putExtra("Due_Date", strDue_Date);
                                    intent.putExtra("Attachment", strAttachment);
                                    intent.putExtra("Status", strStatus);
                                    intent.putExtra("Emp_Name", strEmp_Name);
                                    intent.putExtra("Department", strDepartment);
                                    intent.putExtra("Applicant_ID", strApplicant_ID);
                                    intent.putExtra("Applicant_Name", strApplicant_Name);
                                    startActivity(intent); }
                                else if(strServer_code.equalsIgnoreCase("3") || (strServer_code.equalsIgnoreCase("2")))
                                {
                                    Toast t2 = Toast.makeText(getApplicationContext(),strServer_msg,Toast.LENGTH_SHORT);
                                    t2.show();
                                } }
                            catch (Exception e)
                            {
                                String a=e.toString();
                                Toast t = Toast.makeText(getApplicationContext(),"Arshad",Toast.LENGTH_SHORT);
                                t.show();

                            }
                            bufferedReader.close();
                            inputStream.close();
                            String s=stringBuilder.toString();
                            //Toast t = Toast.makeText(getApplicationContext(),sSDR,Toast.LENGTH_SHORT);
                            //t.show();
                        }
                        catch (Exception e)
                        {
                            String a=e.toString();
                            Toast t = Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                    catch(Exception e)
                    {

                        //Toast t = Toast.makeText(getApplicationContext(),"Failed To Connect",Toast.LENGTH_SHORT);
                        //t.show();
                    }*/
                    //startActivity(new Intent(TrackApplication.this, ReviewApplicationScreen.class));
                    //String a=e.toString();
                    //Toast t = Toast.makeText(getApplicationContext(),"Button presses",Toast.LENGTH_SHORT);
                    //t.show();
                    RequestQueue requestQueue;
                    JsonObjectRequest request;
                    try {
                        //Toast.makeText(getApplicationContext(),"Inside line 157",Toast.LENGTH_SHORT).show();
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        //String URL = "http://192.168.1.16/fyp/mobile/get_doc_detail.php";
                        String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_detail.php";
                        //String url2="https://api.jsonserve.com/YLiiz4";
                        JSONObject jsonBody = new JSONObject();
                        try {
                            //Toast.makeText(getApplicationContext(),"Inside line 162",Toast.LENGTH_SHORT).show();
                            jsonBody.put("doc_id", track.getText().toString());
                            request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JSONArray jsonArray;
                                    try {
                                        jsonArray = response.getJSONArray("doc");
                                        //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                                        //Log.d("Length:",getString(jsonArray.length()));
//                                        for (int i=0;i<jsonArray.length();i++)
//                                        {
//                                            JSONObject employee=jsonArray.getJSONObject(i);
//                                            Toast t2 = Toast.makeText(getApplicationContext(),employee.toString(),Toast.LENGTH_SHORT);
//                                            t2.show();
//
//                                        }
                                        JSONObject received_data_head=jsonArray.getJSONObject(0);
                                        if(received_data_head.getString("reqcode").equalsIgnoreCase("3"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Docuement ID format not Correct",Toast.LENGTH_SHORT).show();
                                        }
                                        else if(received_data_head.getString("reqcode").equalsIgnoreCase("2"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Docuement Not Found",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            MyDbHelper helper=new MyDbHelper(getApplicationContext());
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
                                            Intent intent = new Intent(TrackApplication.this, ReviewApplicationScreen.class);
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
                                            //startActivity(new Intent(TrackApplication.this, ReviewApplicationScreen.class));
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
                            //jsonBody.put("doc_id", track.getText().toString());
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
            }
        });

        trackApplication_backButton = findViewById(R.id.trackApplication_backButton);

        trackApplication_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void checkCredentials() {
        String check_doc_id_str=track.getText().toString();
        //int check_doc_id=Integer.parseInt(check_doc_id_str);
        boolean check_in=true;
        if (isEmpty(track)) {
            track.setError("Please Enter Tracking ID");
            isValid3 = false;
            check_in=false;
        }
        /*if(check_in) {
            int check_doc_id= Integer.decode(check_doc_id_str);
         if (check_doc_id < 800000 && check_doc_id > 899999) {
                track.setError("Please Enter Tracking ID in Correct Format");
                isValid3 = false;
            }
        }*/
    }
    public boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}