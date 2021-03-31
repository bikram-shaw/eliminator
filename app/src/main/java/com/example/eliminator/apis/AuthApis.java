package com.example.eliminator.apis;

import com.example.eliminator.modal.UpcomingMatches;
import com.example.eliminator.modal.UserDetails;
import com.google.gson.JsonObject;

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
    Call<UpcomingMatches> upcomingMatches(
            JsonObject jsonObject
    );
    @GET("game/")
    Call<UpcomingMatches> ongoingMatches(
            JsonObject jsonObject
    );
    @GET("game/")
    Call<UpcomingMatches> resultMatches(
            JsonObject jsonObject
    );
}
