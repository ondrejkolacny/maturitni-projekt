package com.example.reading_teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class PlansRecyclerAdapter extends RecyclerView.Adapter<PlansRecyclerAdapter.MyViewHolder>{
    private ArrayList<PlansListItem>plansListItems;
    private GroupRecyclerClick mrecyclerViewClickListener;

    public PlansRecyclerAdapter(ArrayList<PlansListItem> plansListItems, GroupRecyclerClick groupRecyclerClick){
        this.plansListItems = plansListItems;
        this.mrecyclerViewClickListener = groupRecyclerClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nazevTxt,popis;
        GroupRecyclerClick recyclerViewClickListener;


        public MyViewHolder(final View view, GroupRecyclerClick recyclerViewClickListener){
            super(view);
            nazevTxt = view.findViewById(R.id.plan_title);

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
    public PlansRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_list_item, parent, false);
        return new MyViewHolder(itemView, mrecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlansRecyclerAdapter.MyViewHolder holder, int position) {      //zjistíme, na který item jsme klinuli
        String nazev = plansListItems.get(position).getPlanName();
        holder.nazevTxt.setText(nazev);

    }

    @Override
    public int getItemCount() {
        return plansListItems.size();
    }
}
