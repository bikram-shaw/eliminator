package com.example.eliminator.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliminator.R;
import com.example.eliminator.modal.JoinPlayerList;

import java.util.ArrayList;

public class JoinPlayerListAdapter extends RecyclerView.Adapter<JoinPlayerListAdapter.ViewHolder> {
    Context context;
    ArrayList<JoinPlayerList> joinPlayerLists=new ArrayList<>();

    public JoinPlayerListAdapter(Context context, ArrayList<JoinPlayerList> joinPlayerLists)
    {
        this.context=context;
        this.joinPlayerLists=joinPlayerLists;
    }
    @NonNull
    @Override
    public JoinPlayerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JoinPlayerListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.join_player_list_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull JoinPlayerListAdapter.ViewHolder holder, int position) {
        int i=1;
        holder.player_name.setText(joinPlayerLists.get(position).getPlayer_name());
        holder.team.setText("Team "+(i++));
    }

    @Override
    public int getItemCount() {
        return joinPlayerLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView player_name,team;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            player_name=itemView.findViewById(R.id.join_player_list_name);
            team=itemView.findViewById(R.id.join_player_list_team);


        }
    }
}
