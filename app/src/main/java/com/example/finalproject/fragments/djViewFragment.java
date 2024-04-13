package com.example.finalproject.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class djViewFragment extends Fragment {
    private String djEmail;
    private RecyclerView mRecyclerView;
    private SongsAdapter mAdapter;
    private List<String> mSongsList;
    private List<String> mPerformersList;
    private String djName;
    private TextView helloDj;

    public djViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dj_view, container, false);
        mRecyclerView = view.findViewById(R.id.songsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        helloDj = view.findViewById(R.id.helloView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            djEmail = getArguments().getString("userEmail");
            fetchDJName();
        }
    }

    private void fetchDJName() {
        DatabaseReference djRef = FirebaseDatabase.getInstance().getReference().child("dj");
        djRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot djSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot emailSnapshot = djSnapshot.child("email");
                    if (emailSnapshot.exists() && emailSnapshot.getValue(String.class).equals(djEmail)) {
                        djName = djSnapshot.child("name").toString();
                        helloDj.setText(djName);
                        fetchSongsForDJ(djName);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void fetchSongsForDJ(String djName) {
        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference().child("songs").child(djName);

        songsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSongsList = new ArrayList<>();
                mPerformersList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String songName = snapshot.child("songName").getValue(String.class);
                    String performerName = snapshot.child("performerName").getValue(String.class);

                    if (songName != null && performerName != null) {
                        mSongsList.add(songName);
                        mPerformersList.add(performerName);
                    }
                }

                mAdapter = new SongsAdapter(getContext(), mSongsList, mPerformersList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
