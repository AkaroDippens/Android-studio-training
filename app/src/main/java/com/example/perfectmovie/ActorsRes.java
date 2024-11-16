package com.example.perfectmovie;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.ActorsRegisers;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorsRes extends AppCompatActivity {
    public boolean is_opened = false;

    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    public ActorsAdapter actorsAdapter;
    public Apiinterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actorsres_card);
        recyclerView = findViewById(R.id.recycleActors);

        progressBar = findViewById(R.id.progressBar);
        apiInterface = ServiceBuilder.buildRequest().create(Apiinterface.class);
        int id = getIntent().getIntExtra("id", 0);
        Call<ArrayList<ActorsRegisers>> call = apiInterface.getActors(id);

        call.enqueue(new Callback<ArrayList<ActorsRegisers>>() {
            @Override
            public void onResponse(Call<ArrayList<ActorsRegisers>> call, Response<ArrayList<ActorsRegisers>> response) {
                if (response.isSuccessful()) {
                    is_opened = true;
                    ArrayList<ActorsRegisers> actorsList = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActorsRes.this));
                    recyclerView.setAdapter(actorsAdapter);
                    progressBar.setVisibility(View.GONE);
                } else{
                    Toast.makeText(getApplicationContext(), "Мдааа, ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ActorsRegisers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

