package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.adapters.JoinPlayerListAdapter;
import com.example.eliminator.adapters.UpcomingMatchesAdapter;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.helper.TokenInterceptor;
import com.example.eliminator.modal.JoinPlayerList;
import com.example.eliminator.modal.ResponseMessage;
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

public class PlayerList extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        recyclerView=findViewById(R.id.join_player_list_recycle);

        getPlayerList();
    }
    public void getPlayerList(){
        ProgressDialog progressDialog = new ProgressDialog(PlayerList.this);
       progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUserData().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        String game_id=getIntent().getStringExtra("game_id");
        System.out.println(game_id);
        Call<ArrayList<JoinPlayerList>> call = authApis.getJoinPlayerList(game_id);
        System.out.println(call.request().url());
        call.enqueue(new Callback< ArrayList<JoinPlayerList>>() {
            @Override
            public void onResponse(Call< ArrayList<JoinPlayerList>> call, Response< ArrayList<JoinPlayerList>> response) {
                if (response.isSuccessful()) {
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    ArrayList<JoinPlayerList> joinPlayerLists = response.body();
                    JoinPlayerListAdapter joinPlayerListAdapter=new JoinPlayerListAdapter(getApplicationContext(),joinPlayerLists);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(joinPlayerListAdapter);
                    progressDialog.dismiss();


                } else {
                    progressDialog.dismiss();
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage responseMessage = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(getApplicationContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call< ArrayList<JoinPlayerList>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
                System.out.println(call.request().url());

                Toast.makeText(getApplicationContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}