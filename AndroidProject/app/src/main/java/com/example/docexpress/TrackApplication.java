package com.example.docexpress;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
    private long mLastClickTime = 0;
    boolean isValid3=true;
    AppCompatButton trackApp_Button;
    AppCompatButton trackApplication_backButton;
    EditText track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_application);
        trackApp_Button = findViewById(R.id.trackApp_Button);
        track=findViewById(R.id.trackApp_track);
        trackApp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isValid3 = true;
                checkCredentials();
                if (isValid3)
                {
                    RequestQueue requestQueue;
                    JsonObjectRequest request;
                    try {
                        //Toast.makeText(getApplicationContext(),"Inside line 157",Toast.LENGTH_SHORT).show();
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        //String URL = "http://192.168.1.16/fyp/mobile/get_doc_detail.php";
                        String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_detail.php";
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
        if (isEmpty(track)) {
            track.setError("Please Enter Tracking ID");
            isValid3 = false;
        }
    }
    public boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}