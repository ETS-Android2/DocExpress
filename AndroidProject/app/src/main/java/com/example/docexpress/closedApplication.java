package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class closedApplication extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Ongoing_doc_class> ongoing_doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_application);
        recyclerView=findViewById(R.id.ongoing_rv);
        ongoing_doc=new ArrayList<>();
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocOngoing();
        while (data.moveToNext())
        {
            ongoing_doc.add(new Ongoing_doc_class(data.getString(0),data.getString(1),data.getString(2),data.getString(3),"Completed",data.getString(4),data.getString(5)));
        }
        MyRvAdapterOngoing adapterOngoing=new MyRvAdapterOngoing(ongoing_doc,this);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapterOngoing);
    }
}