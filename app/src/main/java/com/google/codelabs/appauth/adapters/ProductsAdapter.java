package com.google.codelabs.appauth.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Product;

import java.util.List;

/**
 * Created by Ibrah on  6/3/2019.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    List<Product> productList;
    Context context;
    private ContactsAdapterListener listener;

    public ProductsAdapter(List<Product> contactList, Context context, ContactsAdapterListener listener) {
        this.productList = contactList;
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
        final Product product=productList.get(i);
        myViewHolder.name.setText(product.getProductName());
        myViewHolder.phone.setText(product.getProductCategory());

        Glide.with(context)
                .load(product.getProductImage())
                .apply(RequestOptions.circleCropTransform())
                .into(myViewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.price);
            thumbnail=itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(v->{
                // send selected contact in callback
                listener.onContactSelected(productList.get(getAdapterPosition()));
            });
        }
    }

    public interface  ContactsAdapterListener{
        void onContactSelected(Product product);
    }
}
