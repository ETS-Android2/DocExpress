package com.example.docexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.opencensus.internal.StringUtils;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout Home_NewApplication, Home_TrackApplication, Home_OngoingApplication,
            Home_ongoingStatus, Home_closedApplication;
    AppCompatButton Home_backButton;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView user_name_nav,user_job_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.main_toolbar);
        //toolbar.setNavigationIcon(R.drawable.outline_menu_24);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        user_name_nav=headerView.findViewById(R.id.user_name_text);
        user_job_nav=headerView.findViewById(R.id.user_job_text);
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getuserCount();
        while (data.moveToNext())
        {
            user_name_nav.setText(data.getString(1));
            user_job_nav.setText(data.getString(2));
        }
        data.close();
        helper.close();
        final ActionBarDrawerToggle[] actionBarDrawerToggle = {new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer

        )};
        drawerLayout.addDrawerListener(actionBarDrawerToggle[0]);
        actionBarDrawerToggle[0].syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Home_NewApplication = findViewById(R.id.start_doc_button);
        Home_TrackApplication = findViewById(R.id.track_doc_button);
        Home_OngoingApplication = findViewById(R.id.sent_doc_button);
        Home_ongoingStatus = findViewById(R.id.report_sum_button);
        Home_closedApplication = findViewById(R.id.closed_doc_button);
        //Home_backButton = findViewById(R.id.);

//        Home_backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        Home_closedApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        startActivity(new Intent(Home.this, closedApplication.class));
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

        Home_NewApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                JsonObjectRequest request;
                final String[] doc_size = {"0"};
                try {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_names.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("id", "1");
                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = response.getJSONArray("doc");
                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
                                    if(received_data_head.getString("reqcode").equalsIgnoreCase("1"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Could Not Fetch Douments From Server",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        doc_size[0] =received_data_head.getString("doc_numbers");
                                        MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                        helper.dropdocnamesTable();
                                        helper.createDocNamesTable();
                                        for(int i=1;i<jsonArray.length();i++)
                                        {
                                            JSONObject docdetail=jsonArray.getJSONObject(i);
                                            String doc_name_ser=docdetail.getString("doc_name");
                                            helper.insertDocNamesDetails(doc_name_ser);
                                        }
                                        helper.close();
                                        Intent intent=new Intent(Home.this, newApplicationScreen.class);
                                        intent.putExtra("doc_numbers",doc_size[0]);
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
                //startActivity(new Intent(Home.this, newApplicationScreen.class));
            }
        });

        Home_TrackApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TrackApplication.class));
            }
        });

        Home_OngoingApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        startActivity(new Intent(Home.this, ongoing_Application_Screen.class));
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

        Home_ongoingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/get_doc_summary.php";
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
                                        if(received_data_head.getString("doc_total").equalsIgnoreCase("0"))
                                        {
                                            Toast.makeText(getApplicationContext(),"No Applications Found",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            MyDbHelper helper=new MyDbHelper(getApplicationContext());
                                            helper.dropdocSummaryTable();
                                            helper.createDocSummaryTable();
                                            for(int i=0;i<jsonArray.length();i++)
                                            {
                                                JSONObject docdetail=jsonArray.getJSONObject(i);
                                                String total=docdetail.getString("doc_total");
                                                String completed=docdetail.getString("doc_completed");
                                                if(completed.replace("null","").isEmpty())
                                                {
                                                    completed="0";
                                                }
                                                String ongoing=docdetail.getString("doc_on_going");
                                                if(completed.replace("null","").isEmpty())
                                                {
                                                    ongoing="0";
                                                }
                                                String returned=docdetail.getString("doc_returned");
                                                if(returned.replace("null","").isEmpty())
                                                {
                                                    returned="0";
                                                }
                                                String rejected=docdetail.getString("doc_rejected");
                                                if(rejected.replace("null","").isEmpty())
                                                {
                                                    rejected="0";
                                                }
                                                helper.insertDocSummaryTable(total,completed,ongoing,returned,rejected);
                                            }
                                            helper.close();
                                            startActivity(new Intent(Home.this, ProgressScreen.class));
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_logout)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            MyDbHelper helper=new MyDbHelper(getApplicationContext());
            helper.deleteUserId();
            helper.close();
            Intent intent=new Intent(getApplicationContext(),signIn.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onBackPressed()
    {
     //   if(backButtonCount >= 1)
     //   {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
     //   }
     //   else
     //   {
     //       Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
     //       backButtonCount++;
     //   }
    }
}