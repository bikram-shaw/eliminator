package com.example.eliminator.apis;

import com.example.eliminator.modal.UserDetails;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApis {
    @POST("auth/login/")
    Call<UserDetails> login(
            @Body JsonObject data


    );
    @POST("auth/register/")
    Call<UserDetails> signup(
            @Body JsonObject jsonObject
    );
}
