package com.google.codelabs.appauth.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Utils.Constants;
import com.google.codelabs.appauth.activities.LoginActivity;
import com.google.codelabs.appauth.adapters.ProfileBasicAdapter;
import com.google.codelabs.appauth.models.Response;
import com.google.codelabs.appauth.models.User;
import com.google.codelabs.appauth.models.ProfileBasic;
import com.google.codelabs.appauth.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ProfileFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    Unbinder unbinder;
    @BindView(R.id.recyclerViewBasic)
    RecyclerView recyclerViewBasic;

    @BindView(R.id.recyclerViewInfo)
    RecyclerView recyclerViewInfo;

    @BindView(R.id.tvName)
    TextView mTvName;

    @BindView(R.id.tvEmail)
    TextView mTvEmail;

    @BindView(R.id.constraint_container)
    ConstraintLayout mConstraintLayout;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    String imageUri;

    private View view;

    private CompositeSubscription mSubscriptions;


   @BindView(R.id.img_profile_pic)
   ImageView mImageProfile;


    @BindView(R.id.cardView3)
    CardView cardView1;

    @BindView(R.id.cardView5)
    CardView cardView2;
    @BindView(R.id.button_sign_login)
    TextView mLogin;

    private static final String TAG = "ProfileFragment";
    public static  final int IMAGE_REQUEST_CODE=100;


    List<ProfileBasic> profileBasics = new ArrayList<>();
    ProfileBasicAdapter basicAdapter;

    List<ProfileBasic> profileInfoList = new ArrayList<>();
    ProfileBasicAdapter getBasicAdapter;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setAction(TAG);
            startActivityForResult(intent,IMAGE_REQUEST_CODE);

        });


        basicAdapter = new ProfileBasicAdapter(profileBasics);
        // Inflate the layout for this fragment


        mToken = getArguments().getString("token");
        mEmail = getArguments().getString("email");
        imageUri=getArguments().getString("image");

        recyclerViewBasic.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewBasic.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewBasic.setAdapter(basicAdapter);

        getBasicAdapter = new ProfileBasicAdapter(profileInfoList);
        recyclerViewInfo.setAdapter(getBasicAdapter);
        recyclerViewInfo.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewInfo.setLayoutManager(new LinearLayoutManager(getContext()));

        initBasic();
        intInfo();
        mSubscriptions = new CompositeSubscription();

        initSharedPreferences();
        loadProfile();
        Picasso.get().load(imageUri)
                .placeholder(R.drawable.profile)
                .into(mImageProfile);


        return view;
    }


    private void loadProfile() {
        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(User user) {

        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());

    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String s) {
        Snackbar.make(mConstraintLayout, s, Snackbar.LENGTH_SHORT).show();

    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mToken = mSharedPreferences.getString(Constants.TOKEN, "");
        mEmail = mSharedPreferences.getString(Constants.EMAIL, "");
    }


    private void intInfo() {
        ProfileBasic one = new ProfileBasic(R.drawable.ic_move_to_inbox_black_24dp, "Invite Friends");
        ProfileBasic two = new ProfileBasic(R.drawable.ic_headset_mic_black_24dp, "Customer Support");
        ProfileBasic three = new ProfileBasic(R.drawable.ic_rate_review_black_24dp, "Rate Our App");
        ProfileBasic four = new ProfileBasic(R.drawable.ic_email_black_24dp, "Make a Suggestion ");

        profileInfoList.add(one);
        profileInfoList.add(two);
        profileInfoList.add(three);
        profileInfoList.add(four);

        basicAdapter.notifyDataSetChanged();
    }

    private void initBasic() {
        ProfileBasic one = new ProfileBasic(R.drawable.ic_format_list_bulleted_black_24dp, "All my Orders");
        ProfileBasic two = new ProfileBasic(R.drawable.ic_lightbulb_outline_black_24dp, "Pending Sipments");
        ProfileBasic three = new ProfileBasic(R.drawable.ic_account_balance_wallet_black_24dp, "Pending Payments");
        ProfileBasic four = new ProfileBasic(R.drawable.ic_shopping_basket_black_24dp, "Finished Orders");

        profileBasics.add(one);
        profileBasics.add(two);
        profileBasics.add(three);
        profileBasics.add(four);

        basicAdapter.notifyDataSetChanged();
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
        mSubscriptions.unsubscribe();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mSubscriptions.unsubscribe();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode ==IMAGE_REQUEST_CODE) {
         String   imageUrl=data.getStringExtra("image");
            Log.i(TAG, "onActivityResult: "+ imageUrl);
        }

    }
}
