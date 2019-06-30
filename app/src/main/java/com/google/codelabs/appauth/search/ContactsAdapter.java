package com.google.codelabs.appauth.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Contact;

import java.util.List;

/**
 * Created by Ibrah on  6/3/2019.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    List<Contact> contactList;
    Context context;
    private ContactsAdapterListener listener;

    public ContactsAdapter(List<Contact> contactList, Context context, ContactsAdapterListener listener) {
        this.contactList = contactList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_row_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Contact contact=contactList.get(i);
        myViewHolder.name.setText(contact.getName());
        myViewHolder.phone.setText(contact.getPhone());

        Glide.with(context)
                .load(contact.getProfileImage())
                .apply(RequestOptions.circleCropTransform())
                .into(myViewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            name=itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(v->{
                // send selected contact in callback
                listener.onContactSelected(contactList.get(getAdapterPosition()));
            });
        }
    }

    public interface  ContactsAdapterListener{
        void onContactSelected(Contact contact);
    }
}
