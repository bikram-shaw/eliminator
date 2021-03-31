package com.example.eliminator.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eliminator.R;
import com.example.eliminator.activities.Matches;

public class Upcoming extends Fragment {

    public Upcoming() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Matches matches= (Matches) getActivity();
        System.out.println(matches.getGameMode());

        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }
    public void getUpcomingMatches(){

    }

}