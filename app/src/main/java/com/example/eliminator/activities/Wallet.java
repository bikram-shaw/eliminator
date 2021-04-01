package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.adapters.TransactionsAdapter;
import com.example.eliminator.adapters.UpcomingMatchesAdapter;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.helper.TokenInterceptor;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.TransactionResponse;
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

public class Wallet extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        recyclerView=findViewById(R.id.txn_recycle);
        getTransactions();

    }
    public void getTransactions(){
        ProgressDialog progressDialog = new ProgressDialog(Wallet.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUserData().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        Call<TransactionResponse> call = authApis.getTransactions("1");
        System.out.println(call.request().url());
        call.enqueue(new Callback< TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response< TransactionResponse> response) {
                if (response.isSuccessful()) {
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    TransactionResponse transactionResponse = response.body();
                    TransactionsAdapter transactionsAdapter=new TransactionsAdapter(getApplicationContext(),transactionResponse.getResults());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(transactionsAdapter);
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
            public void onFailure(Call< TransactionResponse> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
                System.out.println(call.request().url());

                Toast.makeText(getApplicationContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

}
