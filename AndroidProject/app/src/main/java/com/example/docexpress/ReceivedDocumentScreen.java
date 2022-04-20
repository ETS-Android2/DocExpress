package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ReceivedDocumentScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ReceivedDocumentClass> received_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_document_screen);
        recyclerView=findViewById(R.id.received_rv);
        received_doc=new ArrayList<>();
//        received_doc.add(new ReceivedDocumentClass("800002","Clearance Form","26-JUN-21","26-JUN-21","On Going","900001","Talat Naeem","Amir Rahman","Academic Officer","CS Academic Office","Abdul Hameed","Deputy Manager Academics","Central Academic Office","None"));
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocReceived();
        while (data.moveToNext())
        {
            received_doc.add(new ReceivedDocumentClass(data.getString(0),data.getString(1),
                    data.getString(2),data.getString(3),data.getString(5),
                    data.getString(6),data.getString(7),data.getString(9),
                    data.getString(10),data.getString(12),data.getString(14),
                    data.getString(15),data.getString(17),data.getString(18)));
        }
        MyRvAdapterReceivedDocument adapterReceived=new MyRvAdapterReceivedDocument(received_doc,this);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapterReceived);
    }
}