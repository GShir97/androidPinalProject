package com.example.finalproject.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Songs> songsList;

    public SongsAdapter(Context context, ArrayList<Songs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Songs song = songsList.get(position);
        holder.songName.setText(song.getSongName());
        holder.performer.setText(song.getSongPerformer());
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView songName, performer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.songNameTextView);
            performer = itemView.findViewById(R.id.performerTextView);
        }
    }
}

