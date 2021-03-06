package com.example.eliminator.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.activities.Home;
import com.example.eliminator.activities.Login;
import com.example.eliminator.activities.Matches;
import com.example.eliminator.adapters.UpcomingMatchesAdapter;
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

public class Upcoming extends Fragment {
    private RecyclerView recyclerView;
    String match_type=null;
    public Upcoming() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=view.findViewById(R.id.upcoming_recycler);
        getUpcomingMatches(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public void getUpcomingMatches(View view){
        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getContext()).getUserData().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        Matches matches= (Matches) getActivity();
        match_type=matches.getGameMode();
        System.out.println("sdd"+match_type);
        Call< ArrayList<UpcomingMatches>> call = authApis.upcomingMatches(match_type,"active");
        System.out.println(call.request().url());
        call.enqueue(new Callback< ArrayList<UpcomingMatches>>() {
            @Override
            public void onResponse(Call< ArrayList<UpcomingMatches>> call, Response< ArrayList<UpcomingMatches>> response) {
                if (response.isSuccessful()) {
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    ArrayList<UpcomingMatches> upcomingMatches = response.body();
                    UpcomingMatchesAdapter upcomingMatchesAdapter=new UpcomingMatchesAdapter(view.getContext(),upcomingMatches);
                    System.out.println(upcomingMatches.toString());
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recyclerView.setAdapter(upcomingMatchesAdapter);
                    progressDialog.dismiss();


                } else {
                    progressDialog.dismiss();
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage responseMessage = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call< ArrayList<UpcomingMatches>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
                System.out.println(call.request().url());

                Toast.makeText(getContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

}