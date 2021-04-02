package com.example.eliminator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.eliminator.R;
import com.example.eliminator.fragments.Ongoing;
import com.example.eliminator.fragments.Result;
import com.example.eliminator.fragments.Upcoming;
import com.example.eliminator.helper.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Matches extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener,Toolbar.OnMenuItemClickListener{
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        toolbar=findViewById(R.id.matches_toolbar);
        toolbar.inflateMenu(R.menu.toobar_menu);
        toolbar.setOnMenuItemClickListener( this);
        toolbar.setLogo(R.drawable.ic_menu);
        drawerLayout=findViewById(R.id.matches_drawable_layout);
        navigationView=findViewById(R.id.sidebar_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sidemenu_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                        break;
                    case R.id.sidemenu_profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));

                        break;
                    case R.id.sidemenu_wallet:
                        startActivity(new Intent(getApplicationContext(),Wallet.class));

                        break;
                    case R.id.sidemenu_refer:
                        startActivity(new Intent(getApplicationContext(),Refer_Earn.class));

                        break;
                    case R.id.sidemenu_terms:
                        startActivity(new Intent(getApplicationContext(),T_And_C.class));


                        break;
                    case R.id.sidemenu_contactus:
                        startActivity(new Intent(getApplicationContext(),Contact_Us.class));

                        break;
                    case R.id.sidemenu_aboutus:
                        startActivity(new Intent(getApplicationContext(),AboutUs.class));

                        break;
                    case R.id.sidemenu_logout:
                        SharedPreference sharedPreference=SharedPreference.getInstance(getApplicationContext());
                        sharedPreference.logout();
                        Intent intent=new Intent(getApplicationContext(),Login.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();

                        break;
                }
                closeNavigationMenuView();
                return true;
            }
        });

        // Find logo
        System.out.println(toolbar.getChildCount());
        View view = toolbar.getChildAt(1);
        //to open navigation after clicking Logo Icon
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                // Perform actions
            }
        });
        loadFragment(new Upcoming());
        BottomNavigationView navigation = findViewById(R.id.bottom_menu);
        navigation.setOnNavigationItemSelectedListener(Matches.this);
    }


    public String getGameMode()
    {
        Intent intent=getIntent();
        return intent.getStringExtra("game_mode");
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.toolbar_menu_wallet:
               startActivity(new Intent(getApplicationContext(),Wallet.class));
                break;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.bottom_upcoming:

                fragment = new Upcoming();
                break;

            case R.id.bottom_ongoing:

                fragment = new Ongoing();
                break;


            case R.id.bottom_result:

                fragment = new Result();
                break;



        }

        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.matches_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    public void closeNavigationMenuView(){
      drawerLayout.closeDrawers();
    }
}