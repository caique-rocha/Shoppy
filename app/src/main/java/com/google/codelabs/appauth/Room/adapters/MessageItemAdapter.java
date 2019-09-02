package com.google.codelabs.appauth.Room.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Room.entities.MessageEntity;

import java.util.List;

public class MessageItemAdapter extends
        RecyclerView.Adapter<MessageItemAdapter.MessageItemViewHolder> {

    List<MessageEntity> messageEntities;

    public MessageItemAdapter(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }

    @NonNull
    @Override
    public MessageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.message_item,parent,false);
        return new MessageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemViewHolder holder, int position) {
        if (messageEntities.size() !=0) {
            MessageEntity messageEntity=messageEntities.get(position);
            String sender=messageEntity.getMessageSender();
            String initials=String.valueOf(sender.charAt(0));
            holder.mMessageSender.setText(messageEntity.getMessageSender());
            holder.mMessageText.setText(messageEntity.getMessageText());
            holder.mMessageSenderInitials.setText(initials);
            holder.mMessageTimestamp.setText(messageEntity.getMessageTimestamp());
        }
    }

    public  void setmCart(List<MessageEntity> cart){
        messageEntities=cart;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageEntities.size();
    }

    public class MessageItemViewHolder extends RecyclerView.ViewHolder {
        TextView mMessageSender;
        TextView mMessageSenderInitials;
        TextView mMessageText;
        TextView mMessageTimestamp;

        public MessageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessageSender = itemView.findViewById(R.id.tvSenderName);
            mMessageSenderInitials = itemView.findViewById(R.id.tvSenderInitials);
            mMessageText = itemView.findViewById(R.id.tvText);
            mMessageTimestamp = itemView.findViewById(R.id.tvTimestamp);

        }

    }
}
