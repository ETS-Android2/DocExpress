package com.example.docexpress;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class newApplicationScreen extends AppCompatActivity {

    String document_status="1";
    Boolean isFileSelected=false;
    private RadioGroup radioGroup;
    private int mDate,mMonth,mYear;
    private ImageView uploaded_file_name_close,upload_file_button_image;
    private TextView uploaded_file_name_text;
    Button submit;
    ImageView duedateimg;
    EditText applicantid;
    EditText duedate;
    int applicant_id;
    boolean isValid2=true;
    AppCompatButton newApplication_backButton;
    private Spinner spinner;
    String doc_name_selected="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent2 = getIntent();
        String doc_size = intent2.getStringExtra("doc_numbers");
        //Toast.makeText(getApplicationContext(),doc_size,Toast.LENGTH_SHORT).show();
        int doc_size_num=Integer.parseInt(doc_size);
        String[] paths= new String[doc_size_num];
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocnames();
        int i=0;
        while (data.moveToNext())
        {
            paths[i]=data.getString(0);
            i++;
        }
        isValid2=true;
        setContentView(R.layout.activity_new_application_screen);
        document_status="2";
        uploaded_file_name_close=findViewById(R.id.new_file_attach_close);
        uploaded_file_name_close.setVisibility(View.GONE);
        uploaded_file_name_text=findViewById(R.id.new_app_show_name);
        uploaded_file_name_text.setVisibility(View.GONE);
        upload_file_button_image=findViewById(R.id.new_file_attach);
        radioGroup=findViewById(R.id.new_app_status_rg);
        radioGroup.check(R.id.new_app_status_ongoing);
        submit=findViewById(R.id.submit);
        applicantid=findViewById(R.id.applicantid);
        duedate=findViewById(R.id.new_duedate);
        duedateimg=findViewById(R.id.new_deudateimg);
        newApplication_backButton = findViewById(R.id.newApplication_backButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(newApplicationScreen.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //String text = spinner.getSelectedItem().toString();
        //Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                doc_name_selected = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(getApplicationContext(),doc_name_selected,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        duedateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar cal=Calendar.getInstance();
                mDate=cal.get(Calendar.DATE);
                mMonth=cal.get(Calendar.MONTH);
                mYear=cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(newApplicationScreen.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        duedate.setText(dayOfMonth+"-"+month+"-"+year);
                    }
                },mYear,mMonth,mDate);
                        datePickerDialog.show();
            }
        });
        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                final Calendar cal=Calendar.getInstance();
                mDate=cal.get(Calendar.DATE);
                mMonth=cal.get(Calendar.MONTH);
                mYear=cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(newApplicationScreen.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        duedate.setText(dayOfMonth+"-"+month+"-"+year);
                    }
                },mYear,mMonth,mDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.new_app_status_completed:
                        document_status="1";
                        break;
                    case R.id.new_app_status_ongoing:
                        document_status="2";
                        break;
                }
            }
        });
        uploaded_file_name_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaded_file_name_close.setVisibility(View.GONE);
                uploaded_file_name_text.setVisibility(View.GONE);
                isFileSelected=false;
                //Toast.makeText(getApplicationContext(), "Closed Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        upload_file_button_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Upload File CLicked",Toast.LENGTH_SHORT).show();
                selectPDF();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid2=true;
                checkCredentials();
                if(isValid2) {
                    isValid2=true;
                    if(isFileSelected)
                    {
                        applicant_id = Integer.parseInt(applicantid.getText().toString());
                        //Toast t = Toast.makeText(getApplicationContext(), "arshad", Toast.LENGTH_SHORT);
                        //Toast t = Toast.makeText(getApplicationContext(), duedate.getText().toString(), Toast.LENGTH_SHORT);
                        //t.show();
                        RequestQueue requestQueue;
                        JsonObjectRequest request;
                        try {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/insert.php";
                            JSONObject jsonBody = new JSONObject();
                            try {
                                String user_id_from_table="0";
                                MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                Cursor data= helper.getuserID();
                                while (data.moveToNext())
                                {
                                    user_id_from_table=data.getString(0);
                                }
                                data.close();
                                helper.close();
                                jsonBody.put("doc_name",doc_name_selected);
                                jsonBody.put("emp_id", user_id_from_table);
                                jsonBody.put("app_id",applicantid.getText().toString());
                                jsonBody.put("doc_due_date",duedate.getText().toString());
                                jsonBody.put("doc_attachment","1");
                                jsonBody.put("doc_status",document_status);
                                request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        JSONArray jsonArray;
                                        try {
                                            jsonArray = response.getJSONArray("doc");
                                            //Toast.makeText(getApplicationContext(),jsonArray.toString(),Toast.LENGTH_SHORT).show();
                                            JSONObject received_data_head=jsonArray.getJSONObject(0);
                                            if(received_data_head.getString("reqcode").equalsIgnoreCase("1"))
                                            {
                                                Toast.makeText(getApplicationContext(),"Applicant ID NOT Found",Toast.LENGTH_SHORT).show();
                                            }
                                            else if(received_data_head.getString("reqcode").equalsIgnoreCase("2"))
                                            {
                                                MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                                helper.dropdocInsertedTable();
                                                helper.createDocInsertedTable();
                                                JSONObject docdetail=jsonArray.getJSONObject(1);
                                                String doc_id_ser=docdetail.getString("doc_id");
                                                String doc_name_ser=docdetail.getString("doc_name");
                                                String doc_start_date_ser=docdetail.getString("doc_start_date");
                                                String doc_end_date_ser=docdetail.getString("doc_due_date");
                                                String doc_attach_ser=docdetail.getString("doc_attachment");
                                                String doc_staus_ser = docdetail.getString("doc_status");
                                                if(docdetail.getString("doc_status").equalsIgnoreCase("1"))
                                                {
                                                    doc_staus_ser="Completed";
                                                }
                                                else if(docdetail.getString("doc_status").equalsIgnoreCase("2"))
                                                {
                                                    doc_staus_ser="On Going";
                                                }
                                                String doc_emp_id_ser = docdetail.getString("emp_id");
                                                String doc_app_id_ser = docdetail.getString("app_id");
                                                String doc_emp_name_ser = docdetail.getString("emp_name");
                                                String doc_dept_name_ser = docdetail.getString("dept_name");
                                                String doc_app_name_ser = docdetail.getString("app_name");

                                                helper.insertDocInsertedDetails(doc_id_ser,doc_name_ser,doc_start_date_ser,
                                                        doc_end_date_ser,doc_attach_ser,doc_staus_ser,doc_emp_id_ser,doc_app_id_ser,doc_emp_name_ser,doc_dept_name_ser,doc_app_name_ser);
                                                helper.close();
                                                if(document_status.equalsIgnoreCase("1"))
                                                {
                                                    startActivity(new Intent(newApplicationScreen.this, InsertedDocumentComplete.class));
                                                }
                                                else {
                                                    startActivity(new Intent(newApplicationScreen.this, InsetedDocument.class));
                                                    //Toast.makeText(getApplicationContext(),docdetail.toString(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else if(received_data_head.getString("reqcode").equalsIgnoreCase("3"))
                                            {
                                                Toast.makeText(getApplicationContext(),"Record Insertion Failed",Toast.LENGTH_SHORT).show();
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
                    else
                    {
                        //Toast.makeText(getApplicationContext(),"Kindly Select PDF File",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialog=new AlertDialog.Builder(newApplicationScreen.this);
                        dialog.setTitle("Kindly Select PDF File!");
                        //dialog.setMessage();
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        AlertDialog d=dialog.create();
                        d.show();
                    }
                }
            }
        });
        newApplication_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void selectPDF() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF File Selected"),12);
    }
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uploaded_file_name_text.setVisibility(View.VISIBLE);
            String fileName=data.getDataString().substring(data.getDataString().lastIndexOf("/")+1);
            String fileName2=data.getData().toString();
            File myFile = new File(fileName2);
            String pathStr = myFile.getAbsolutePath();
            String displayName = null;
            if (fileName2.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor =newApplicationScreen.this.getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (fileName2.startsWith("file://")) {
                displayName = myFile.getName();
            }
            uploaded_file_name_text.setText(displayName);
            uploaded_file_name_close.setVisibility(View.VISIBLE);
            isFileSelected=true;
            //Toast.makeText(getApplicationContext(),displayName,Toast.LENGTH_SHORT).show();
        }
    }
    void checkCredentials() {
        if (isEmpty(applicantid)) {
            applicantid.setError("Please Enter Applicant ID");
            isValid2 = false;
        }
        else if(isEmpty(applicantid))
        {
            applicantid.setError("Please Enter Due Date");
            isValid2=false;
        }
        else if(isEmpty(duedate))
        {
            duedate.setError("Please Set Due Date");
            isValid2=false;
        }
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}