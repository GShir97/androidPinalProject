package com.example.finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class djViewFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    DatabaseReference djRef;
    SongsAdapter myAdapter;
    ArrayList<Songs> list;
    TextView djNameView;
    TextView likesView;
    private FirebaseAuth mAuth;
    private ImageButton logoutButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dj_view, container, false);

        recyclerView = view.findViewById(R.id.songsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        myAdapter = new SongsAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        database = FirebaseDatabase.getInstance().getReference().child("songs");

        djRef = FirebaseDatabase.getInstance().getReference().child("dj");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot djSnapshot : snapshot.getChildren()) {
                    String songName = djSnapshot.child("songName").getValue(String.class);
                    String performerName = djSnapshot.child("performerName").getValue(String.class);

                    Songs song = new Songs(songName, performerName);
                    list.add(song);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        djNameView = view.findViewById(R.id.djNameView);
        likesView = view.findViewById(R.id.likesView);

        djRef = FirebaseDatabase.getInstance().getReference().child("dj").child("Alon Deloko");
        djRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String djName = snapshot.child("djName").getValue(String.class);
                    djNameView.setText(djName);

                    Long likes = snapshot.child("likes").getValue(Long.class);
                    if (likes != null) {
                        likesView.setText(String.valueOf(likes));
                    } else {
                        likesView.setText("0");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mAuth = FirebaseAuth.getInstance();
        logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

