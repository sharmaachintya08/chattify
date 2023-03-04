package com.example.chatroom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatroom.R;
import com.example.chatroom.model.ChatModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Objects;

public class ChatAdapter extends FirestoreRecyclerAdapter<ChatModel,ChatAdapter.ChatViewHolder> {


    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatModel model) {
        holder.message.setText(model.getMessage());
        Glide.with(holder.user_image.getContext().getApplicationContext())
                .load(model.getUser_image_url())
                .into(holder.user_image);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ChatViewHolder(v);
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView message ;
        ImageView user_image;

        public ChatViewHolder(@NonNull View itemView) {

            super(itemView);
            message = itemView.findViewById(R.id.message);
            user_image = itemView.findViewById(R.id.user_image);
        }
    }
}
