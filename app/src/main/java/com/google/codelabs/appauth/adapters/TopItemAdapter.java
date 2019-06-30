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
import com.google.codelabs.appauth.interfaces.ItemClickListener;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.models.TopItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopItemAdapter extends RecyclerView.Adapter<TopItemAdapter.TopItemViewHolder> {
    private List<Product> topItemModelList;
    ItemClickListener mItemClickListener;


    public TopItemAdapter(List<Product> topItemModelList, ItemClickListener mItemClickListener) {
        this.topItemModelList = topItemModelList;
        this.mItemClickListener=mItemClickListener;
    }

    @NonNull
    @Override
    public TopItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.top_item,viewGroup,false);
        TopItemViewHolder topItemViewHolder=new TopItemViewHolder(view,mItemClickListener);
        return  topItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopItemViewHolder topItemViewHolder, int i) {
        Product topItemModel=topItemModelList.get(i);
        Picasso.get().load(topItemModel.getProductImage())
                .into(topItemViewHolder.ivProduct);

        TextView name=topItemViewHolder.tvProduct;
        name.setText(topItemModel.getProductName());

        TextView price =topItemViewHolder.tvPrice;
        price.setText(("$").concat(topItemModel.getProductPrice().toString()));

    }

    @Override
    public int getItemCount() {
        return topItemModelList.size();
    }

    class TopItemViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
       ImageView ivProduct;
       TextView tvProduct;
       TextView tvPrice;
       ItemClickListener itemClickListener;


       public TopItemViewHolder(@NonNull View itemView,ItemClickListener itemClickListener1) {
           super(itemView);
           ivProduct=itemView.findViewById(R.id.topImage);
           tvProduct=itemView.findViewById(R.id.topName);
           tvPrice=itemView.findViewById(R.id.topPrice);
           itemClickListener=itemClickListener1;
           itemView.setOnClickListener(this);

       }

        @Override
        public void onClick(View v) {
itemClickListener.respond(getAdapterPosition(),
        topItemModelList.get(getAdapterPosition()).getProductName(),
        topItemModelList.get(getAdapterPosition()).getProductPrice(),
        topItemModelList.get(getAdapterPosition()).getProductImage()

        );
        }
    }
}
