package com.example.docexpress;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyRvAdapterInsertedDocumentRoute extends RecyclerView.Adapter<MyRvAdapterInsertedDocumentRoute.MyViewHolderInsertedDocumentRoute> {
    List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails;
    String comments;
    Context c;
    public MyRvAdapterInsertedDocumentRoute(List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails,String comments, Context c) {
        this.insertedDocumentRouteDetails=insertedDocumentRouteDetails;
        this.comments=comments;
        this.c=c;
    }
    @NonNull
    @Override
    public MyRvAdapterInsertedDocumentRoute.MyViewHolderInsertedDocumentRoute onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.row_inserted_document_route_detail,parent,false);
        return new MyViewHolderInsertedDocumentRoute(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyRvAdapterInsertedDocumentRoute.MyViewHolderInsertedDocumentRoute holder, int position) {
        holder.serialNo.setText(insertedDocumentRouteDetails.get(position).getSerial_no());
        holder.emp_name.setText(insertedDocumentRouteDetails.get(position).getEmployee_name());
        holder.emp_rank.setText(insertedDocumentRouteDetails.get(position).getEmployee_rank());
        holder.dept_name.setText(insertedDocumentRouteDetails.get(position).getDepartment_name());
        holder.already_sent.setText(insertedDocumentRouteDetails.get(position).getAlready_sent());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(c,insertedDocumentRouteDetails.get(position).getEmployee_name(),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(v.getContext(), TrackApplication.class);
                //v.getContext().startActivity(intent);
                String emp_name_selected=insertedDocumentRouteDetails.get(position).getEmployee_name();
                AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Send Document to "+insertedDocumentRouteDetails.get(position).getEmployee_name()+"?");
                //dialog.setMessage();

                //Toast.makeText(c,emp_id_from_table,Toast.LENGTH_SHORT).show();

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String emp_id_from_table="0";
                        MyDbHelper helperuser=new MyDbHelper(c);
                        Cursor datauser= helperuser.getdocRouteEmpId(emp_name_selected);
                        while (datauser.moveToNext())
                        {
                            emp_id_from_table=datauser.getString(0);
                        }
                        datauser.close();
                        helperuser.close();
                        if(emp_id_from_table.equalsIgnoreCase("0"))
                        {
                            MyDbHelper helperuser2=new MyDbHelper(c);
                            Cursor datauser2= helperuser2.getdocRouteCustomizedEmpId(emp_name_selected);
                            while (datauser2.moveToNext())
                            {
                                emp_id_from_table=datauser2.getString(0);
                            }
                            datauser2.close();
                            helperuser2.close();
                        }
                        String user_id_from_table="0";
                        MyDbHelper helperuser3=new MyDbHelper(c);
                        Cursor datauser3= helperuser3.getuserID();
                        while (datauser3.moveToNext())
                        {
                            user_id_from_table=datauser3.getString(0);
                        }
                        datauser3.close();
                        helperuser3.close();
                        String doc_id="0";
                        MyDbHelper helper4=new MyDbHelper(c);
                        Cursor data4=helper4.getdocInserted();
                        while (data4.moveToNext())
                        {
                            doc_id=data4.getString(0);
                        }
                        //helper4.dropdocInsertedTable();
                        data4.close();
                        helper4.close();
                        //Toast.makeText(c,doc_id,Toast.LENGTH_SHORT).show();
                        RequestQueue requestQueue;
                        JsonObjectRequest request;
                        try {
                            requestQueue = Volley.newRequestQueue(v.getContext());
                            String server_address="http://"+holder.itemView.getContext().getString(R.string.server_ip)+"/fyp/mobile/insert_doc_status.php";
                            JSONObject jsonBody = new JSONObject();
                            try {
                                jsonBody.put("doc_id", doc_id);
                                jsonBody.put("emp_id_sender", user_id_from_table);
                                jsonBody.put("emp_id_receiver", emp_id_from_table);
                                jsonBody.put("comments", comments);
                                request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        JSONArray jsonArray;
                                        try {
                                            jsonArray = response.getJSONArray("doc");
                                            JSONObject received_data_head=jsonArray.getJSONObject(0);
                                            if(received_data_head.getString("reqcode").equalsIgnoreCase("2"))
                                            {
                                                Toast.makeText(c,"Document Sent Successfully",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(v.getContext(), Home.class);
                                                v.getContext().startActivity(intent);
                                            }
                                            else if(received_data_head.getString("reqcode").equalsIgnoreCase("3"))
                                            {
                                                Toast.makeText(c,"Document Sending Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast t2 = Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT);
                                            t2.show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(v.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                requestQueue.add(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(c,"document not sendt",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog d=dialog.create();
                d.show();

            }
        });
    }
    @Override
    public int getItemCount() {
        return insertedDocumentRouteDetails.size();
    }
    public class MyViewHolderInsertedDocumentRoute extends RecyclerView.ViewHolder {
        TextView serialNo, emp_name,emp_rank, dept_name, already_sent;
        LinearLayout ll;

        public MyViewHolderInsertedDocumentRoute(@NonNull View itemView) {
            super(itemView);
            serialNo= itemView.findViewById(R.id.SerialNo_activity);
            emp_name = itemView.findViewById(R.id.employee_name_activity);
            emp_rank = itemView.findViewById(R.id.employee_rank_activity);
            dept_name = itemView.findViewById(R.id.department_name_activity);
            already_sent = itemView.findViewById(R.id.already_sent_activity);
            ll = itemView.findViewById(R.id.inserted_document_ll1);
        }
    }
}