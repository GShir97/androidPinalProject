package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class clubFragment extends Fragment {


    private Spinner clubSpinner;
    private Button showClubButton;
    private TextView clubAgeTextView, clubDJTextView, clubSongTextView, clubOpenTextView, clubAddressTextView;

    private DatabaseReference clubsRef;
    private List<String> clubNamesList;
    private ArrayAdapter<String> spinnerAdapter;
    private String selectedClubName;
    private LinearLayout titles;


    public clubFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_club, container, false);

        clubSpinner = view.findViewById(R.id.clubsSpinner);
        showClubButton = view.findViewById(R.id.showClubButton);
        clubDJTextView = view.findViewById(R.id.DJView);
        clubSongTextView = view.findViewById(R.id.songView);
        clubOpenTextView = view.findViewById(R.id.openView);
        clubAddressTextView = view.findViewById(R.id.addressView);
        clubAgeTextView = view.findViewById(R.id.ageView);
        titles = view.findViewById(R.id.titlesLayout);

        clubsRef = FirebaseDatabase.getInstance().getReference().child("clubs");

        clubNamesList = new ArrayList<>();

        retrieveClubNames();
        clubSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClubName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        showClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveClubDetails(selectedClubName);
                titles.setVisibility(View.VISIBLE);
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
                            clubAgeTextView.setText(club.getAge());
                            clubDJTextView.setText(club.getDJ());
                            clubSongTextView.setText(club.getSong());
                            clubOpenTextView.setText(club.getOpen());
                            clubAddressTextView.setText(club.getAddress());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}