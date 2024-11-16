package com.example.sqliteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Footballer> footballerList;

    public RecyclerViewAdapter(Context context, ArrayList<Footballer> footballerList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.footballerList = footballerList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.footballer_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Footballer footballer = footballerList.get(position);
        holder.footballerFirstName.setText(footballer.getFootballer_first_name());
        holder.footballerLastName.setText(footballer.getFootballer_last_name());
        holder.footballClub.setText(footballer.getFootball_club());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewInterface.onItemClick(footballer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return footballerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView footballerFirstName, footballerLastName, footballClub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            footballerFirstName = itemView.findViewById(R.id.f_first_name);
            footballerLastName = itemView.findViewById(R.id.f_last_name);
            footballClub = itemView.findViewById(R.id.f_club);
        }
    }
}
