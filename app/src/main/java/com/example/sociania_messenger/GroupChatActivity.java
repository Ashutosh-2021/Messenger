package com.example.sociania_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sociania_messenger.Adapters.ChatAdapter;
import com.example.sociania_messenger.Models.MessageModel;
import com.example.sociania_messenger.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    // Create A binding
    ActivityChatDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create binding object
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hide the navigation bar
        getSupportActionBar().hide();

        // back arrow image
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move the GroupActivity to MainActivity
                startActivity(new Intent(GroupChatActivity.this, MainActivity.class));
                finish();
            }
        });

        // Create FireBase Database Object in Global and after create object we can instance the object
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Get the Sender Id in Firebase
        final String SenderId = FirebaseAuth.getInstance().getUid();
        // Get the Default Name
        binding.userName.setText("Public Chat");

        // Create final ArrayList
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        // Create Final in ChatAdapter
        ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        // Set the Adapter in Recycle View
        binding.chatsRecycler.setAdapter(chatAdapter);

        // Create linear Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // set the Layout in chatRecycleView
        binding.chatsRecycler.setLayoutManager(layoutManager);


        // Get the Data in database and Show the data on app
        database.getReference().child("Group")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Clear the Message
                        messageModels.clear();
                        // Get the Message In one By one
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Send Image button
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the Message in editText
                final String message = binding.etMessage.getText().toString();
                // Set the Sender Id and message on MessageModel
                final MessageModel model = new MessageModel(SenderId, message);
                // Get the Date and Time on Message
                model.setTimesTemp(new Date().getTime());
                // Set Default Text
                binding.etMessage.setText("");

                // Step 1:  Create Database  Node and the Name of Node is Group
                // Step 2: After Create Node we can push the Message
                // Step 3: after push operation we can set the Value

                // All step
                database.getReference().child("Group")
                        .push()
                        .setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });


            }
        });
    }
}