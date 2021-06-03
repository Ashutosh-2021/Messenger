package com.example.sociania_messenger.Adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sociania_messenger.Models.MessageModel;
import com.example.sociania_messenger.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    // Create ArrayList to Store the Message
    ArrayList<MessageModel> messageModels;
    Context context;
    // Sender And Receiver Id
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    // Create a Constructor
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Identify the view  type
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Find the type receiver or sender
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Get the Sender And Receiver Message
        MessageModel messageModel = messageModels.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            // Show the Message
            ((SenderViewHolder) holder).senderText.setText(messageModel.getMessage());
            // Show the Time
            String time = DateUtils.formatDateTime(context, messageModel.getTimesTemp(), DateUtils.FORMAT_SHOW_TIME);
            ((SenderViewHolder) holder).senderTime.setText(time);
        } else {
            // Show thw Message
            ((ReceiverViewHolder) holder).receiverText.setText(messageModel.getMessage());
            // Show the Time
            String time = DateUtils.formatDateTime(context, messageModel.getTimesTemp(), DateUtils.FORMAT_SHOW_TIME);
            ((ReceiverViewHolder) holder).receiverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        // get the message count
        return messageModels.size();
    }

    // Crate ReceiverViewHolder inner class
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        // Crate textView
        TextView receiverText, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverText = itemView.findViewById(R.id.reciverText);
            receiverTime = itemView.findViewById(R.id.reciverTime);

        }
    }

    // Crate SenderViewHolder inner class
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        // Crate textView
        TextView senderText, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);

        }
    }
}
