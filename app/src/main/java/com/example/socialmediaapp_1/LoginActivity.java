package com.example.socialmediaapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialmediaapp_1.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntered = binding.loginActivityEmailEv.getText().toString();
                String passwordEntered = binding.loginActivityPasswordEv.getText().toString();

                if(TextUtils.isEmpty(emailEntered) || TextUtils.isEmpty(passwordEntered)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.empty_field_message), Toast.LENGTH_SHORT).show();
                } else if(passwordEntered.trim().length() < 6) {
                    Toast.makeText(LoginActivity.this, getString(R.string.short_password), Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailEntered, passwordEntered);
                }
            }
        });

        binding.goToSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String emailEntered, String passwordEntered) {
        auth.signInWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Logged user in successfully");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //Set flags to make sure user can't go back
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Error in logging in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //Set flags to make sure user can't go back
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}