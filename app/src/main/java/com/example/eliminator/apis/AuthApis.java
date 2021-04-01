package com.example.eliminator.apis;

import com.example.eliminator.modal.TransactionResponse;
import com.example.eliminator.modal.UpcomingMatches;
import com.example.eliminator.modal.UserDetails;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApis {
    @POST("auth/login/")
    Call<UserDetails> login(
            @Body JsonObject data


    );
    @POST("auth/register/")
    Call<UserDetails> signup(
            @Body JsonObject jsonObject
    );
    @GET("game/")
    Call<ArrayList<UpcomingMatches>> upcomingMatches(
            @Query("game") String game,
            @Query("status") String status
    );
    @GET("game/")
    Call<ArrayList<UpcomingMatches>> ongoingMatches(
            @Query("game") String game,
            @Query("status") String status
    );
    @GET("game/")
    Call<UpcomingMatches> resultMatches(
            @Query("game") String game,
            @Query("status") String status
    );
    @GET("transaction/")
    Call<TransactionResponse> getTransactions(
            @Query("page") String page

    );
}
