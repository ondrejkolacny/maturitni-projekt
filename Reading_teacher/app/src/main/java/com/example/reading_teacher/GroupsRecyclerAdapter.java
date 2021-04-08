package com.example.reading_teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.MyViewHolder>{
    private ArrayList<GroupsListItem>groupsListItems;
    private GroupRecyclerClick mrecyclerViewClickListener;

    public GroupsRecyclerAdapter(ArrayList<GroupsListItem> groupsListItems, GroupRecyclerClick groupRecyclerClick){
        this.groupsListItems = groupsListItems;
        this.mrecyclerViewClickListener = groupRecyclerClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nazevTxt,popis;
        GroupRecyclerClick recyclerViewClickListener;


        public MyViewHolder(final View view, GroupRecyclerClick recyclerViewClickListener){
            super(view);
            nazevTxt = view.findViewById(R.id.TitleTv);
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
    public GroupsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        return new MyViewHolder(itemView, mrecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsRecyclerAdapter.MyViewHolder holder, int position) {      //zjistíme, na který item jsme klinuli
        String nazev = groupsListItems.get(position).getGroupName();
        holder.nazevTxt.setText(nazev);

    }

    @Override
    public int getItemCount() {
        return groupsListItems.size();
    }
}
