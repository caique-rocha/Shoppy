package com.google.codelabs.appauth.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.ProfileBasic;

import java.util.List;

public class ProfileBasicAdapter extends RecyclerView.Adapter<ProfileBasicAdapter.profileBasicAdapterViewHolder> {
    List<ProfileBasic> profileBasicList;

    public ProfileBasicAdapter(List<ProfileBasic> profileBasicList) {
        this.profileBasicList = profileBasicList;
    }

    @NonNull
    @Override
    public profileBasicAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.profile_basic_item,viewGroup,false);
        profileBasicAdapterViewHolder adapterViewHolder=new profileBasicAdapterViewHolder(view);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull profileBasicAdapterViewHolder profileBasicAdapterViewHolder, int i) {
        ProfileBasic mprofileBasic=profileBasicList.get(i);
        ImageView imageView=profileBasicAdapterViewHolder.imageView;
        TextView mTextViewname=profileBasicAdapterViewHolder.mTextViewname;

        imageView.setImageResource(mprofileBasic.getIcon());
        mTextViewname.setText(mprofileBasic.getName());


    }

    @Override
    public int getItemCount() {
        return profileBasicList.size();
    }


    class profileBasicAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mTextViewname;

        public profileBasicAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_profile_basic);
            mTextViewname=itemView.findViewById(R.id.text_profile_basic);

        }
    }
}
