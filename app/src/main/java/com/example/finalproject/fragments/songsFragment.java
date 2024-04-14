package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class songsFragment extends Fragment {

    public songsFragment() {
        // Required empty public constructor
    }

    private Spinner clubSpinner;
    private TextView clubDjTextView, clubDjTitle;
    private RadioButton likeButton;
    private DatabaseReference clubsRef;
    private List<String> clubNamesList;
    private ArrayAdapter<String> spinnerAdapter;
    private DatabaseReference djRef;
    private DatabaseReference djLikesRef;
    private String DjName;
    private boolean isButtonPressed = false;
    private  Button requestButton;
    private LinearLayout songs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        clubSpinner = view.findViewById(R.id.songClubSpinner);
        clubDjTextView = view.findViewById(R.id.playDjView);
        likeButton = view.findViewById(R.id.likeButton);
        clubDjTitle = view.findViewById(R.id.playDj);

        requestButton = view.findViewById(R.id.requestButton);
        songs = view.findViewById(R.id.songs);

        clubsRef = FirebaseDatabase.getInstance().getReference().child("clubs");
        djRef = FirebaseDatabase.getInstance().getReference().child("dj").child("name");

        clubNamesList = new ArrayList<>();
        retrieveClubNames();

        clubSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClubName = parent.getItemAtPosition(position).toString();
                retrieveClubDetails(selectedClubName);

                if(isButtonPressed)
                {
                    likeButton.setChecked(false);
                    likeButton.setBackgroundResource(R.drawable.baseline_favorite_24);
                }
                clubDjTitle.setVisibility(View.GONE);
                likeButton.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isButtonPressed) {
                    isButtonPressed = true;
                    likeButton.setBackgroundResource(R.drawable.baseline_favorite_red_24);
                    incrementLikesForDJ(clubDjTextView.getText().toString());
                } else {
                    isButtonPressed = false;
                    likeButton.setBackgroundResource(R.drawable.baseline_favorite_24);
                    decrementLikesForDJ(clubDjTextView.getText().toString());
                }

            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songName = ((EditText) view.findViewById(R.id.songNameEdit)).getText().toString();
                String performerName = ((EditText) view.findViewById(R.id.performerEdit)).getText().toString();

                if (songName.isEmpty() || performerName.isEmpty()) {

                    Toast.makeText(getContext(), "Please enter song name and performer.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!DjName.isEmpty() && !songName.isEmpty() && !performerName.isEmpty()) {
                    DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference().child("songs");
                    DatabaseReference newSongRef = songsRef.push();

                    newSongRef.child("songName").setValue(songName);
                    newSongRef.child("performerName").setValue(performerName);


                    ((EditText) view.findViewById(R.id.songNameEdit)).setText("");
                    ((EditText) view.findViewById(R.id.performerEdit)).setText("");

                    Toast.makeText(getContext(), "Song Requested Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
                    });
        return view;
    }

    private void retrieveClubNames() {
        clubsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubNamesList.add("Select a club");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String clubName = dataSnapshot.child("name").getValue(String.class);
                    if (clubName != null) {
                        clubNamesList.add(clubName);
                    }
                }
                spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, clubNamesList);
                clubSpinner.setAdapter(spinnerAdapter);
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void retrieveClubDetails(String clubName) {
        clubsRef.orderByChild("name").equalTo(clubName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Club club = dataSnapshot.getValue(Club.class);
                        if (club != null) {
                            DjName = club.getDJ();
                            clubDjTextView.setText(DjName);
                            clubDjTitle.setVisibility(View.VISIBLE);
                            songs.setVisibility(View.VISIBLE);
                            likeButton.setChecked(false);
                            if (!"None".equals(club.getDJ())) {
                                likeButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void incrementLikesForDJ(String djName) {
        djLikesRef = FirebaseDatabase.getInstance().getReference().child("dj").child(djName).child("likes");
        djLikesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long likes = snapshot.getValue(Long.class);
                    if (likes != null) {
                        djLikesRef.setValue(likes + 1);
                    }
                } else {
                    djLikesRef.setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void decrementLikesForDJ(String djName) {
        djLikesRef = FirebaseDatabase.getInstance().getReference().child("dj").child(djName).child("likes");
        djLikesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long likes = snapshot.getValue(Long.class);
                    if (likes != null && likes > 0) {
                        djLikesRef.setValue(likes - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

    }
}