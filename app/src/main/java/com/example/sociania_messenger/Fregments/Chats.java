package com.example.sociania_messenger.Fregments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sociania_messenger.Adapters.UsersAdapter;
import com.example.sociania_messenger.Models.User;
import com.example.sociania_messenger.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats extends Fragment {


    // Add binding
    FragmentChatsBinding binding;
    ArrayList<User> list = new ArrayList<User>();
    FirebaseDatabase database;

    public Chats() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the fragmentChatsBinding in  Binding
        binding = FragmentChatsBinding.inflate(inflater, container, false);

        // Set Adapter
        UsersAdapter usersAdapter = new UsersAdapter(list, getContext());
        // set the adapter in recycleView
        binding.chatsRecycler.setAdapter(usersAdapter);
        // Create a Liner Layout Manager and create a Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatsRecycler.setLayoutManager(linearLayoutManager);
        // Instance In Firebase Database
        database = FirebaseDatabase.getInstance();

        // Get the Data In firebase database
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the List
                list.clear();
                // Get the data and show thw data in Snapshot
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Get the data In Users Class
                    User user = dataSnapshot.getValue(User.class);
                    // Find the data In GetId
                    user.setUserId(dataSnapshot.getKey());
                    // Login User Is not Show the recycle View
                    if (!user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                    list.add(user);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}