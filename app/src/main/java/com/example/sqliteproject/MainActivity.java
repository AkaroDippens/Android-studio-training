package com.example.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView rv;

    RecyclerViewInterface recyclerViewInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.list_footballer);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        RecyclerViewAdapter footballAdapter = new RecyclerViewAdapter(this, dataBaseHelper.getFootballers(),
                this);
        rv.setAdapter(footballAdapter);


        Button addFootballerButton = findViewById(R.id.add_footballer);
        addFootballerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFootballerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(Footballer footballer) {
        Intent intent = new Intent(MainActivity.this, FootballersDetailActivity.class);
        intent.putExtra("id", footballer.getID_Footballer());
        startActivity(intent);
    }
}