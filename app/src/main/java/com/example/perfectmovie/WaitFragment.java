package com.example.perfectmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.Film;
import com.example.perfectmovie.models.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitFragment extends Fragment {

    Apiinterface apiInterface;
    RecyclerView recycler;
    ProgressBar progressBar;
    Animation slideinl, slideoutl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_wait, container, false);
        progressBar = (ProgressBar) view1.findViewById(R.id.progressBar);
        slideinl = AnimationUtils.loadAnimation(view1.getContext(), R.anim.slide_in_left);
        slideoutl = AnimationUtils.loadAnimation(view1.getContext(), R.anim.slide_out_left);
        recycler = (RecyclerView)view1.findViewById(R.id.recycleWait);
        recycler.startAnimation(slideinl);
        apiInterface = ServiceBuilder.buildRequest().create(Apiinterface.class);
        RecyclerAdapter.OnStateClickListener stateClickListener = new RecyclerAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Film film, int position, View v) {
                Intent intent = new Intent(view1.getContext(), FilmDetails.class);
                intent.putExtra("id", film.getFilmId());
                startActivity(intent);

            }
        };

        Call<Root> page = apiInterface.getPageId();

        page.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()){
                    recycler.setLayoutManager(new LinearLayoutManager(view1.getContext()));
                    recycler.setHasFixedSize(true);
                    Root page = response.body();
                    RecyclerAdapter adapter = new RecyclerAdapter(view1.getContext(), page.getFilms(), stateClickListener);
                    recycler.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(view1.getContext(), "Мдааа, ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(view1.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view1;
    }
}
