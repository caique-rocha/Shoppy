package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.activities.MainActivity;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Utils.Constants;
import com.google.codelabs.appauth.models.Response;
import com.google.codelabs.appauth.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.codelabs.appauth.Utils.Validation.validateEmail;
import static com.google.codelabs.appauth.Utils.Validation.validateFields;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private EditText mEtEmail;
    private EditText mEtPassword;
    private View mBtLogin;
    private TextView mTvForgotPassword;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
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

        View view = inflater.inflate(R.layout.fragment_login, container, false);
      initViews(view);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        initSharedPreferences();
        return view;
    }

    private void initSharedPreferences() {
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    private void initViews(View v) {
        mEtEmail =  v.findViewById(R.id.editTextmail);
        mEtPassword = v.findViewById(R.id.editTextpass);
        mBtLogin =  v.findViewById(R.id.login_view);
        mTvForgotPassword =v.findViewById(R.id.forgot_password);

        mBtLogin.setOnClickListener(view->login());
        mTvForgotPassword.setOnClickListener((view)->startActivity(new Intent(getActivity(), MainActivity.class)));

    }

    private void showDialog() {
        ResetPasswordDialog fragment = new ResetPasswordDialog();

        fragment.show(getFragmentManager(), ResetPasswordDialog.TAG);
    }

    private void login() {
        setError();

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {

            err++;
            mEtEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mEtPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            loginProcess(email,password);
           

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void showSnackBarMessage(String s) {
        if (getView() != null) {

            Snackbar.make(getView(),s, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void loginProcess(String email, String password) {
        mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
        
    }

    private void handleError(Throwable error) {
        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }

    }

    private void handleResponse(Response response) {
        Log.d(TAG, response.getToken());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();

        mEtEmail.setText(null);
        mEtPassword.setText(null);

//
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("token",response.getToken());
        intent.putExtra("email",response.getMessage());
        startActivity(intent);

    }

    private void setError() {

        mEtEmail.setError(null);
        mEtPassword.setError(null);
    }



    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_sign_up, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

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
        void onFragmentInteraction(Uri uri);
    }
}
