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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.main_toolbar);
        //toolbar.setNavigationIcon(R.drawable.outline_menu_24);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle= new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer

        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        MenuItem menuItem=findViewById(R.id.nav_menu);
//        NavigationView navigationView=findViewById(R.id.nav_logout);
//        navigationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyDbHelper helper=new MyDbHelper(getApplicationContext());
//                helper.dropUserIdTable();
//                helper.close();
//                Intent intent=new Intent(getApplicationContext(),signIn.class);
//                startActivity(intent);
//            }
//        });
//        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                MyDbHelper helper=new MyDbHelper(getApplicationContext());
//                helper.dropUserIdTable();
//                helper.close();
//                Intent intent=new Intent(getApplicationContext(),signIn.class);
//                startActivity(intent);
//                return false;
//                            }
//            }
//        );
//        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                Toast.makeText(getApplicationContext(),"Menu Expanded",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        });

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
                startActivity(new Intent(Home.this, closedApplication.class));
            }
        });

        Home_NewApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, newApplicationScreen.class));
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
                startActivity(new Intent(Home.this, ongoing_Application_Screen.class));
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
    public static String nullToEmpty(CharSequence str) {
        return str == null ? "" : str.toString();
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