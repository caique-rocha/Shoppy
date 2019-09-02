package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Response;
import com.google.codelabs.appauth.models.User;
import com.google.codelabs.appauth.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.codelabs.appauth.Utils.Validation.validateEmail;
import static com.google.codelabs.appauth.Utils.Validation.validateFields;

public class SignUpFragment extends Fragment {
    public static final String TAG = SignUpFragment.class.getSimpleName();
    private CompositeSubscription mSubscriptions;



    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private View   mBtRegister;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
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
        View view= inflater.inflate(R.layout.fragment_sign_upragment, container, false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mEtName =  v.findViewById(R.id.editTextUsername);
        mEtEmail = v.findViewById(R.id.editTextEmail);
        mEtPassword = v.findViewById(R.id.editTextPassword);
        mBtRegister =  v.findViewById(R.id.SignUpButton);

        mBtRegister.setOnClickListener(view -> register());
    }

    private void register() {
        setError();
        String name = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        int err = 0;

        if (!validateFields(name)) {

            err++;
            mEtName.setError("Name should not be empty !");
        }

        if (!validateEmail(email)) {

            err++;
            mEtEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mEtPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            registerProcess(user);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void registerProcess(User user) {
        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
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

    private void showSnackBarMessage(String message) {
        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }

    }



    private void handleResponse(Response response) {
        showSnackBarMessage(response.getMessage());



    }

    private void setError() {

        mEtName.setError(null);
        mEtEmail.setError(null);
        mEtPassword.setError(null);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriptions.unsubscribe();

    }
}
