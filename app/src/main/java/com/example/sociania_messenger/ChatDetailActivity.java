package com.example.sociania_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sociania_messenger.Adapters.ChatAdapter;
import com.example.sociania_messenger.Models.MessageModel;
import com.example.sociania_messenger.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the Navigation Bar
        getSupportActionBar().hide();
        // Add Binding
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instantiate Firebase Object
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // String senderId are global so we can use the final keyword
        final String senderId  = auth.getUid();
        String recieveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profile_pic = getIntent().getStringExtra("profile_pic");


        // Set the UserName And Profile Pic in Activity
        binding.userName.setText(userName);
        Picasso.get().load(profile_pic).placeholder(R.drawable.ic_user).into(binding.profileImage);

        // Move to Main Activity
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Get the Message In app
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        // Call the ChatAdapter
        ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        // Set the Adapter in Recycle View
        binding.chatsRecycler.setAdapter(chatAdapter);

        // Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatsRecycler.setLayoutManager(linearLayoutManager);

        // Add Sender Id and Receiver Id and Store the Value in global Variable
        final String SenderRoot = senderId + recieveId;
        final String ReceiverRoot = recieveId + senderId;

        // Show the Value In RecycleView
        database.getReference().child("chats").child(SenderRoot).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the List because the loop are show the data in multiple time
                messageModels.clear();
                // Get the Multiple Data in FireBase Using foreach loop
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // Show the Data
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Send Button Activity
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Message in user
                String message = binding.etMessage.getText().toString();
                // store the message in sender id
                final MessageModel model = new MessageModel(senderId, message);
                // Find date and time and set the model
                model.setTimesTemp(new Date().getTime());
                // set default text is empty
                binding.etMessage.setText("");


                /*
                * Create Database child Node
                * The name of node is chats
                * And after create chats node to create a inner node
                * The inner node is known as SenderRoot
                * after create inner node call push method
                * The push method are create the automatic node in separate Message
                * after push to set the Value in model*/

                // Implement the above process In Sender
                database.getReference().child("chats").child(SenderRoot).push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Implement the above process In Receiver
                        database.getReference().child("chats").child(ReceiverRoot)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }
        });

    }
}