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
import com.google.codelabs.appauth.models.Latest;
import com.google.codelabs.appauth.models.MainCategory;
import com.google.codelabs.appauth.models.TopItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //items to display
    public  List<?> items;
   public static final int MENU = 1;
    public static final int LATEST = 2;
   public final int TOP_RATED = 3;
    RecyclerView recyclerView;
    Context context;

    //provide a constructor
    public ComplexRecyclerViewAdapter(List<?> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        context=viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case MENU:
                View view1 = layoutInflater.inflate(R.layout.main_category_item, viewGroup, false);
                viewHolder = new ComplexRecyclerViewAdapter.mainCategoryViewHolder(view1);

                break;
            case LATEST:
                View view2 = layoutInflater.inflate(R.layout.item_latest, viewGroup, false);
                viewHolder = new ComplexRecyclerViewAdapter.LatestViewHolder(view2);
                break;
            case TOP_RATED:
                View view3 = layoutInflater.inflate(R.layout.top_item, viewGroup, false);
                viewHolder = new ComplexRecyclerViewAdapter.TopItemViewHolder(view3);
                break;


        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case LATEST:
                LatestViewHolder viewHolder1 = (LatestViewHolder) viewHolder;
                configureViewHolder1(viewHolder1, i);
                break;
            case MENU:
                mainCategoryViewHolder viewHolder2 = (mainCategoryViewHolder) viewHolder;
                configureViewHolder2(viewHolder2, i);
//                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                break;
            case TOP_RATED:
                TopItemViewHolder viewHolder3 = (TopItemViewHolder) viewHolder;
                configureViewHolder3(viewHolder3, i);
                break;
        }

//        if (viewHolder instanceof mainCategoryViewHolder) {
//            LinearLayoutManager layoutManager=new LinearLayoutManager
//                    (context,
//                            LinearLayoutManager.HORIZONTAL,false);
//            recyclerView.setLayoutManager(layoutManager);
//        }

    }

    private void configureViewHolder3(TopItemViewHolder viewHolder3, int i) {
        TopItemModel topItemModel = (TopItemModel)items.get(i);
        Picasso.get().load(topItemModel.getmTopImage())
                .into(viewHolder3.ivProduct);

        TextView name = viewHolder3.tvProduct;
        name.setText(topItemModel.getmTopName());

        TextView price = viewHolder3.tvPrice;
        price.setText(("$").concat(topItemModel.getmTopPrice().toString()));

    }

    private void configureViewHolder2(mainCategoryViewHolder viewHolder2, int i) {
        MainCategory mainCategory = (MainCategory) items.get(i);
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

    private void configureViewHolder1(LatestViewHolder viewHolder1, int i) {
        Latest latest = (Latest) items.get(i);
        ImageView topRatedImage = viewHolder1.imageViewLatest;
//        topRatedImage.setImageResource(latest.getImage());

    }

    //return size of data set
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (items.get(position) instanceof MainCategory) {
            return MENU;
        } else if (items.get(position) instanceof Latest) {
            return LATEST;
        } else if (items.get(position) instanceof TopItemModel) {
            return TOP_RATED;
        }
        return -1;
    }


    static class mainCategoryViewHolder extends RecyclerView.ViewHolder {
        static TextView category;
        static FloatingActionButton view;
        RecyclerView recyclerView;


        mainCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.text_view_category);
            view = itemView.findViewById(R.id.image_button_view);
            recyclerView=itemView.findViewById(R.id.recyclerView);




        }


    }

    class LatestViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLatest;

        public LatestViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLatest = itemView.findViewById(R.id.image_latest);

        }
    }

    class TopItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvProduct;
        TextView tvPrice;


        TopItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.topImage);
            tvProduct = itemView.findViewById(R.id.topName);
            tvPrice = itemView.findViewById(R.id.topPrice);

        }
    }
}
