package com.example.perfectmovie;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.FilmInfo;
import com.example.perfectmovie.models.Trailer;
import com.example.perfectmovie.models.Video;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder>{
    private final Context context;
    private final FilmInfo film;

    public DetailsAdapter(Context context, FilmInfo film) {
        this.context = context;
        this.film = film;
    }

    @NonNull
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_card, parent, false);
        return new DetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(film.getNameRu());
        holder.details.setText(film.getDescription());
        holder.year.setText(String.valueOf(film.getYear()));
        holder.rate.setText(String.valueOf(film.getRatingImdb()));
        Picasso.with(context).load(film.getPosterUrlPreview()).placeholder(R.drawable.ronaldo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, details, year, rate;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
            year = itemView.findViewById(R.id.year);
            rate = itemView.findViewById(R.id.rate);
            imageView = (ImageView) itemView.findViewById(R.id.Photo);

        }
    }
}
