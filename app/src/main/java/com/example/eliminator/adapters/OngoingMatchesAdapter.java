package com.example.eliminator.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliminator.R;
import com.example.eliminator.activities.Wallet;
import com.example.eliminator.activities.Youtube;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.helper.TokenInterceptor;
import com.example.eliminator.modal.GenerateChecksum;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.RoomDetails;
import com.example.eliminator.modal.UpcomingMatches;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        holder.title.setText("# "+upcomingMatches.get(position).getId()+" "+upcomingMatches.get(position).getType()+" Match");
        String is_join=upcomingMatches.get(position).getIs_join();
        if(Integer.parseInt(is_join)==1){
            holder.room_details_btn.setText("Get Room Details");
            holder.room_details_btn.setTextColor(Color.WHITE);
            holder.room_details_btn.setBackgroundColor(Color.rgb(0, 153, 51));

        }
        else{
            holder.room_details_btn.setText("Not Joined");
            holder.room_details_btn.setTextColor(Color.WHITE);
            holder.room_details_btn.setBackgroundColor(Color.rgb(181, 33, 0));
            holder.room_details_btn.setEnabled(false);
        }
        holder.room_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRoomDetails(upcomingMatches.get(position).getId());
            }
        });
        holder.watch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, Youtube.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return upcomingMatches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_time,win_amount,per_kill,entry_fee,category,type,map,spots,title;
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
            title=itemView.findViewById(R.id.ongoing_match_card_title);
            map=itemView.findViewById(R.id.ongoing_match_card_map);
            spots=itemView.findViewById(R.id.ongoing_match_card_spots);
            progressBar=itemView.findViewById(R.id.ongoing_match_card_progress_bar);
           watch_btn=itemView.findViewById(R.id.ongoing_match_card_watch_btn);
            room_details_btn=itemView.findViewById(R.id.ongoing_match_card_room_details_btn);

        }
    }
    public void getRoomDetails(String game_id){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(context).getUserData().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        Call<RoomDetails> call = authApis.getRoomDetails(game_id);
        System.out.println(call.request().url());

        call.enqueue(new Callback<RoomDetails>() {
            @Override
            public void onResponse(Call<RoomDetails> call, Response< RoomDetails> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    RoomDetails roomDetails=response.body();
                    roomDetailsPopup(roomDetails.getRoom_id(),roomDetails.getPassword());
                    System.out.println(response.body().toString());



                } else {
                    progressDialog.dismiss();
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage responseMessage = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(context, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RoomDetails> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void roomDetailsPopup(String room_id,String password){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.room_details_popup,null);
        final TextView roomid = mView.findViewById(R.id.room_details_id);
        final TextView roomPass = mView.findViewById(R.id.room_details_password);
        Button ok=mView.findViewById(R.id.room_details_ok_btn);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        roomid.setText("Room Id : "+room_id);
        roomPass.setText("Password : "+password);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
