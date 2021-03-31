package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eliminator.R;
import com.example.eliminator.apis.AuthApis;
import com.example.eliminator.apis.BaseUrl;
import com.example.eliminator.helper.SharedPreference;
import com.example.eliminator.modal.ResponseMessage;
import com.example.eliminator.modal.UserDetails;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    private EditText mobile,email,pass,cpass,refer_code;
    private Button signup_btn,login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onBindViewWithObjects();
    }

    private void onBindViewWithObjects(){
        mobile=findViewById(R.id.reg_mobile);
        email=findViewById(R.id.reg_email);
        pass=findViewById(R.id.reg_pass);
        cpass=findViewById(R.id.reg_cpass);
        refer_code=findViewById(R.id.reg_refer_code);
        signup_btn=findViewById(R.id.reg_btn);
        login_btn=findViewById(R.id.reg_login_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
    public void signup(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApis authApis = retrofit.create(AuthApis.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mobile.getText().toString());
        jsonObject.addProperty("password", pass.getText().toString());
        jsonObject.addProperty("email", email.getText().toString());
        jsonObject.addProperty("refer_code", refer_code.getText().toString());
        System.out.println(jsonObject);
        Call<UserDetails> call = authApis.signup(jsonObject);
        System.out.println(call.request().url());
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    // Loader.hideProgressDialog(new ProgressDialog(getApplicationContext()));
                    UserDetails userDetails = response.body();
                    SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
                    sharedPreference.insertUserData(userDetails);
                    Intent intent = new Intent(Register.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
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
            public void onFailure(Call<UserDetails> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}