package com.google.codelabs.appauth.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Latest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {
    public TopRatedAdapter(List<Latest> mTopList) {
        this.mTopList = mTopList;
    }

    List<Latest> mTopList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_latest, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Latest latest =mTopList.get(i);
        ImageView topRatedImage=viewHolder.imageViewLatest;
//        topRatedImage.setImageBitmap(latest.getImage());
        Picasso.get().load(latest.getImage())
                .into(topRatedImage);

    }

    @Override
    public int getItemCount() {
        return mTopList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLatest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLatest = itemView.findViewById(R.id.image_latest);

        }
    }
}
