package com.google.codelabs.appauth.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Review;

import java.util.List;

public class ShopReviewAdapter extends  RecyclerView.Adapter<ShopReviewAdapter.ShopReviewViewHolder>  {
    private List<Review> mReviewList;
    Review mReview;

    public ShopReviewAdapter(List<Review> mReviewList, Review mReview) {
        this.mReviewList = mReviewList;
        this.mReview = mReview;
    }

    @NonNull
    @Override
    public ShopReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.review_item,null);
        ShopReviewViewHolder holder=new ShopReviewViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopReviewViewHolder shopReviewViewHolder, int i) {
        mReview=mReviewList.get(i);
        RatingBar ratingBar=shopReviewViewHolder.mRatingBar;
        TextView mMessage=shopReviewViewHolder.mMessage;

        mMessage.setText(mReview.getReviewMessage());
        ratingBar.setRating(mReview.getReviewStars());

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ShopReviewViewHolder extends RecyclerView.ViewHolder{
      RatingBar mRatingBar;
     TextView mMessage,mTimeStamp,mSender;

        ShopReviewViewHolder(@NonNull View itemView) {
           super(itemView);
           mRatingBar=itemView.findViewById(R.id.review_rating);
           mMessage=itemView.findViewById(R.id.review_message);
           mSender=itemView.findViewById(R.id.review_sender);
           mTimeStamp=itemView.findViewById(R.id.review_date);
       }
   }
}
