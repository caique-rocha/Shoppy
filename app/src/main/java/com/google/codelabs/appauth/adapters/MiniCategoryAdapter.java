package com.google.codelabs.appauth.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.MiniCategory;

import java.util.List;

public class MiniCategoryAdapter extends RecyclerView.Adapter<MiniCategoryAdapter.miniCategoryViewHolder> {
    List<MiniCategory> miniCategoryList;

    public MiniCategoryAdapter(List<MiniCategory> miniCategoryList) {
        this.miniCategoryList = miniCategoryList;
    }

    @NonNull
    @Override
    public miniCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.mini_category,viewGroup,false);
        miniCategoryViewHolder miniCategoryViewHolder=new miniCategoryViewHolder(view);
        return miniCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull miniCategoryViewHolder miniCategoryViewHolder, int i) {
        MiniCategory miniCategoryInstance=miniCategoryList.get(i);
         TextView textView;
         textView=miniCategoryViewHolder.subCategoryName;
         textView.setText(miniCategoryInstance.getName());

    }

    @Override
    public int getItemCount() {
        return miniCategoryList.size();
    }


    class miniCategoryViewHolder extends RecyclerView.ViewHolder{
        TextView subCategoryName;

        public miniCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryName=itemView.findViewById(R.id.textViewSubcategory);
        }
    }
}
