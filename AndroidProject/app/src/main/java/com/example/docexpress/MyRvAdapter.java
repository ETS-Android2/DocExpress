package com.example.docexpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {


    List<tracked_document> track_doc_list_rv;
    Context c;

    public MyRvAdapter(List<tracked_document> track_doc_list_rv, Context c) {

        this.c=c;
        this.track_doc_list_rv=track_doc_list_rv;
    }

    @NonNull
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, int position) {
        holder.step.setText(track_doc_list_rv.get(position).getDoc_step_no());
        holder.rec_date.setText(track_doc_list_rv.get(position).getDoc_rec_date());
        holder.emp_id.setText(track_doc_list_rv.get(position).getDoc_emp_id());
        holder.comments.setText(track_doc_list_rv.get(position).getDoc_comments());
    }

    @Override
    public int getItemCount() {
        return track_doc_list_rv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView step,rec_date,emp_id,comments;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            step=itemView.findViewById(R.id.step);
            rec_date=itemView.findViewById(R.id.rec_date);
            emp_id=itemView.findViewById(R.id.emp_id);
            comments=itemView.findViewById(R.id.comments);
        }
    }
}
