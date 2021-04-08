package com.example.reading_teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.VViewHolder>{
    private ArrayList<LeaderboardItem>leaderboardItems;


    public LeaderboardAdapter(ArrayList<LeaderboardItem> leaderboardItems){
        this.leaderboardItems = leaderboardItems;

    }

    public class VViewHolder extends RecyclerView.ViewHolder{
        private TextView usernameTv, percentTv;



        public VViewHolder(final View view){
            super(view);
            usernameTv = view.findViewById(R.id.leaderboard_name);
            percentTv = view.findViewById(R.id.leaderboard_percent);

        }

    }

    @NonNull
    @Override
    public LeaderboardAdapter.VViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new VViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.VViewHolder holder, int position) {      //zjistíme, na který item jsme klinuli
        String user = leaderboardItems.get(position).getUser();
        String percent = leaderboardItems.get(position).getPercent();
        holder.usernameTv.setText(user);
        holder.percentTv.setText(percent);

    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
    }
}

