package com.example.finalproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalproject.R;
import com.example.finalproject.fragments.clubFragment;
import com.example.finalproject.fragments.djFragment;
import com.example.finalproject.fragments.homeFragment;
import com.example.finalproject.fragments.songsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

public class SecActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    homeFragment homeFragment = new homeFragment();
    djFragment djFragment = new djFragment();
    clubFragment clubFragment = new clubFragment();
    songsFragment songsFragment = new songsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        bottomNavigationView = findViewById(R.id.bottomNavView);

        getSupportFragmentManager().beginTransaction().replace(R.id.bottomView,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomView, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.djs) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomView, djFragment).commit();
                    return true;
                } else if (itemId == R.id.clubs) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomView, clubFragment).commit();
                    return true;
                } else if (itemId == R.id.songs) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomView, songsFragment).commit();
                    return true;
                }
                return false;
            }

        });
    }
}