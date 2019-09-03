package com.google.codelabs.appauth.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.codelabs.appauth.BuildConfig;
import com.google.codelabs.appauth.Helpers.RecyclerItemTouchHelper;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Room.adapters.CartItemAdapter;
import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.Room.viewmodel.CartViewModel;
import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.STKPush;
import com.google.codelabs.appauth.saf.RetrofitInstance;
import com.google.codelabs.appauth.saf.RetrofitInterface;
import com.google.codelabs.appauth.saf.Utils;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.google.codelabs.appauth.saf.Config.BUSINESS_SHORT_CODE;
import static com.google.codelabs.appauth.saf.Config.CALLBACKURL;
import static com.google.codelabs.appauth.saf.Config.PARTYB;
import static com.google.codelabs.appauth.saf.Config.PASSKEY;
import static com.google.codelabs.appauth.saf.Config.TRANSACTION_TYPE;

public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = "CartFragment";
    Unbinder unbinder;

    @BindView(R.id.checkoutButton)
    ImageButton checkOut;
    @BindView(R.id.rVCart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.constraint_layout)
    ConstraintLayout mConstraintLayout;

    //list of all items
    private List<CartEntity> cartEntities = new ArrayList<>();
    private List<CartEntity> cartEntitiesHolder = new ArrayList<>();
    //Adapter
    private CartItemAdapter adapter;

    CartViewModel mCartViewModel;
    private OnFragmentInteractionListener mListener;

    private String token, phone_number;

    public CartFragment() {
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        //add a divider between recycler view items

        adapter = new CartItemAdapter(cartEntities);

        mCartViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CartViewModel.class);
        mCartViewModel.getmAllCart().observe(getActivity(), cartEntities -> {
            //update UI with cached copy of the words in the adapter
            adapter.setmCart(cartEntities);

            cartEntitiesHolder.addAll(cartEntities);

            final TextView mCartCount = getActivity().findViewById(R.id.tvNotif);
            mCartCount.setVisibility(View.VISIBLE);
            mCartCount.setText(String.valueOf(cartEntities.size()));

        });

        recyclerViewCart.setAdapter(adapter);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCart.setItemAnimator(new DefaultItemAnimator());


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewCart);

        //populate


        checkOut.setOnClickListener(v -> toCheckOut());

        return view;
    }


    private void toCheckOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.saf);
        final EditText mPhoneNumber = new EditText(getActivity());
        mPhoneNumber.setHint(R.string.hint);
        builder.setView(mPhoneNumber);

        builder.setPositiveButton("Checkout", (dialog, which) -> {
            phone_number = mPhoneNumber.getText().toString();
            obtainToken();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void obtainToken() {

        try {
            String app_key = BuildConfig.CONSUMER_KEY;
            String app_secret = BuildConfig.CONSUMER_SECRET;
            String appKeySecret = app_key + ":" + app_secret;
            byte[] bytes = appKeySecret.getBytes(StandardCharsets.ISO_8859_1);
            String auth = Base64.encodeToString(bytes, Base64.NO_WRAP);

            RetrofitInterface retrofitInterface = RetrofitInstance
                    .initRetrofit()
                    .create(RetrofitInterface.class);
            Call<AccessToken> call = retrofitInterface.getAccessToken("Basic " + auth);
            call.enqueue(new Callback<AccessToken>() {

                @Override
                public void onResponse
                        (@NotNull Call<AccessToken> call,
                         @NotNull Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            token = response.body().accessToken;
                        }
                        assert response.body() != null;
                        Timber.e(response.body().accessToken);
                        sendStkPush(phone_number);
                    }


                }

                @Override
                public void onFailure(@NotNull Call<AccessToken> call, @NotNull Throwable t) {
                    Timber.e(t);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendStkPush(String phone_number) {
//        Log.e( "sendStkPush: ",token );
        String timestamp = Utils.getTimeStamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(10),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "test",
                "test"
        );
//        Log.d("sendStkPush: ",token);

        RetrofitInterface retrofitInterface = RetrofitInstance
                .initRetrofit()
                .create(RetrofitInterface.class);
        Call<ResponseBody> call = retrofitInterface.sendPush(stkPush, "Bearer" + " " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Timber.d("data: %s", response.body().toString());
                    }
                } else {
                    Timber.d("onResponse Error: %s", response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Timber.d("onFailure: failed");
            }
        });
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
        if (viewHolder instanceof CartItemAdapter.CartViewHolder) {

            //get the removed item name and display it in snackbar
            String name = cartEntitiesHolder.get(position).mName;

            //backup of the removed item for undo process
            final CartEntity deletedItem = cartEntitiesHolder.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            //remove the item from recyclerview
            adapter.removeItem(viewHolder.getAdapterPosition());

            //delete from database

            mCartViewModel.deleteCartEntity(deletedItem);

            //shoe in snack with Undo Option
            Snackbar snackbar = Snackbar
                    .make(mConstraintLayout, name + " " + "removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", v -> {
                //undo is selected, restore the deleted item
                adapter.restoreItem(deletedItem, deletedIndex);
                mCartViewModel.insert(deletedItem);
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
