package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpFragment extends Fragment {


    private FirebaseAuth mAuth;
    private CheckBox checkboxDj;
    private Bundle bundle;

    public signUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signupButton = view.findViewById(R.id.signupButton);
        checkboxDj = view.findViewById(R.id.checkboxDj);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) view.findViewById(R.id.emailSignup)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.passwordSignup)).getText().toString();
                boolean isDj = checkboxDj.isChecked();
                bundle = new Bundle();
                bundle.putString("userEmail", email);


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        saveUserData(user.getUid(), email, isDj);
                                        Toast.makeText(getContext(), "Signing up successful.", Toast.LENGTH_LONG).show();
                                        navigateBasedOnDjStatus(isDj);
                                    }
                                } else {

                                    Toast.makeText(getContext(), "Signing up failed.", Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });
        return view;
    }
        private void saveUserData(String userId, String email, boolean isDj){
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            User newUser = new User(email, isDj, userId);
            usersRef.child(userId).setValue(newUser)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Sign up successful.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error saving user data.", Toast.LENGTH_SHORT).show();
                    });
        }

    private void navigateBasedOnDjStatus(boolean isDj) {
        if (isDj) {
            // Navigate to DJ fragment
            Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_signInFragment, bundle);
        } else {
            // Navigate to home fragment
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_signUpFragment_to_homeFragment2);
        }
    }
}
