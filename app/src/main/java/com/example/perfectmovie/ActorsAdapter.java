package com.example.perfectmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.ActorsRegisers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ActorViewHolder> {
    private ArrayList<ActorsRegisers> actorsList;
    private final Context context;

    public ActorsAdapter(Context context, ArrayList<ActorsRegisers> actorsList) {
        this.actorsList = actorsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        ActorsRegisers actor = actorsList.get(position);
        if (actor.getNameRu() != null && !actor.getNameRu().isEmpty())
            holder.nameText.setText(actor.getNameRu());
        holder.professionText.setText(actor.getProfessionText());
        Picasso.with(context).load(actor.getPosterUrl()).placeholder(R.drawable.ronaldo).into(holder.posterUrl);
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    static class ActorViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView professionText;
        ImageView posterUrl;

        public ActorViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            professionText = itemView.findViewById(R.id.professionText);
            posterUrl = itemView.findViewById(R.id.actorImage);
        }
    }
}
