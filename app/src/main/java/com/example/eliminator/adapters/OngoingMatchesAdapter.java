package com.example.eliminator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliminator.R;
import com.example.eliminator.modal.UpcomingMatches;

import java.util.ArrayList;

public class OngoingMatchesAdapter extends RecyclerView.Adapter<OngoingMatchesAdapter.ViewHolder>{
    Context context;
    ArrayList<UpcomingMatches> upcomingMatches=new ArrayList<>();

    public OngoingMatchesAdapter(Context context, ArrayList<UpcomingMatches> upcomingMatches)
    {
        this.context=context;
        this.upcomingMatches=upcomingMatches;
    }
    @NonNull
    @Override
    public OngoingMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ongoing_match_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingMatchesAdapter.ViewHolder holder, int position) {
        holder.date_time.setText(upcomingMatches.get(position).getDate()+" at "+upcomingMatches.get(position).getTime());
        holder.win_amount.setText(upcomingMatches.get(position).getWinning_prize());
        holder.per_kill.setText(upcomingMatches.get(position).getPer_kill());
        holder.entry_fee.setText(upcomingMatches.get(position).getEntry_fee());
        holder.category.setText(upcomingMatches.get(position).getCategory());
        holder.type.setText(upcomingMatches.get(position).getType());
        holder.map.setText(upcomingMatches.get(position).getMap());
        holder.spots.setText(upcomingMatches.get(position).getJoin_spot()+" / "+upcomingMatches.get(position).getSpots());
        holder.progressBar.setProgress(Integer.parseInt(upcomingMatches.get(position).getJoin_spot()));
        holder.progressBar.setMax(Integer.parseInt(upcomingMatches.get(position).getSpots()));

    }

    @Override
    public int getItemCount() {
        return upcomingMatches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_time,win_amount,per_kill,entry_fee,category,type,map,spots;
        ProgressBar progressBar;
        Button watch_btn,room_details_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_time=itemView.findViewById(R.id.ongoing_match_card_date_time);
            win_amount=itemView.findViewById(R.id.ongoing_match_card_win_amount);
            per_kill=itemView.findViewById(R.id.ongoing_match_card_per_kill);
            entry_fee=itemView.findViewById(R.id.ongoing_match_card_entry_fee);
            category=itemView.findViewById(R.id.ongoing_match_card_category);
            type=itemView.findViewById(R.id.ongoing_match_card_type);
            map=itemView.findViewById(R.id.ongoing_match_card_map);
            spots=itemView.findViewById(R.id.ongoing_match_card_spots);
            progressBar=itemView.findViewById(R.id.ongoing_match_card_progress_bar);
           watch_btn=itemView.findViewById(R.id.ongoing_match_card_watch_btn);
            room_details_btn=itemView.findViewById(R.id.ongoing_match_card_room_details_btn);

        }
    }
}
