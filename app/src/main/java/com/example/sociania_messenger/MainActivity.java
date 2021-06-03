package com.example.sociania_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sociania_messenger.Adapters.FregmentsAdapters;
import com.example.sociania_messenger.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create Object of Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // create Firebase auth Instance
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Sociania");

        // Set the Fragment
        binding.viwPager.setAdapter(new FregmentsAdapters(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viwPager);

    }

    // Show Menu In Main Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Selected Menu Item Options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Select The Item In Menu
        switch (item.getItemId()) {

            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;

            case R.id.groupchat:
                Intent intent_1 = new Intent(MainActivity.this, GroupChatActivity.class);
                startActivity(intent_1);
                break;

            case R.id.logout:
                // Logout the app
                auth.signOut();
                // after logout move the sign in Activity
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

}