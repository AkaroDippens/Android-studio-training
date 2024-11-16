package com.example.perfectmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.perfectmovie.models.Film;
import com.example.perfectmovie.models.FilmInfo;
import com.example.perfectmovie.models.Root;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setFragment(new WaitFragment());
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bott_wait:
                        setFragment(new WaitFragment());
                        return true;
                    case R.id.bott_top:
                        setFragment(new TopFragment());
                        return true;
                }
                return false;
            }
        });
    }
    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment, null).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}