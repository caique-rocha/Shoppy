package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.ProductDetailsActivity;
import com.google.codelabs.appauth.adapters.TopItemAdapter;
import com.google.codelabs.appauth.interfaces.ItemClickListener;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.product.ProductClient;
import com.google.codelabs.appauth.product.ProductInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoriesFragment extends Fragment {
    private static final String TAG = "AllCategoriesFragment";


    Unbinder unbinder;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.textViewCategory)
    TextView categorytv;

    String category;

    RecyclerView recyclerViewAll;

    List<?> topItemModelList = new ArrayList<>();
    TopItemAdapter topItemAdapter;


    Fragment fragment;

    private OnFragmentInteractionListener mListener;

    public AllCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewAll = view.findViewById(R.id.rvImages2);


        categorytv.setText(category);

        ProductInterface productInterface = ProductClient.getProductClient().create(ProductInterface.class);
        Call<List<Product>> call = productInterface.searchByName(category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.e(TAG, "onResponse: "+response);
                if (response.isSuccessful()) {
                    for (Product product:response.body()
                         ) {
                        Log.d(TAG, "onResponse: "+product);
                    }

                    loadDatalist(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });




        return view;
    }

    private void loadDatalist(List<Product> usersList) {
        TopItemAdapter adapter = new TopItemAdapter(usersList,mItemClickListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getActivity(), 3, RecyclerView.VERTICAL, false);
        recyclerViewAll.setLayoutManager(gridLayoutManager);
        recyclerViewAll.setNestedScrollingEnabled(false);
        recyclerViewAll.setAdapter(adapter);
    }

    ItemClickListener mItemClickListener=(int position,String id,String name,String price,String image)->{
        Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("image",image);
        startActivity(intent);

    };




    @OnClick(R.id.imageView)
    public void onClick(View v) {
        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().
                getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right
        );
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
