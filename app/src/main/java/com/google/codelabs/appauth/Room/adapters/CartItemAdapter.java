package com.google.codelabs.appauth.Room.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.models.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter  extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder>{

    private List<CartEntity> cartItemList;

    public CartItemAdapter(List<CartEntity> cartItemList) {
        this.cartItemList = cartItemList;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item,parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
     //get item position

        //get the views and set them
        TextView tVName = holder.tVName;
        TextView tvDescription = holder.tvDescription;
        TextView tVPrice = holder.tVPrice;
        TextView tVQuantity = holder.tVQuantity;
        ImageView iVPicture = holder.iVPicture;

        //set click listener here
        if (cartItemList !=null) {
            CartEntity Item = cartItemList.get(position);
            tVName.setText(Item.getmName());
            tvDescription.setText(Item.getmCategory());
            tVPrice.setText(Item.getmPrice());
            tVQuantity.setText(String.valueOf(1));
            Picasso.get()
                    .load(Item.getmImage())
                    .into(iVPicture);

        }

    }

  public  void setmCart(List<CartEntity> cart){
        cartItemList=cart;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (cartItemList !=null) {
            return cartItemList.size();
        }
        return 0;
    }

    public void removeItem(int position) {
        cartItemList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(CartEntity item, int position) {
        cartItemList.add(position, item);
        notifyItemInserted(position);

    }

   public class  CartViewHolder extends RecyclerView.ViewHolder{
        TextView tVName;
        TextView tvDescription;
        TextView tVPrice;
        TextView tVQuantity;
        ImageView iVPicture;
        public View viewForeground;
        public View viewBackground;

        public CartViewHolder(@NonNull View itemView) {
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
