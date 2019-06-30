package com.google.codelabs.appauth.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.wallet.Cart;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.CartItem;

import java.util.List;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.cartItemViewHolder>{

private List<CartItem> cartItemList;

    public CartItemAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public cartItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.cart_item,viewGroup,false);
        cartItemViewHolder viewHolder= new  cartItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cartItemViewHolder cartItemViewHolder, int i) {
        //get item position
        CartItem Item=cartItemList.get(i);

        //get the views and set them
        TextView tVName=cartItemViewHolder.tVName;
        TextView tvDescription=cartItemViewHolder.tvDescription;
        TextView tVPrice=cartItemViewHolder.tVPrice;
        TextView tVQuantity=cartItemViewHolder.tVQuantity;
        ImageView iVPicture=cartItemViewHolder.iVPicture;

        //set click listener here
        tVName.setText(Item.getName());
        tvDescription.setText(Item.getDescription());
        tVPrice.setText(Item.getCost());
        tVQuantity.setText(Item.getAmount());
        iVPicture.setImageResource(Item.getImageUrl());



    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public  void removeItem(int position){
        notifyItemRemoved(position);
    }

    public void restoreItem(CartItem item, int position){
        cartItemList.add(position,item);
        notifyItemInserted(position);

    }

    public class cartItemViewHolder extends RecyclerView.ViewHolder {
        //initialize the views here
        TextView tVName;
        TextView tvDescription;
        TextView tVPrice;
        TextView tVQuantity;
        ImageView iVPicture;
      public View viewForeground;
       public View viewBackground;


        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tVName=itemView.findViewById(R.id.tVName);
            tvDescription=itemView.findViewById(R.id.tvDescription);
            tVPrice=itemView.findViewById(R.id.tVPrice);
            tVQuantity=itemView.findViewById(R.id.tVQuantity);
            iVPicture=itemView.findViewById(R.id.iVPicture);
            viewForeground=itemView.findViewById(R.id.view_foreground);
            viewBackground=itemView.findViewById(R.id.view_background);



        }
    }


}
