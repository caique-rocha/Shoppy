package com.google.codelabs.appauth.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.codelabs.appauth.fragments.AllCategoriesFragment;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.interfaces.FragmentCommunication;
import com.google.codelabs.appauth.models.MainCategory;


import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.mainCategoryViewHolder> {
    private LayoutInflater inflater;
    List<MainCategory> mainCategoryList;
    Context context;
   public FragmentCommunication mFragmentCommunication;

    public MainCategoryAdapter(List<MainCategory> mainCategoryList,
                               Context context, FragmentCommunication mFragmentCommunication) {
        this.mainCategoryList = mainCategoryList;
        inflater = LayoutInflater.from(context);
        this.mFragmentCommunication = mFragmentCommunication;
    }

    @NonNull
    @Override
    public mainCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = inflater.inflate(R.layout.main_category_item, viewGroup, false);
        mainCategoryViewHolder mainCategoryView = new mainCategoryViewHolder(view, mFragmentCommunication);
        return mainCategoryView;
    }

    @Override
    public void onBindViewHolder(@NonNull mainCategoryViewHolder mainCategoryViewHolder, int i) {
        MainCategory mainCategory = mainCategoryList.get(i);
        TextView mTextViewCategory;
        FloatingActionButton view;
        mTextViewCategory = mainCategoryViewHolder.category;
        view = mainCategoryViewHolder.view;

        mTextViewCategory.setText(mainCategory.getLabel());
        view.setBackgroundTintList(ColorStateList.valueOf(mainCategory.getColor()));
        view.setImageResource(mainCategory.getIcon());

        AllCategoriesFragment allCategoriesFragment = new AllCategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", mainCategory.getLabel());
        allCategoriesFragment.setArguments(bundle);

    }


    @Override
    public int getItemCount() {
        return mainCategoryList.size();
    }

    class mainCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mButtonCategory;
        TextView category;
        FloatingActionButton view;
        FragmentCommunication fragmentCommunication;

        public mainCategoryViewHolder(@NonNull View itemView, FragmentCommunication mCommunicator) {
            super(itemView);
            category = itemView.findViewById(R.id.text_view_category);
            view = itemView.findViewById(R.id.image_button_view);
            fragmentCommunication = mCommunicator;
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            fragmentCommunication.respond(getAdapterPosition(),mainCategoryList.get(getAdapterPosition()).getLabel());
        }
    }
}
