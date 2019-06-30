package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.google.android.gms.wallet.Cart;
import com.google.codelabs.appauth.Helpers.RecyclerItemTouchHelper;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.CheckOutActivity;
import com.google.codelabs.appauth.adapters.CartItemAdapter;
import com.google.codelabs.appauth.models.CartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    Unbinder unbinder;

    @BindView(R.id.checkoutButton)
    ImageButton checkOut;
    @BindView(R.id.rVCart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.constraint_layout)
    ConstraintLayout mConstraintLayout;

    //list of all items
    private List<CartItem> cartList=new ArrayList<>();

    //Adapter
    CartItemAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public CartFragment() {
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
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
         unbinder= ButterKnife.bind(this,view);
         //add a divider between recycler view items
        //adapter
        adapter=new CartItemAdapter(cartList);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewCart.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewCart.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCart.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
       new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewCart);
        //populate
        populateCart();


         return view;
    }

    //callback after swipe


    private void populateCart() {
        //create new instances of the model class
         CartItem scarf=new CartItem("Scarf","Read wollen Scarf","40","2",R.drawable.scarf);
         CartItem backpack=new CartItem("Backpack","Brown Backpack","50","3",R.drawable.backpack);
         CartItem heels=new CartItem("Heels","Red Backpack ","60","4",R.drawable.boots);

        CartItem scarf1=new CartItem("Scarf","Read wollen Scarf","40","2",R.drawable.scarf);
        CartItem backpack1=new CartItem("Backpack","Brown Backpack","50","3",R.drawable.backpack);
        CartItem heels1=new CartItem("Heels","Red Backpack ","60","4",R.drawable.boots);

        cartList.add(heels);
        cartList.add(scarf);
         cartList.add(backpack);

      adapter.notifyDataSetChanged();

    }

    @OnClick (R.id.checkoutButton)
     public void onClick(View v){
       startActivity(new Intent(getActivity(), CheckOutActivity.class));
    }

    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartItemAdapter.cartItemViewHolder) {
            //get the removed item name and display it in snackbar
            String name=cartList.get(viewHolder.getAdapterPosition()).getName();

            //backup of the removed item for undo process
            final CartItem deletedItem=cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex=viewHolder.getAdapterPosition();

            //remove the item from recyclerview
            adapter.removeItem(viewHolder.getAdapterPosition());

            //shoe in snackbar with Undo Option
            Snackbar snackbar=Snackbar
                    .make(mConstraintLayout,name+" "+"removed from cart!",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem,deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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
