package com.example.socialmediaapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialmediaapp_1.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameEntered = binding.registerActivityUsernameEv.getText().toString();
                String emailEntered = binding.registerActivityEmailEv.getText().toString();
                String passwordEntered = binding.registerActivityPasswordEv.getText().toString();

                if(TextUtils.isEmpty(usernameEntered) || TextUtils.isEmpty(emailEntered) || TextUtils.isEmpty(passwordEntered)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.empty_field_message), Toast.LENGTH_SHORT).show();
                } else if(passwordEntered.trim().length() < 6) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.short_password), Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(usernameEntered, emailEntered, passwordEntered);
                }
            }
        });

        binding.goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(String usernameEntered, String emailEntered, String passwordEntered) {
        // TODO: Check if email is already being used
        auth.createUserWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnCompleteListener(RegisterActivity.this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("username", usernameEntered);
                            map.put("email", emailEntered);
                            map.put("password", passwordEntered);
                            map.put("Uid", auth.getUid());

                            dbRef.child(auth.getUid())
                                    .child("Info")
                                    .setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "onComplete: " + "User data added");
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            // Set flags to make sure user can't go back
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error in registering user", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onComplete: " + task.getResult().toString());
                        }
                    }
                });
    }
}