package com.example.sociania_messenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sociania_messenger.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        // Use the thread because the Activity are call on or many time
        Thread thread = new Thread() {
            // Call the run Function

            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // User Login Condition Check
                    if (auth.getCurrentUser() != null) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                        finish();
                    }

                }
            }
        };thread.start();


    }
}