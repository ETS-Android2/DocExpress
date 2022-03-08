package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.checkerframework.common.value.qual.IntVal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProgressScreen extends AppCompatActivity {
    TextView textView;
    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_screen);
        textView=findViewById(R.id.all_applications_count);
        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDescription(null);
        pieChart.setEntryLabelTextSize(16);
        Legend l = pieChart.getLegend();
        l.setTextSize(15f);
        l.setForm(Legend.LegendForm.CIRCLE);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String s=e.getData().toString();
                if(s.equalsIgnoreCase("1"))
                {
                    Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
                    RequestQueue requestQueue;
                    JsonObjectRequest request;
                    try {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_completed.php";
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
                                            Toast.makeText(getApplicationContext(),"No Applications Found",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                            helper.dropdocOngoingTable();
                                            helper.createDocOngoingTable();
                                            for(int i=1;i<jsonArray.length();i++)
                                            {
                                                JSONObject docdetail=jsonArray.getJSONObject(i);
                                                String doc_id_ser=docdetail.getString("doc_id");
                                                String doc_name_ser=docdetail.getString("doc_name");
                                                String doc_start_date_ser=docdetail.getString("start_date");
                                                String doc_end_date_ser=docdetail.getString("end_date");
                                                String doc_app_id_ser=docdetail.getString("app_id");
                                                String doc_app_name_ser=docdetail.getString("app_name");
                                                helper.insertDocOngoingTable(doc_id_ser,doc_name_ser,doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                            }
                                            helper.close();
                                            startActivity(new Intent(getApplicationContext(), closedApplication.class));
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
                        } catch (JSONException ee) {
                            ee.printStackTrace();
                            Toast.makeText(getApplicationContext(),ee.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ee)
                    {
                        ee.printStackTrace();
                        Toast.makeText(getApplicationContext(),ee.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                else if (s.equalsIgnoreCase("2")) {
                    RequestQueue requestQueue;
                    JsonObjectRequest request;
                    try {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_ongoing.php";
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
                                            Toast.makeText(getApplicationContext(),"No Applications Found",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                            helper.dropdocOngoingTable();
                                            helper.createDocOngoingTable();
                                            for(int i=1;i<jsonArray.length();i++)
                                            {
                                                JSONObject docdetail=jsonArray.getJSONObject(i);
                                                String doc_id_ser=docdetail.getString("doc_id");
                                                String doc_name_ser=docdetail.getString("doc_name");
                                                String doc_start_date_ser=docdetail.getString("start_date");
                                                String doc_end_date_ser=docdetail.getString("end_date");
                                                String doc_app_id_ser=docdetail.getString("app_id");
                                                String doc_app_name_ser=docdetail.getString("app_name");
                                                helper.insertDocOngoingTable(doc_id_ser,doc_name_ser,doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                            }
                                            helper.close();
                                            startActivity(new Intent(getApplicationContext(), ongoing_Application_Screen.class));
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
                        } catch (JSONException ee) {
                            ee.printStackTrace();
                            Toast.makeText(getApplicationContext(),ee.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ee)
                    {
                        ee.printStackTrace();
                        Toast.makeText(getApplicationContext(),ee.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                else if (s.equalsIgnoreCase("3"))
                {
                    Toast.makeText(getApplicationContext(),"Returned",Toast.LENGTH_SHORT).show();
                }
                else if(s.equalsIgnoreCase("4"))
                {
                    Toast.makeText(getApplicationContext(),"Rejected",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        String total="0";
        String completed="0";
        String ongoing="0";
        String returned="0";
        String rejected="0";
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocSummary();
        while (data.moveToNext())
        {
            total=data.getString(1);
            completed=data.getString(2);
            ongoing=data.getString(3);
            returned=data.getString(4);
            rejected=data.getString(5);
        }
        textView.setText(total);
        pieEntryList.add(new PieEntry(Integer.parseInt(completed), "Completed",1));
        pieEntryList.add(new PieEntry(Integer.parseInt(ongoing), "On Going",2));
        pieEntryList.add(new PieEntry(Integer.parseInt(returned), "Returned",3));
        pieEntryList.add(new PieEntry(Integer.parseInt(rejected), "Rejected",4));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, null);
        pieDataSet.setValueTextSize(15);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //String format="###,###,###";
        //pieDataSet.setValueFormatter(new LargeValueFormatter());
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        finish();
    }
}