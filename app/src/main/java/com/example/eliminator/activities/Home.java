package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eliminator.R;

public class Home extends AppCompatActivity {
    private CardView ffsds,ffcs,fft;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ffsds=findViewById(R.id.home_ffsds_card);
        ffcs=findViewById(R.id.home_ffcs_card);
        fft=findViewById(R.id.home_fft_card);
        ffsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent=new Intent(getApplicationContext(),Matches.class);
               intent.putExtra("game_mode","ffsds");
                startActivity(intent);

            }
        });
        ffcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),Matches.class);
                intent.putExtra("game_mode","ffcs");
                startActivity(intent);

            }
        });
        fft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),Matches.class);
                intent.putExtra("game_mode","fft");
                startActivity(intent);

            }
        });
    }
}