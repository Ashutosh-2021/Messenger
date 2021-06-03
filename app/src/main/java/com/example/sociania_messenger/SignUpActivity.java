package com.example.sociania_messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sociania_messenger.Models.User;
import com.example.sociania_messenger.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/*
 * Developer - Ashutosh Soni
 */

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseDatabase database;
    // Add Loading View
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Binding Object
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Action Bar Hide
        getSupportActionBar().hide();
        // Firebase Auth Instance
        mAuth = FirebaseAuth.getInstance();
        // FirebaseDataBase Instance
        database = FirebaseDatabase.getInstance();
        // ProgressDialog Instance
        progressDialog = new ProgressDialog(SignUpActivity.this);
        // Add Title in ProgressDialog
        progressDialog.setTitle("Create Account..");
        // Add message in ProgressDialog
        progressDialog.setMessage("We are creating your account..");

        // Go In the Next Activity
        binding.tvAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // SignUp With Email and Password
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the Condition the all Information are fill
                if(binding.etUserName.getText().toString().isEmpty()){
                    binding.etUserName.setError("Please Enter the Username ");
                    return;
                }
                if(binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Please Enter the Email ");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Please Enter the Password ");
                    return;
                }
                // Show the ProgressDialog
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Dismiss the ProgressDialog
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // User Information add
                                    User user = new User(binding.etUserName.getText().toString(), binding.etEmail.getText().toString(),
                                             binding.etPassword.getText().toString());
                                    // Identify the user using id in firebase
                                    String id = task.getResult().getUser().getUid();
                                    // User data save in database in different root User->id->Information
                                    database.getReference().child("User").child(id).setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Successfully Login ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}