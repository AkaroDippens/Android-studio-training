package com.example.paperdb18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView listFootballer;
    Button addFootballer;
    RecyclerViewInterface recyclerViewInterface = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        listFootballer = findViewById(R.id.list_footballer);
        addFootballer = findViewById(R.id.add_footballer);

        addFootballer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFootballerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        PaperDBHelper paperDBHelper = new PaperDBHelper();
        listFootballer.setLayoutManager(new LinearLayoutManager(this));
        listFootballer.setHasFixedSize(true);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, paperDBHelper.getFootballers(), recyclerViewInterface);
        listFootballer.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Footballer footballer) {
        Intent intent = new Intent(MainActivity.this, FootballerDetailActivity.class);
        intent.putExtra("id", footballer.getId());
        startActivity(intent);
        finish();
    }
}
