package com.example.docexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
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

public class StudentHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout Home_NewApplication, Home_TrackApplication, Home_OngoingApplication,
            Home_ongoingStatus, Home_closedApplication, Home_receivedApplication,Home_draftApplication;
    AppCompatButton Home_backButton;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView user_name_nav, user_job_nav;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        toolbar = findViewById(R.id.main_toolbar2);
        //toolbar.setNavigationIcon(R.drawable.outline_menu_24);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        View headerView = navigationView.getHeaderView(0);
        user_name_nav = headerView.findViewById(R.id.user_name_text);
        user_job_nav = headerView.findViewById(R.id.user_job_text);
        MyDbHelper helper = new MyDbHelper(getApplicationContext());
        Cursor data = helper.getuserCount();
        while (data.moveToNext()) {
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

        Home_NewApplication = findViewById(R.id.start_doc_button2);
        Home_TrackApplication = findViewById(R.id.track_doc_button2);
        Home_OngoingApplication = findViewById(R.id.sent_doc_button2);
        Home_ongoingStatus = findViewById(R.id.report_sum_button2);
        Home_closedApplication = findViewById(R.id.closed_doc_button2);
        Home_receivedApplication = findViewById(R.id.received_doc_button2);
        Home_draftApplication = findViewById(R.id.draft_doc_button2);
        //Home_backButton = findViewById(R.id.);

//        Home_backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        Home_closedApplication.setVisibility(View.GONE);

        Home_NewApplication.setVisibility(View.GONE);
        Home_draftApplication .setVisibility(View.GONE);

        Home_TrackApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHome.this, TrackApplication.class));
            }
        });

        Home_OngoingApplication.setVisibility(View.GONE);

        Home_ongoingStatus.setVisibility(View.GONE);

        Home_receivedApplication.setVisibility(View.GONE);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            drawerLayout.closeDrawer(GravityCompat.START);
            MyDbHelper helper = new MyDbHelper(getApplicationContext());
            helper.deleteUserId();
            helper.close();
            Intent intent = new Intent(getApplicationContext(), signIn.class);
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
    public void onBackPressed() {
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