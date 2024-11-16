package com.example.perfectmovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perfectmovie.models.Trailer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trailers extends AppCompatActivity {

    Apiinterface apiInterface;
    RecyclerView recyclerVid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        apiInterface = ServiceBuilder.buildRequest().create(Apiinterface.class);
        recyclerVid = findViewById(R.id.recycleVid);
        int id = getIntent().getIntExtra("id", 0);
        Call<Trailer> getTr = apiInterface.getVId(id);
        getTr.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                if(response.isSuccessful()) {
                    recyclerVid.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerVid.setHasFixedSize(true);
                    Trailer trailer = response.body();
                    VideoAdapter vidAdapter = new VideoAdapter(Trailers.this, trailer.getItems());
                    recyclerVid.setAdapter(vidAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Мдааа, ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
