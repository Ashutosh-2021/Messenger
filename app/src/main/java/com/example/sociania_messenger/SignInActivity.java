package com.example.sociania_messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sociania_messenger.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Crate Object of binding
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Action Bar Hide
        getSupportActionBar().hide();
        // Firebase auth Instance
        auth = FirebaseAuth.getInstance();
        // ProgressDialog Instance
        progressDialog = new ProgressDialog(SignInActivity.this);
        // Add Title in ProgressDialog
        progressDialog.setTitle("Login.");
        // Add message in ProgressDialog
        progressDialog.setMessage("Login your account..");


        // TextView Operation move to second activity
        binding.tvClickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Sign In Button Of Sign In app
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the all Information are fill or not
                if (binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Please Enter the Email ");
                    return;
                }
                if (binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Please Enter the Password ");
                    return;
                }
                // Show the progressDialog
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Dismiss the progressDialog
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Go In the Next Activity
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Show the error Message
                                    Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        // User Login Condition Check
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}