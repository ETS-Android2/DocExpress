package com.example.docexpress;

import android.content.Context;
import android.content.Intent;
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

public class MyRvAdapterOngoing extends RecyclerView.Adapter<MyRvAdapterOngoing.MyViewHolderOngoing> {
    List<Ongoing_doc_class> ongoing_doc;
    Context c;
    public MyRvAdapterOngoing(List<Ongoing_doc_class> ongoing_doc, Context c) {
        this.ongoing_doc=ongoing_doc;
        this.c=c;
    }

    @NonNull
    @Override
    public MyRvAdapterOngoing.MyViewHolderOngoing onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.row_ongoing,parent,false);
        return new MyViewHolderOngoing(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapterOngoing.MyViewHolderOngoing holder, int position) {
        holder.Doc_ID.setText(ongoing_doc.get(position).getTracking_id());
        holder.Doc_Name.setText(ongoing_doc.get(position).getDoc_name());
        holder.Start_Date.setText(ongoing_doc.get(position).getStart_date());
        holder.Due_Date.setText(ongoing_doc.get(position).getEnd_date());
        holder.Status.setText(ongoing_doc.get(position).getStatus());
        holder.Applicant_ID.setText(ongoing_doc.get(position).getApplicant_id());
        holder.Applicant_Name.setText(ongoing_doc.get(position).getApplicant_name());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,ongoing_doc.get(position).getTracking_id(),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(v.getContext(), TrackApplication.class);
                //v.getContext().startActivity(intent);

                RequestQueue requestQueue;
                JsonObjectRequest request;
                try {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                    String server_address="http://"+holder.itemView.getContext().getString(R.string.server_ip)+"/fyp/mobile/get_doc_detail.php";
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("doc_id", holder.Doc_ID.getText().toString());
                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = response.getJSONArray("doc");
                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
                                        MyDbHelper helper=new MyDbHelper(v.getContext());
                                        helper.dropdoctrackTable();
                                        helper.createDocTrackTable();
                                        for(int i=1;i<jsonArray.length();i++)
                                        {
                                            JSONObject docdetail=jsonArray.getJSONObject(i);
                                            String step=docdetail.getString("step");
                                            String rec_date=docdetail.getString("rec_date");
                                            String emp_id=docdetail.getString("emp_id");
                                            String comments=docdetail.getString("comments");
                                            helper.insertDocTrackDetails(step,rec_date,emp_id,comments);
                                        }
                                        helper.close();
                                        Intent intent = new Intent(v.getContext(), ReviewApplicationScreen.class);
                                        intent.putExtra("Doc_ID",received_data_head.getString("doc_id"));
                                        intent.putExtra("Doc_Name",received_data_head.getString("doc_name"));
                                        intent.putExtra("Start_Date",received_data_head.getString("doc_start_date") );
                                        intent.putExtra("Due_Date", received_data_head.getString("doc_due_date"));
                                        intent.putExtra("Attachment", received_data_head.getString("doc_attachment"));
                                        intent.putExtra("Status", received_data_head.getString("doc_status"));
                                        intent.putExtra("Emp_Name", received_data_head.getString("emp_name"));
                                        intent.putExtra("Department", received_data_head.getString("dept_name"));
                                        intent.putExtra("Applicant_ID", received_data_head.getString("app_id"));
                                        intent.putExtra("Applicant_Name", received_data_head.getString("app_name"));
                                        v.getContext().startActivity(intent);
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
        return ongoing_doc.size();
    }

    public class MyViewHolderOngoing extends RecyclerView.ViewHolder {
        TextView Doc_ID, Doc_Name, Start_Date, Due_Date, Attachment, Status, Applicant_ID, Applicant_Name;
        LinearLayout ll;

        public MyViewHolderOngoing(@NonNull View itemView) {
            super(itemView);
            Doc_ID = itemView.findViewById(R.id.ongoing_TrackingID);
            Doc_Name = itemView.findViewById(R.id.ongoing_DocName);
            Start_Date = itemView.findViewById(R.id.ongoing_StartDate);
            Due_Date = itemView.findViewById(R.id.ongoing_DueDate);
            //Attachment = itemView.findViewById(R.id.ongoing_Attachment);
            Status = itemView.findViewById(R.id.ongoing_Status);
            Applicant_ID = itemView.findViewById(R.id.ongoing_ApplicantID);
            Applicant_Name = itemView.findViewById(R.id.ongoing_ApplicantName);
            ll = itemView.findViewById(R.id.ongoing_ll1);
        }
    }
}
