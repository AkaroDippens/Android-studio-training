package com.example.perfectmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.FilmInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmDetails extends AppCompatActivity {

    TextView details, name;
    ImageView imageView;
    Apiinterface apiInterface;
    RecyclerView recycler;
    Button button, button2;
    Animation scale, scaleout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        scale = AnimationUtils.loadAnimation(FilmDetails.this, R.anim.scale);
        details = findViewById(R.id.details);
        imageView = findViewById(R.id.Photo);
        name = findViewById(R.id.name);
        recycler = findViewById(R.id.recycleDet);
        recycler.startAnimation(scale);

        button = findViewById(R.id.gototr);
        button.startAnimation(scale);
        button2 = findViewById(R.id.actors);
        button2.startAnimation(scale);

        int id = getIntent().getIntExtra("id", 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Trailers.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), ActorsRes.class);
                intent2.putExtra("id", id);
                startActivity(intent2);
            }
        });

        apiInterface = ServiceBuilder.buildRequest().create(Apiinterface.class);

        Call<FilmInfo> getFilm = apiInterface.getFid(id);
        getFilm.enqueue(new Callback<FilmInfo>() {
            @Override
            public void onResponse(Call<FilmInfo> call, Response<FilmInfo> response) {
                if (response.isSuccessful()) {
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler.setHasFixedSize(true);
                    FilmInfo filmInfo = response.body();
                    DetailsAdapter filmAdapter = new DetailsAdapter(FilmDetails.this, filmInfo);
                    recycler.setAdapter(filmAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Мдааа, ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
