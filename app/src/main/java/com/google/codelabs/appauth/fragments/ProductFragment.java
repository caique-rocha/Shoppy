package com.google.codelabs.appauth.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.MainActivity;
import com.google.codelabs.appauth.models.Review;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProductFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    Unbinder unbinder;

    @BindView(R.id.constraint_share_item)
    ConstraintLayout mShareProduct;

   private ImageButton mAddToCart;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_product, container, false);
        unbinder= ButterKnife.bind(this,view);
       mAddToCart=view.findViewById(R.id.add_to_cart_button);


       mAddToCart.setOnClickListener(v -> {
           TextView mCartCount=getActivity().findViewById(R.id.cart_count);
           int count=Integer.valueOf(mCartCount.getText().toString());
           mCartCount.setText(String.format(Locale.ENGLISH,"%d",count+1));           AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
           builder.setTitle("Successfully Added to Cart...".concat("Continue Shopping?"));

           builder.setPositiveButton(android.R.string.yes,((dialog, which) -> {
               Intent intent=new Intent(getActivity(), MainActivity.class);
               startActivity(intent);
           }));
           builder.setNegativeButton("Checkout",((dialog, which) ->{
               Snackbar.make(view,"To CheckOut",Snackbar.LENGTH_SHORT).show();
               Intent intent=new Intent(getActivity(),MainActivity.class);
               startActivity(intent);
               dialog.cancel();
           } ));
           builder.show();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
