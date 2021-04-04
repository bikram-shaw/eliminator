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
import com.example.eliminator.activities.Home;
import com.example.eliminator.activities.Login;
import com.example.eliminator.activities.Matches;
import com.example.eliminator.activities.PlayerList;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.helper.TokenInterceptor;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.UpcomingMatches;
import com.example.eliminator.modal.UserDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingMatchesAdapter extends RecyclerView.Adapter<UpcomingMatchesAdapter.ViewHolder>{
    Context context;
    ArrayList<UpcomingMatches> upcomingMatches=new ArrayList<>();

    public UpcomingMatchesAdapter(Context context, ArrayList<UpcomingMatches> upcomingMatches)
    {
        this.context=context;
        this.upcomingMatches=upcomingMatches;
    }
    @NonNull
    @Override
    public UpcomingMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.upcoming_match_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMatchesAdapter.ViewHolder holder, int position) {
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
          String is_join=upcomingMatches.get(position).getIs_join();
        if(Integer.parseInt(is_join)==1){
            holder.join_btn.setText("Joined");
            holder.join_btn.setTextColor(Color.WHITE);
            holder.join_btn.setBackgroundColor(Color.rgb(255, 204, 0));
            holder.join_btn.setEnabled(false);
        }
          holder.title.setText("# "+upcomingMatches.get(position).getId()+" "+upcomingMatches.get(position).getType()+" Match");
          int is_free_spot=Integer.parseInt(upcomingMatches.get(position).getSpots())-Integer.parseInt(upcomingMatches.get(position).getJoin_spot());
          if(is_free_spot>0){
              holder.join_btn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      openJoinGamePopup(upcomingMatches.get(position).getId(),upcomingMatches.get(position).getType(),
                              upcomingMatches.get(position).getEntry_fee());
                  }
              });
          }
          else {
              if(Integer.parseInt(is_join)==0) {
                  holder.join_btn.setText("Match Full");
                  holder.join_btn.setTextColor(Color.WHITE);
                  holder.join_btn.setBackgroundColor(Color.rgb(181, 33, 0));
                  holder.join_btn.setEnabled(false);
              }
          }
          holder.player_list_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context, PlayerList.class);
                  intent.putExtra("game_id",upcomingMatches.get(position).getId());
                  context.startActivity(intent);
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
        Button player_list_btn,join_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          date_time=itemView.findViewById(R.id.match_card_date_time);
          win_amount=itemView.findViewById(R.id.match_card_win_amount);
          per_kill=itemView.findViewById(R.id.match_card_per_kill);
          entry_fee=itemView.findViewById(R.id.match_card_entry_fee);
          category=itemView.findViewById(R.id.match_card_category);
          type=itemView.findViewById(R.id.match_card_type);
          title=itemView.findViewById(R.id.upcoming_match_title);
          map=itemView.findViewById(R.id.match_card_map);
          spots=itemView.findViewById(R.id.match_card_spots);
          progressBar=itemView.findViewById(R.id.match_card_progress_bar);
          player_list_btn=itemView.findViewById(R.id.match_card_player_list_btn);
          join_btn=itemView.findViewById(R.id.match_card_join_btn);

        }
    }
    public void openJoinGamePopup(String matchId,String type,String entry_fee){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.join_match_popup,null);
        final EditText player1 = mView.findViewById(R.id.player_one);
        final EditText player2 = mView.findViewById(R.id.player_two);
        final EditText player3 = mView.findViewById(R.id.player_three);
        final EditText player4 = mView.findViewById(R.id.player_four);
        if(type.equals("Solo"))
        {
            player2.setVisibility(View.GONE);
            player3.setVisibility(View.GONE);
            player4.setVisibility(View.GONE);
        }
        else if(type.equals("Duo"))
        {
            player3.setVisibility(View.GONE);
            player4.setVisibility(View.GONE);
        }
        else{

        }

        Button btn_cancel = (Button)mView.findViewById(R.id.join_player_cancel_btn);
        Button btn_okay = (Button)mView.findViewById(R.id.join_player_ok_btn);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinMatch(matchId,player1.getText().toString(),player2.getText().toString(),player3.getText().toString()
                ,player4.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    public void joinMatch(String matchId,String player1, String player2, String player3, String player4){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(context).getUserData().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("player_name", player1+" "+player2+" "+player3+" "+player4);
        jsonObject.addProperty("game", matchId);
        System.out.println(jsonObject);
        Call<ResponseMessage> call = authApis.joinMatch(jsonObject);
        System.out.println(call.request().url());
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    System.out.println("success");
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    ResponseMessage responseMessage = response.body();
                    Toast.makeText(context, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.dismiss();
                    System.out.println("akhane");
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage responseMessage = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(context, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println(responseMessage.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("catch");
                    }

                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("failed");
                Toast.makeText(context, "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
