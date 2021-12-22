package com.example.docexpress;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class newApplicationScreen extends AppCompatActivity {


    Button submit;
    EditText applicantid;
    EditText duedate;
    int applicant_id;
    String due_date;
    boolean isValid2=true;
    AppCompatButton newApplication_backButton;
    private Spinner spinner;
    private static final String[] paths = {"Select Application Name", "Course Replacement Form", "Clearance Form",
            "Course Withdraw Form", "Grade Change Form", "Semester Freeze Form", "Event Request Form"};
    private Spinner spinner2;
    private static final String[] paths2 = {"Select Application Status", "Completed", "On Going"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isValid2=true;
        setContentView(R.layout.activity_new_application_screen);
        submit=findViewById(R.id.submit);
        applicantid=findViewById(R.id.applicantid);
        duedate=findViewById(R.id.duedate);
        newApplication_backButton = findViewById(R.id.newApplication_backButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(newApplicationScreen.this,
                android.R.layout.simple_spinner_item, paths);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(newApplicationScreen.this,
                android.R.layout.simple_spinner_item, paths2);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner2.setAdapter(adapter2);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    switch (position) {
                        case 0:
                            // Whatever you want to happen when the first item gets selected
                            break;
                        case 1:
                            // Whatever you want to happen when the second item gets selected
                            break;
                        case 2:
                            // Whatever you want to happen when the thrid item gets selected
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isValid2=true;
                    checkCredentials();
                    if(isValid2) {
                        isValid2=true;
                        applicant_id = Integer.parseInt(applicantid.getText().toString());
                        //startActivity();
                        Toast t = Toast.makeText(getApplicationContext(), "arshad", Toast.LENGTH_SHORT);
                        t.show();
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
    void checkCredentials() {
        if (isEmpty(applicantid)) {
            applicantid.setError("Please Enter Applicant ID");
            isValid2 = false;
        }
        else if(isEmpty(duedate))
        {
            duedate.setError("Please Enter Due Date");
            isValid2=false;
        }
        /*if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }*/
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}