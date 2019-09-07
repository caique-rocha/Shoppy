package com.google.codelabs.appauth.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.codelabs.appauth.Helpers.MyReceiver;
import com.google.codelabs.appauth.Helpers.NetworkUtil;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.adapters.SignUpTabLayout;
import com.google.codelabs.appauth.fragments.LoginFragment;
import com.google.codelabs.appauth.fragments.PasswordResetFragment;
import com.google.codelabs.appauth.fragments.ResetPasswordDialog;
import com.google.codelabs.appauth.fragments.SignUpFragment;

public class SignUpActivty extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener,
        PasswordResetFragment.OnFragmentInteractionListener,
        ResetPasswordDialog.Listener
{

    TabLayout signUpTabs;
    ViewPager signUpPager;

    public static final String TAG = SignUpActivty.class.getSimpleName();

    private LoginFragment mLoginFragment;
    private ResetPasswordDialog mResetPasswordDialog;
    private BroadcastReceiver MyReceiver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activty);

        signUpPager = findViewById(R.id.signUpViewPager);
        setUpViewPager(signUpPager);


        signUpTabs = findViewById(R.id.signUpTabs);
        signUpTabs.setupWithViewPager(signUpPager);

        whiteNotificationBar(signUpTabs);

        if (savedInstanceState == null) {

            loadFragment();
        }




    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            int flags=view.getSystemUiVisibility();
            flags |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }

    }



    private void loadFragment() {
    }

    private void setUpViewPager(ViewPager viewPager) {
        SignUpTabLayout signUpTabLayout = new SignUpTabLayout(getSupportFragmentManager());
        signUpTabLayout.addFragment(new LoginFragment(), "Login");
        signUpTabLayout.addFragment(new SignUpFragment(), "Sign Up");
        signUpTabLayout.addFragment(new PasswordResetFragment(), "Reset");
        viewPager.setAdapter(signUpTabLayout);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void onPasswordReset(String message) {
        showSnackBarMessage(message);
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(findViewById(R.id.frame_sign_up),message, Snackbar.LENGTH_SHORT).show();

    }
}
