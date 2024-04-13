package com.example.finalproject.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mSongsList;
    private List<String> mPerformersList;

    public SongsAdapter(Context context, List<String> songsList, List<String> performersList) {
        mContext = context;
        mSongsList = songsList;
        mPerformersList = performersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songNameTextView.setText(mSongsList.get(position));
        holder.performerTextView.setText(mPerformersList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView;
        TextView performerTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.songNameTextView);
            performerTextView = itemView.findViewById(R.id.performerTextView);
        }
    }
}

