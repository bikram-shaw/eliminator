package com.example.eliminator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.activities.Matches;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.UpcomingMatches;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ongoing extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Matches matches= (Matches) getActivity();
        System.out.println(matches.getGameMode());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing, container, false);
    }
    public void getOngoingMatches(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        JsonObject jsonObject = new JsonObject();
        // jsonObject.addProperty("mobile", mobile.getText().toString());
        //jsonObject.addProperty("password", password.getText().toString());
        System.out.println(jsonObject);
        Call<UpcomingMatches> call = authApis.upcomingMatches(jsonObject);
        System.out.println(call.request().url());
        call.enqueue(new Callback<UpcomingMatches>() {
            @Override
            public void onResponse(Call<UpcomingMatches> call, Response<UpcomingMatches> response) {
                if (response.isSuccessful()) {
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    UpcomingMatches upcomingMatches = response.body();

                } else {
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
            public void onFailure(Call<UpcomingMatches> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}