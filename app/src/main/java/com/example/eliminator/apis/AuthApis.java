package com.example.eliminator.apis;

import com.example.eliminator.modal.GenerateChecksum;
import com.example.eliminator.modal.JoinPlayerList;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.RoomDetails;
import com.example.eliminator.modal.TransactionResponse;
import com.example.eliminator.modal.UpcomingMatches;
import com.example.eliminator.modal.UserDetails;
import com.example.eliminator.modal.Wallet;
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
    @GET("wallet/")
    Call<Wallet> getWalletBalance(


    );
    @GET("add-money/")
    Call<GenerateChecksum> getChecksum(
            @Query("amount") String amount

    );
    @GET("join-match/")
    Call<ArrayList<JoinPlayerList>> getJoinPlayerList(
            @Query("game_id") String game_id

    );
    @POST("join-match/")
    Call<ResponseMessage> joinMatch(
            @Body JsonObject data


    );

    @GET("room-details/")
    Call<RoomDetails> getRoomDetails(
            @Query("game_id") String game_id

    );
}
