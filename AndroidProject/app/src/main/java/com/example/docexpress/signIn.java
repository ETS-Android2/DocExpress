package com.example.docexpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class signIn extends AppCompatActivity {
    AppCompatButton signIn_Button;
    EditText username;
    EditText password;
//    Button register;
//    Button login;
    TextView signUp;
    String server_response;
    String signin_server_response2;
    String signin_server_response_user_id;
    int signin_server_response=0;
    JSONObject jo;
    String sSDR;
    boolean isValid = true;
    //private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //getSupportActionBar().hide();
        signIn_Button = findViewById(R.id.signIn_Login);
        username = findViewById(R.id.signIn_Email);
        password = findViewById(R.id.signIn_Password);
        signUp = findViewById(R.id.signUp);
        //spinner=(ProgressBar)findViewById(R.id.progressBar);
        //spinner.setVisibility(View.GONE);
        setupListeners();

//        signIn_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(signIn.this, Home.class));
//
//            }
//        });
    }
    private void setupListeners() {
        signIn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner.setVisibility(View.VISIBLE);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        spinner.setVisibility(View.VISIBLE);
//                    }
//                });
//                Runnable runnable = new Runnable(){
//                    public void run() {
//                        //some code here
//                        ProgressBar spinner2;
//                        spinner2=(ProgressBar)findViewById(R.id.progressBar);
//                        spinner2.setVisibility(View.GONE);
//                        spinner2.setVisibility(View.VISIBLE);
//                    }
//                };
//                Thread thread=new Thread(runnable);
//                thread.start();
                signin_server_response=0;
                isValid = true;
                checkUsername();
                if (isValid) {
                String user_name=username.getText().toString();
                String pass_word=password.getText().toString();
                HttpParams params= new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params,5000);
                //HttpConnectionParams.setSoTimeout(params,5000);
                HttpClient httpclient = new DefaultHttpClient(params);
                //HttpPost httppost=new HttpPost( "http://192.168.18.83/fyp/loginget.php?email="+user_name+"pass="+pass_word);
                //HttpPost httppost=new HttpPost( "http://192.168.1.9/fyp/mobile/login.php");
                    String server_address="http://"+getString(R.string.server_ip)+"/fyp/mobile/login.php";
                    //Toast.makeText(getApplicationContext(),server_address,Toast.LENGTH_SHORT).show();
                HttpPost httppost=new HttpPost( server_address);
                BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("email", user_name);
                BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("pass", pass_word);
                //List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(usernameBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePAir);
                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
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
                            sSDR=jo.getString("reqcode");
                            signin_server_response=Integer.parseInt(sSDR);
                            sSDR=jo.getString("reqmsg");
                            signin_server_response2=sSDR;
                            sSDR=jo.getString("id");
                            signin_server_response_user_id=sSDR;
                            //sSDR=Integer.toString(signin_server_response);
                        }
                        catch (Exception e)
                        {

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
                }
               // startActivity(new Intent(signIn.this, Home.class));
                    if (signin_server_response==1) {
                        //everything checked we open new activity
                        Toast t = Toast.makeText(getApplicationContext(),signin_server_response2,Toast.LENGTH_SHORT);
                        t.show();
                        Toast.makeText(getApplicationContext(),signin_server_response_user_id,Toast.LENGTH_SHORT).show();
                        MyDbHelper helper=new MyDbHelper(getApplicationContext());
                        helper.insertUserID(signin_server_response_user_id);
                        helper.close();
                        Intent i = new Intent(signIn.this, Home.class);
                        startActivity(i);
                        finish();
                        //we close this activity
                    } else if(signin_server_response==0) {
                        Toast t = Toast.makeText(getApplicationContext(), "Could Not Connect To Server", Toast.LENGTH_SHORT);
                        t.show();
                        //spinner.setVisibility(View.GONE);
                    }
                    else if(signin_server_response==2) {
                        Toast t = Toast.makeText(getApplicationContext(),signin_server_response2, Toast.LENGTH_SHORT);
                        t.show();
                    }
                    else if(signin_server_response==3) {
                        Toast t = Toast.makeText(getApplicationContext(),signin_server_response2, Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void checkUsername() {
        if (isEmpty(username)) {
            username.setError("You must enter username to login!");
            isValid = false;
        } else {
            if (!isEmail(username)) {
                username.setError("Enter valid email!");
                isValid = false;
            }
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        //check email and password
        //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
        //For example simple check is cool
//        if (isValid) {
//            String usernameValue = username.getText().toString();
//            String passwordValue = password.getText().toString();
//            //if (usernameValue.equals("test@test.com") && passwordValue.equals("password1234")) {
//            if (signin_server_response==1) {
//                //everything checked we open new activity
//                Toast t = Toast.makeText(getApplicationContext(),"SuccessFully Logged In",Toast.LENGTH_SHORT);
//                t.show();
//                Intent i = new Intent(signIn.this, Home.class);
//                startActivity(i);
//                //we close this activity
//                this.finish();
//            } else if(signin_server_response==0) {
//                Toast t = Toast.makeText(this, "Could Not Connect To Server", Toast.LENGTH_SHORT);
//                t.show();
//            }
//        }
        //if(isValid) {
        //    this.finish();
        //}
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


}