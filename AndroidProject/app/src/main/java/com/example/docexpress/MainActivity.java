package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.enableDefaults();
        //getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyDbHelper helper=new MyDbHelper(getApplicationContext());
                Cursor data=helper.getuserCount();
                //Toast.makeText(getApplicationContext(),data.getCount(),Toast.LENGTH_SHORT);
                if(data.getCount()>0)
                {
                    MyDbHelper helper2=new MyDbHelper(getApplicationContext());
                    Cursor data2=helper2.getuserRole();
                    //Toast.makeText(getApplicationContext(),data.getCount(),Toast.LENGTH_SHORT);
                    while(data2.moveToNext()) {
                        String user_role = data2.getString(0);
                        //Toast.makeText(getApplicationContext(),user_role,Toast.LENGTH_SHORT);
                        data2.close();
                        helper2.close();
                        if (user_role.equalsIgnoreCase("applicant")) {
                            Intent intent = new Intent(getApplicationContext(), StudentHome.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                        }
                    }
                }
                else {
                    Intent intent = new Intent(MainActivity.this, signIn.class);
                    startActivity(intent);
                }
                data.close();
                helper.close();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}