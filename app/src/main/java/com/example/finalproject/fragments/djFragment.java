package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


public class djFragment extends Fragment {

    private Spinner djSpinner;
    private Button showDjButton;
    private TextView djNameTextView, djSongTextView, djClubTextView, djAddressTextView;
    private DatabaseReference djRef;
    private List<String> djNamesList;
    private ArrayAdapter<String> djSpinnerAdapter;
    private String selectedDjName;
    private LinearLayout titles;



    public djFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dj, container, false);

        djSpinner = view.findViewById(R.id.djSpinner);
        showDjButton = view.findViewById(R.id.showDjButton);
        djNameTextView = view.findViewById(R.id.DJView);
        djSongTextView = view.findViewById(R.id.songView);
        djClubTextView = view.findViewById(R.id.clubView);
        djAddressTextView = view.findViewById(R.id.addressView);
        titles = view.findViewById(R.id.titlesLayout);

        djRef = FirebaseDatabase.getInstance().getReference().child("dj");

        djNamesList = new ArrayList<>();

        retrieveDjNames();
        djSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDjName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        showDjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveDjDetails(selectedDjName);
                titles.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
    private void retrieveDjNames() {
        djRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                djNamesList.add("Select a DJ");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String clubName = dataSnapshot.child("name").getValue(String.class);
                    if (clubName != null) {
                        djNamesList.add(clubName);
                    }
                }
                djSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, djNamesList);
                djSpinner.setAdapter(djSpinnerAdapter);
                djSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void retrieveDjDetails(String clubName) {
        djRef.orderByChild("name").equalTo(clubName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Dj dj = dataSnapshot.getValue(Dj.class);
                        if (dj != null) {
                            djNameTextView.setText(dj.getName());
                            djSongTextView.setText(dj.getSong());
                            djClubTextView.setText(dj.getClub());
                            djAddressTextView.setText(dj.getAddress());
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