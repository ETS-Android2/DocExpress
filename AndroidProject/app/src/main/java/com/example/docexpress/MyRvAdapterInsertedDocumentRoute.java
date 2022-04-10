package com.example.docexpress;

import android.annotation.SuppressLint;
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

public class MyRvAdapterInsertedDocumentRoute extends RecyclerView.Adapter<MyRvAdapterInsertedDocumentRoute.MyViewHolderInsertedDocumentRoute> {
    List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails;
    Context c;
    public MyRvAdapterInsertedDocumentRoute(List<InsertedDocumentRouteDetails> insertedDocumentRouteDetails, Context c) {
        this.insertedDocumentRouteDetails=insertedDocumentRouteDetails;
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
                Toast.makeText(c,insertedDocumentRouteDetails.get(position).getEmployee_name(),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(v.getContext(), TrackApplication.class);
                //v.getContext().startActivity(intent);

//                RequestQueue requestQueue;
//                JsonObjectRequest request;
//                try {
//                    requestQueue = Volley.newRequestQueue(v.getContext());
//                    String server_address="http://"+holder.itemView.getContext().getString(R.string.server_ip)+"/fyp/mobile/get_doc_detail.php";
//                    JSONObject jsonBody = new JSONObject();
//                    try {
//                        jsonBody.put("doc_id", holder.Doc_ID.getText().toString());
//                        request= new JsonObjectRequest(Request.Method.POST, server_address, jsonBody, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                JSONArray jsonArray;
//                                try {
//                                    jsonArray = response.getJSONArray("doc");
//                                    JSONObject received_data_head=jsonArray.getJSONObject(0);
//                                    MyDbHelper helper=new MyDbHelper(v.getContext());
//                                    helper.dropdoctrackTable();
//                                    helper.createDocTrackTable();
//                                    for(int i=1;i<jsonArray.length();i++)
//                                    {
//                                        JSONObject docdetail=jsonArray.getJSONObject(i);
//                                        String step=docdetail.getString("step");
//                                        String rec_date=docdetail.getString("rec_date");
//                                        String emp_id=docdetail.getString("emp_id");
//                                        String comments=docdetail.getString("comments");
//                                        helper.insertDocTrackDetails(step,rec_date,emp_id,comments);
//                                    }
//                                    helper.close();
//                                    Intent intent = new Intent(v.getContext(), ReviewApplicationScreen.class);
//                                    intent.putExtra("Doc_ID",received_data_head.getString("doc_id"));
//                                    intent.putExtra("Doc_Name",received_data_head.getString("doc_name"));
//                                    intent.putExtra("Start_Date",received_data_head.getString("doc_start_date") );
//                                    intent.putExtra("Due_Date", received_data_head.getString("doc_due_date"));
//                                    intent.putExtra("Attachment", received_data_head.getString("doc_attachment"));
//                                    intent.putExtra("Status", received_data_head.getString("doc_status"));
//                                    intent.putExtra("Emp_Name", received_data_head.getString("emp_name"));
//                                    intent.putExtra("Department", received_data_head.getString("dept_name"));
//                                    intent.putExtra("Applicant_ID", received_data_head.getString("app_id"));
//                                    intent.putExtra("Applicant_Name", received_data_head.getString("app_name"));
//                                    v.getContext().startActivity(intent);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    Toast t2 = Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT);
//                                    t2.show();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                error.printStackTrace();
//                                Toast.makeText(v.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        requestQueue.add(request);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                    Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
//                }

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