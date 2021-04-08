package com.example.reading_teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class StudentsAddAdapter extends RecyclerView.Adapter<StudentsAddAdapter.MyViewHolder>{
    private ArrayList<StudentAddItem>studentAddItems;
    private GroupRecyclerClick mrecyclerViewClickListener;

    public StudentsAddAdapter(ArrayList<StudentAddItem> studentAddItems, GroupRecyclerClick groupRecyclerClick){
        this.studentAddItems = studentAddItems;
        this.mrecyclerViewClickListener = groupRecyclerClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView emailTxt,nameTxt;
        GroupRecyclerClick recyclerViewClickListener;


        public MyViewHolder(final View view, GroupRecyclerClick recyclerViewClickListener){
            super(view);
            emailTxt = view.findViewById(R.id.user_email_tv);
            nameTxt = view.findViewById(R.id.user_name_tv);
            this.recyclerViewClickListener = recyclerViewClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            recyclerViewClickListener.onItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public StudentsAddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userstoadd_item, parent, false);
        return new MyViewHolder(itemView, mrecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAddAdapter.MyViewHolder holder, int position) {      //zjistíme, na který item jsme klinuli
        String email = studentAddItems.get(position).getStudentEmail();
        String username = studentAddItems.get(position).getStudentName();
        holder.emailTxt.setText(email);
        holder.nameTxt.setText(username);

    }

    @Override
    public int getItemCount() {
        return studentAddItems.size();
    }
}