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


        unbinder = ButterKnife.bind(this, view);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UploadProductActivity.class);
            startActivity(intent);
        });


        return view;
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

}
