 package com.example.reading_student;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<DataToList>planData;
    private RecyclerViewClickListener mrecyclerViewClickListener;

    public RecyclerAdapter(ArrayList<DataToList> planData, RecyclerViewClickListener recyclerViewClickListener){
        this.planData = planData;
        this.mrecyclerViewClickListener = recyclerViewClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nazevTxt,skupinaTxt;
        RecyclerViewClickListener recyclerViewClickListener;


        public MyViewHolder(final View view, RecyclerViewClickListener recyclerViewClickListener){
            super(view);
            nazevTxt = view.findViewById(R.id.TitleTv);
            skupinaTxt = view.findViewById(R.id.description);
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
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new MyViewHolder(itemView, mrecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {      //zjistíme, na který item jsme klinuli
        String nazev = planData.get(position).getPlanName();
        String groupNazev = planData.get(position).getGroupName();
        holder.nazevTxt.setText(nazev);
        holder.skupinaTxt.setText(groupNazev);

    }

    @Override
    public int getItemCount() {
        return planData.size();
    }
}

