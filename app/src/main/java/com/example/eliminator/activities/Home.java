package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eliminator.R;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
    private CardView ffsds,ffcs,fft;
    ImageView ffsds_pic,ffcs_pic,fft_pic;
    Intent intent;
public void loadHomeImages(){
    ffsds_pic=findViewById(R.id.home_ffsds);
    ffcs_pic=findViewById(R.id.home_ffcs);
    fft_pic=findViewById(R.id.home_fft);
    Picasso.get().load("https://eliminator-images.s3.ap-south-1.amazonaws.com/home_images/ffcs.jpg")
            .into(ffcs_pic);
    Picasso.get().load("https://eliminator-images.s3.ap-south-1.amazonaws.com/home_images/ffsds.jpg")
            .into(ffsds_pic);
    Picasso.get().load("https://eliminator-images.s3.ap-south-1.amazonaws.com/home_images/fft.jpg")
            .into(fft_pic);
}
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
        loadHomeImages();
    }
}