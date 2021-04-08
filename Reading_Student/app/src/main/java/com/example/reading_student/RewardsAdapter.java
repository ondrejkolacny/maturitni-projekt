package com.example.reading_student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {               //https://awsrh.blogspot.com/2017/10/modern-profile-ui-design-in-android.html - design user profilu

    private List<RewardsItem> listItems;
    private Context context;

    public RewardsAdapter(List<RewardsItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reward_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RewardsAdapter.ViewHolder holder, int position) {

        RewardsItem listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.imageView.setImageResource(listItem.getImage());
    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView textViewHead;
    public TextView textViewDesc;
    public ImageView imageView;


    public ViewHolder(View itemView) {
        super(itemView);
        textViewHead = (TextView) itemView.findViewById(R.id.reward_title);
        textViewDesc = (TextView) itemView.findViewById(R.id.reward_description);
        imageView = (ImageView) itemView.findViewById(R.id.reward_image);
    }
}
}