package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.UploadProductActivity;

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


public class ShopProductsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.fabAddProduct)
    FloatingActionButton fabAdd;
    @BindView(R.id.shop_product_fragment)
    RecyclerView recyclerView;

    private SectionedRecyclerViewAdapter sectionAdapter;

    private OnFragmentInteractionListener mListener;

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
        sectionAdapter=new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new ProductsSection(ProductsSection.APARELL));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.BEAUTY));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.ELECTRONICS));
        sectionAdapter.addSection(new ProductsSection(ProductsSection.SHOES));

        RecyclerView recyclerView=view.findViewById(R.id.shop_product_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        unbinder = ButterKnife.bind(this, view);

        fabAdd.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(), UploadProductActivity.class);
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
        final static  int BEAUTY=0;
        final static  int ELECTRONICS=1;
        final static  int APARELL=2;
        final static  int SHOES=3;

        String title;
        List<String> list;
        int imgPlaceHolderResId;

        ProductsSection(int topic){
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_ex2_item)
                    .headerResourceId(R.layout.section_ex2_header)
                    .footerResourceId(R.layout.section_ex2_footer)
                    .build());

            switch (topic) {
                case BEAUTY:
                    this.title="Beauty";
                    this.list=getProducts(R.array.products_beauty);
                    this.imgPlaceHolderResId=R.drawable.boots;
                    break;
                case ELECTRONICS:
                    this.title="Electronics";
                    this.list=getProducts(R.array.products_electronics);
                    this.imgPlaceHolderResId=R.drawable.backpack;
                    break;
                case APARELL:
                    this.title="Aparell";
                    this.list=getProducts(R.array.products_aparell);
                    this.imgPlaceHolderResId=R.drawable.scarf;
                    break;
                case SHOES:
                    this.title="Shoes";
                    this.list=getProducts(R.array.products_shoes);
                    this.imgPlaceHolderResId=R.drawable.backpack;
                    break;
            }

        }

        private List<String> getProducts(int arrayResource){
            return new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResource)));
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
            final ItemViewHolder itemHolder=(ItemViewHolder)holder;

            String [] item=list.get(position).split("\\|");

            itemHolder.tvHeader.setText(item[0]);
            itemHolder.tvDate.setText(item[1]);
            itemHolder.imgItem.setImageResource(imgPlaceHolderResId);

            itemHolder.rootView.setOnClickListener((View v)->{
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
            HeaderViewHolder headerHolder=(HeaderViewHolder)holder;
            headerHolder.tvTitle.setText(title);
        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder=(FooterViewHolder) holder;
            footerHolder.rootView.setOnClickListener((View v)->{
                Toast.makeText(getContext(),String.format("clicked on footer of Section %s",title),Toast.LENGTH_SHORT).show();
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

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        private View rootView ;
        private ImageView imgItem;
        private  TextView tvHeader;
        private  TextView tvDate;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView=itemView;
            imgItem=itemView.findViewById(R.id.imgItem);
            tvHeader=itemView.findViewById(R.id.tvHeader);
            tvDate= itemView.findViewById(R.id.tvDate);

        }
    }
}
