package com.example.docexpress;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class MyRvAdapterReceivedDocument extends RecyclerView.Adapter<MyRvAdapterReceivedDocument.MyViewHolderReceived> {
    List<ReceivedDocumentClass> received_doc;
    Context c;
    public MyRvAdapterReceivedDocument(List<ReceivedDocumentClass> received_doc, Context c) {
        this.received_doc=received_doc;
        this.c=c;
    }

    @NonNull
    @Override
    public MyRvAdapterReceivedDocument.MyViewHolderReceived onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.row_received_document_screen,parent,false);
        return new MyViewHolderReceived(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapterReceivedDocument.MyViewHolderReceived holder, int position) {
        holder.Doc_ID.setText(received_doc.get(position).getTracking_id());
        holder.Doc_Name.setText(received_doc.get(position).getDoc_name());
        holder.Start_Date.setText(received_doc.get(position).getStart_date());
        holder.Due_Date.setText(received_doc.get(position).getEnd_date());
        holder.Status.setText(received_doc.get(position).getStatus());
        holder.Applicant_ID.setText(received_doc.get(position).getApplicant_id());
        holder.Applicant_Name.setText(received_doc.get(position).getApplicant_name());
        holder.starting_emp_name.setText(received_doc.get(position).getStarting_emp_name());
        holder.starting_emp_rank.setText(received_doc.get(position).getStarting_emp_rank());
        holder.starting_emp_dept.setText(received_doc.get(position).getStarting_emp_dept());
        holder.sender_emp_name.setText(received_doc.get(position).getSender_emp_name());
        holder.sender_emp_rank.setText(received_doc.get(position).getSender_emp_rank());
        holder.sender_emp_dept.setText(received_doc.get(position).getSender_emp_dept());
        holder.sender_emp_note.setText(received_doc.get(position).getSender_emp_note());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(c,received_doc.get(position).getTracking_id(),Toast.LENGTH_SHORT).show();
                String doc_id_cur=received_doc.get(position).getTracking_id();
                String doc_name_selected=received_doc.get(position).getDoc_name();

                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    String server_address="http://"+holder.itemView.getContext().getString(R.string.server_ip)+"/fyp/mobile/get_doc_route.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        //String user_id_from_table="0";
                        //MyDbHelper helper=new MyDbHelper(getApplicationContext());
                        //Cursor data= helper.getuserID();
                        //while (data.moveToNext())
                        //{
                        //    user_id_from_table=data.getString(0);
                        //}
                        //data.close();
                        //helper.close();
                        String user_id_from_table="0";
                        MyDbHelper helperuser=new MyDbHelper(v.getContext());
                        Cursor datauser= helperuser.getuserID();
                        while (datauser.moveToNext())
                        {
                            user_id_from_table=datauser.getString(0);
                        }
                        datauser.close();
                        helperuser.close();
                        jsonBody.put("doc_name", doc_name_selected);
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
                                        Toast.makeText(v.getContext(),"No Default Route Found",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        MyDbHelper helper=new MyDbHelper(v.getContext());
                                        helper.dropdocrouteTable();
                                        helper.createDocRouteTable();
                                        for(int i=1;i<jsonArray.length();i++)
                                        {
                                            JSONObject docdetail=jsonArray.getJSONObject(i);
                                            String doc_id_ser=docdetail.getString("doc_step_no");
                                            String doc_name_ser=docdetail.getString("doc_name");
                                            String doc_start_date_ser=docdetail.getString("emp_id");
                                            String doc_end_date_ser=docdetail.getString("emp_name");
                                            String doc_app_id_ser=docdetail.getString("emp_rank");
                                            String doc_app_name_ser=docdetail.getString("dept_name");
                                            helper.insertDocRouteTable(doc_id_ser,doc_name_ser,doc_start_date_ser,doc_end_date_ser,doc_app_id_ser,doc_app_name_ser);
                                        }
                                        helper.close();
                                        //startActivity(new Intent(newApplicationScreen.this, InsetedDocument.class));
                                        Intent intent = new Intent(v.getContext(), ForwardDocumentScreen.class);
                                        intent.putExtra("Doc_ID",doc_id_cur);
                                        v.getContext().startActivity(intent);
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
    }
    @Override
    public int getItemCount() {
        return received_doc.size();
    }

    public class MyViewHolderReceived extends RecyclerView.ViewHolder {
        TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Applicant_ID, Applicant_Name,
                starting_emp_name,starting_emp_rank,starting_emp_dept,sender_emp_name,sender_emp_rank,sender_emp_dept,sender_emp_note;
        LinearLayout ll;

        public MyViewHolderReceived(@NonNull View itemView) {
            super(itemView);
            Doc_ID = itemView.findViewById(R.id.received_TrackingID);
            Doc_Name = itemView.findViewById(R.id.received_DocName);
            Start_Date = itemView.findViewById(R.id.received_StartDate);
            Due_Date = itemView.findViewById(R.id.received_DueDate);
            //Attachment = itemView.findViewById(R.id.ongoing_Attachment);
            Status = itemView.findViewById(R.id.received_Status);
            Applicant_ID = itemView.findViewById(R.id.received_ApplicantID);
            Applicant_Name = itemView.findViewById(R.id.received_ApplicantName);
            starting_emp_name= itemView.findViewById(R.id.received_starting_emp_name);
            starting_emp_rank=itemView.findViewById(R.id.received_starting_emp_rank);
            starting_emp_dept=itemView.findViewById(R.id.received_starting_emp_dept);
            sender_emp_name=itemView.findViewById(R.id.received_sender_emp_name);
            sender_emp_rank=itemView.findViewById(R.id.received_sender_emp_rank);
            sender_emp_dept=itemView.findViewById(R.id.received_sender_emp_dept);
            sender_emp_note=itemView.findViewById(R.id.received_sender_emp_note);
            ll = itemView.findViewById(R.id.received_ll1);
        }
    }
}