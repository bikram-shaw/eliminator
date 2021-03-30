package com.example.eliminator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.eliminator.R;
import com.example.eliminator.adapters.MatchesAdapters;
import com.example.eliminator.fragments.Ongoing;
import com.example.eliminator.fragments.Result;
import com.example.eliminator.fragments.Upcoming;
import com.google.android.material.tabs.TabLayout;

public class Matches extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MatchesAdapters matchesAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        tabLayout=findViewById(R.id.matches_tab);
        viewPager=findViewById(R.id.matches_pager);
        matchesAdapters=new MatchesAdapters(getSupportFragmentManager());
        matchesAdapters.addFragment(new Upcoming(),"Upcoming");
        matchesAdapters.addFragment(new Ongoing(),"Ongoing");
        matchesAdapters.addFragment(new Result(),"Result");
        viewPager.setAdapter(matchesAdapters);
        tabLayout.setupWithViewPager(viewPager);


    }
}