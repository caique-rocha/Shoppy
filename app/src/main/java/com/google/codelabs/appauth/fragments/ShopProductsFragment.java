package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.UploadProductActivity;
import com.google.codelabs.appauth.models.TopItemModel;
import com.google.codelabs.appauth.product.ProductClient;
import com.google.codelabs.appauth.product.ProductInterface;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopProductsFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.fabAddProduct)
    FloatingActionButton fabAdd;
    @BindView(R.id.shop_product_fragment)
    RecyclerView recyclerView;

    private SectionedRecyclerViewAdapter sectionAdapter;

    private OnFragmentInteractionListener mListener;

    TopItemModel topItemModel;

    public ShopProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_products, container, false);
        sectionAdapter = new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new ProductsSection(ProductsSection.APARELL));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.BEAUTY));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.ELECTRONICS));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.SHOES));

        RecyclerView recyclerView = view.findViewById(R.id.shop_product_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        unbinder = ButterKnife.bind(this, view);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UploadProductActivity.class);
            startActivity(intent);
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ProductsSection extends StatelessSection {
        final static int BEAUTY = 0;
        final static int ELECTRONICS = 1;
        final static int APARELL = 2;
        final static int SHOES = 3;

        String title;
        private List<TopItemModel> list=new ArrayList<>();
        List<TopItemModel> listone=new ArrayList<>();
        List<TopItemModel> listtwo=new ArrayList<>();
        List<TopItemModel> listthree=new ArrayList<>();
        List<TopItemModel> listfour=new ArrayList<>();
        int imgPlaceHolderResId;

        ProductInterface productInterface = ProductClient.getProductClient().create(ProductInterface.class);
        Call<List<TopItemModel>> call;

        ProductsSection(int topic) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_ex2_item)
                    .headerResourceId(R.layout.section_ex2_header)
                    .footerResourceId(R.layout.section_ex2_footer)
                    .build());

            switch (topic) {
                case BEAUTY:
                    this.title = "Beauty";

                    call = productInterface.getByLabel("Beauty");
                    call.enqueue(new Callback<List<TopItemModel>>() {
                        @Override
                        public void onResponse(Call<List<TopItemModel>> call, Response<List<TopItemModel>> response) {
                            listone = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<TopItemModel>> call, Throwable t) {

                        }
                    });
                    this.list=listone;
                    this.imgPlaceHolderResId = R.drawable.boots;
                    break;
                case ELECTRONICS:
                    this.title = "Electronics";
                    call = productInterface.getByLabel("Electronics");
                    call.enqueue(new Callback<List<TopItemModel>>() {
                        @Override
                        public void onResponse(Call<List<TopItemModel>> call, Response<List<TopItemModel>> response) {
                            listtwo = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<TopItemModel>> call, Throwable t) {

                        }
                    });
                     this.list=listtwo;
                    this.imgPlaceHolderResId = R.drawable.backpack;
                    break;
                case APARELL:
                    this.title = "Aparell";
                    call = productInterface.getByLabel("Aparell");
                    call.enqueue(new Callback<List<TopItemModel>>() {
                        @Override
                        public void onResponse(Call<List<TopItemModel>> call, Response<List<TopItemModel>> response) {
                            listthree = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<TopItemModel>> call, Throwable t) {

                        }
                    });
                    list=this.listthree;
                    this.imgPlaceHolderResId = R.drawable.scarf;
                    break;
                case SHOES:
                    this.title = "Shoes";
                    call = productInterface.getByLabel("Shoes");
                    call.enqueue(new Callback<List<TopItemModel>>() {
                        @Override
                        public void onResponse(Call<List<TopItemModel>> call, Response<List<TopItemModel>> response) {
                            listfour = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<TopItemModel>> call, Throwable t) {

                        }
                    });
                    this.list=listfour;
                    this.imgPlaceHolderResId = R.drawable.backpack;
                    break;
            }

        }


        /**
         * Create a stateless Section object based on {@link SectionParameters}.
         *
         * @param sectionParameters section parameters
         */
        public ProductsSection(SectionParameters sectionParameters) {
            super(sectionParameters);
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            topItemModel = list.get(position);
//            String [] item=list.get(position).split("\\|");

//            itemHolder.tvHeader.setText(item[0]);
//            itemHolder.tvPrice.setText(item[1]);
//            itemHolder.imgItem.setImageResource(imgPlaceHolderResId);
            itemHolder.tvHeader.setText(topItemModel.getmTopName());
            itemHolder.tvPrice.setText(topItemModel.getmTopPrice());
            Picasso.get().load(topItemModel.getmImageUrl())
                    .into(itemHolder.imgItem);


            itemHolder.rootView.setOnClickListener((View v) -> {
                Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s",
                        sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                        title),
                        Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(title);
        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.rootView.setOnClickListener((View v) -> {
                Toast.makeText(getContext(), String.format("clicked on footer of Section %s", title), Toast.LENGTH_SHORT).show();
            });

        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        FooterViewHolder(View view) {
            super(view);

            rootView = view;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private ImageView imgItem;
        private TextView tvHeader;
        private TextView tvPrice;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            imgItem = itemView.findViewById(R.id.imgItem);
            tvHeader = itemView.findViewById(R.id.tvHeader);
            tvPrice = itemView.findViewById(R.id.text_view_price);

        }
    }
}
