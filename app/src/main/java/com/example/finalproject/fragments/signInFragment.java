package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.activities.SecActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;



public class signInFragment extends Fragment {

    private FirebaseAuth mAuth;


    public signInFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        Button signInButton = view.findViewById(R.id.buttonLogin);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) view.findViewById(R.id.textEmailAddress)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.textPassword)).getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        String userId = user.getUid();
                                        checkDJStatus(userId, new OnDjStatusCheckedListener() {
                                            @Override
                                            public void onDjStatusChecked(boolean isDj) {
                                                navigateBasedOnDjStatus(isDj);
                                            }
                                        });
                                    }


                                } else {
                                    Toast.makeText(getContext(), "Signing in failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

        Button buttonSignup = (Button) view.findViewById(R.id.buttonSignup);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        return view;
    }

    private void navigateBasedOnDjStatus(boolean isDj) {
        if (isDj) {
            Navigation.findNavController(requireView()).navigate(R.id.action_signInFragment_to_djViewFragment);
        } else {
            Intent intent = new Intent(getActivity(), SecActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
    private void checkDJStatus(String userId, OnDjStatusCheckedListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        boolean isDj = user.getDj();
                        listener.onDjStatusChecked(isDj);
                    } else {
                        listener.onDjStatusChecked(false);
                    }
                } else {
                    listener.onDjStatusChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onDjStatusChecked(false);
            }
        });
    }

    public interface OnDjStatusCheckedListener {
        void onDjStatusChecked(boolean isDj);
    }

}
