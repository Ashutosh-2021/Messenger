package com.example.sociania_messenger.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sociania_messenger.ChatDetailActivity;
import com.example.sociania_messenger.Models.User;
import com.example.sociania_messenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Extend the RecyclerView and give the class Name
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolders> {

    ArrayList<User> list;
    Context context;

    public UsersAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Show the data in View
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        User user = list.get(position);
        // Show the image in User or user not set the profile so show the Default image
        Picasso.get().load(user.getProfile_pic()).placeholder(R.drawable.ic_user).into(holder.imageView);
        // Show User Name
        holder.userName.setText(user.getUserName());


        /*
        * Show the last Message
        * create a firebase Database getReference or child
        * get the user id in firebase Database and user class
        * call the orderByChild function because show the last message only
        * after call the function you can call the another function is limitToLast
        * print the single value call the function addEventListenerSingleValue*/

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUserId())
                .orderByChild("timesTemp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Show the Message
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        holder.lastMessage.setText(dataSnapshot.child("message").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Go in the Chat Activity
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId", user.getUserId());
                intent.putExtra("profile_pic", user.getProfile_pic());
                intent.putExtra("userName", user.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        // Get the Item in List Size
        if (list != null)
            return list.size();
        else
            return 0;
    }

    // Create a viewHolder Class and hold the view data
    class ViewHolders extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName, lastMessage;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userNameList);
            lastMessage = itemView.findViewById(R.id.lastMessage);

        }
    }
}
