package com.example.eliminator.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.adapters.TransactionsAdapter;
import com.example.eliminator.adapters.UpcomingMatchesAdapter;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.helper.TokenInterceptor;
import com.example.eliminator.modal.GenerateChecksum;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.TransactionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.w3c.dom.ls.LSOutput;

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
TextView wal_bal,win_bal;
Button add_money_btn;
String callbackurl="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
private GenerateChecksum generateChecksum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        recyclerView=findViewById(R.id.txn_recycle);
        wal_bal=findViewById(R.id.wal_bal);
        win_bal=findViewById(R.id.win_bal);
        add_money_btn=findViewById(R.id.add_money_btn);
        getWalletBal();
        getTransactions();

        add_money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder alert = new AlertDialog.Builder(Wallet.this);
                View mView = getLayoutInflater().inflate(R.layout.add_money_popup,null);
                final EditText add_money_amount = mView.findViewById(R.id.add_money_amount);
                Button btn_cancel = (Button)mView.findViewById(R.id.add_money_cancel_btn);
                Button btn_okay = (Button)mView.findViewById(R.id.add_money_ok_btn);
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
                      String amount=add_money_amount.getText().toString();
                      if(!amount.equals(""))
                      {
                          getChecksum(amount);
                          alertDialog.dismiss();
                      }
                      else {
                          Toast.makeText(getApplicationContext(),"Enter a valid amount !",Toast.LENGTH_SHORT).show();
                      }



                    }
                });
                alertDialog.show();





            }
        });


    }
public void generateTransaction(String amount){
    PaytmOrder paytmOrder = new PaytmOrder(generateChecksum.getOrder_id(), "eVExLv25221231925149", generateChecksum.getChecksum(),
            amount, "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+generateChecksum.getOrder_id());
    TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
        @Override
        public void onTransactionResponse(@Nullable Bundle bundle) {
            System.out.println(bundle.toString());
            Toast.makeText(getApplicationContext(), "Payment Transaction response " + bundle.toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),Wallet.class));
        }

        @Override
        public void networkNotAvailable() {
            Toast.makeText(getApplicationContext(), "Turn on internet connectivity !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorProceed(String s) {

        }

        @Override
        public void clientAuthenticationFailed(String s) {

        }

        @Override
        public void someUIErrorOccurred(String s) {
            Toast.makeText(getApplicationContext(), "Something went wrong ,Try again !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorLoadingWebPage(int i, String s, String s1) {
            Toast.makeText(getApplicationContext(), "Something went wrong ,Try again !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBackPressedCancelTransaction() {
            Toast.makeText(getApplicationContext(), "Payment Failed !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Wallet.class));

        }

        @Override
        public void onTransactionCancel(String s, Bundle bundle) {
            Toast.makeText(getApplicationContext(), "Payment Failed !", Toast.LENGTH_SHORT).show();
        }
        // code statement);
    }

    );
    transactionManager.setShowPaymentUrl("https://securegw.paytm.in/theia/api/v1/showPaymentPage");
    transactionManager.startTransaction(Wallet.this, 2);
}

    public void getTransactions(){
        ProgressDialog progressDialog = new ProgressDialog(Wallet.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
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
public void getWalletBal(){
    ProgressDialog progressDialog = new ProgressDialog(Wallet.this);
    progressDialog.setMessage("Loading...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
    TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUserData().getToken());
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    Retrofit retrofit = new Retrofit.Builder().client(client)
            .baseUrl(BaseUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AuthApis authApis = retrofit.create(AuthApis.class);
    Call<com.example.eliminator.modal.Wallet> call = authApis.getWalletBalance();
    System.out.println(call.request().url());
    call.enqueue(new Callback<com.example.eliminator.modal.Wallet>() {
        @Override
        public void onResponse(Call<com.example.eliminator.modal.Wallet> call, Response< com.example.eliminator.modal.Wallet> response) {
            if (response.isSuccessful()) {
                // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                com.example.eliminator.modal.Wallet wallet = response.body();
                wal_bal.setText(wallet.getWal_bal());
                win_bal.setText(wallet.getWin_bal());
                System.out.println(wallet);
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
        public void onFailure(Call< com.example.eliminator.modal.Wallet> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
        }
    });
}
public void getChecksum(String amount){

    ProgressDialog progressDialog = new ProgressDialog(Wallet.this);
    progressDialog.setMessage("Loading...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
    TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUserData().getToken());
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    Retrofit retrofit = new Retrofit.Builder().client(client)
            .baseUrl(BaseUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AuthApis authApis = retrofit.create(AuthApis.class);
    Call<GenerateChecksum> call = authApis.getChecksum(amount);
    System.out.println(call.request().url());

    call.enqueue(new Callback<GenerateChecksum>() {
        @Override
        public void onResponse(Call<GenerateChecksum> call, Response< GenerateChecksum> response) {
            if (response.isSuccessful()) {
                // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                generateChecksum=response.body();
                generateTransaction(amount);
                System.out.println(response.body().toString());
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
        public void onFailure(Call< GenerateChecksum> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
        }
    });

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hry paytm code is r "+resultCode);
        if (requestCode == 2 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }
}
