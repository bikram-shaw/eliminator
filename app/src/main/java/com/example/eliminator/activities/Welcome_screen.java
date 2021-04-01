package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eliminator.R;
import com.example.eliminator.helper.SharedPreference;

public class Welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        SharedPreference sharedPreference=SharedPreference.getInstance(getApplicationContext());
        if(sharedPreference.isLoggedIn())
        {
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
        else {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

    }
}