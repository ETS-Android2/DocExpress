package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    CardView Home_NewApplication, Home_TrackApplication, Home_OngoingApplication,
            Home_ongoingStatus, Home_closedApplication;
    AppCompatButton Home_backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Home_NewApplication = findViewById(R.id.Home_NewApplication);
        Home_TrackApplication = findViewById(R.id.Home_TrackApplication);
        Home_OngoingApplication = findViewById(R.id.Home_OngoingApplication);
        Home_ongoingStatus = findViewById(R.id.Home_OngoingStatus);
        Home_closedApplication = findViewById(R.id.Home_closedApplication);
        Home_backButton = findViewById(R.id.Home_backButton);

        Home_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
                startActivity(new Intent(Home.this, ProgressScreen.class));
            }
        });
    }
}