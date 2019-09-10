package com.google.codelabs.appauth.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.MobileAds;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.fragments.AllCategoriesFragment;
import com.google.codelabs.appauth.fragments.CartFragment;
import com.google.codelabs.appauth.fragments.CheckOut;
import com.google.codelabs.appauth.fragments.HomeFragment;
import com.google.codelabs.appauth.fragments.MessagesFragment;
import com.google.codelabs.appauth.fragments.MoreFragment;
import com.google.codelabs.appauth.fragments.NotificationFragment;
import com.google.codelabs.appauth.fragments.OrderSuccess;
import com.google.codelabs.appauth.fragments.ProfileFragment;
import com.google.codelabs.appauth.fragments.SearchFragment;
import com.google.codelabs.appauth.fragments.ConversationFragment;
import com.google.codelabs.appauth.saf.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        CartFragment.OnFragmentInteractionListener,
        MoreFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        AllCategoriesFragment.OnFragmentInteractionListener,
        CheckOut.OnFragmentInteractionListener,
        OrderSuccess.OnFragmentInteractionListener,
        MessagesFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener,
        ConversationFragment.OnFragmentInteractionListener {


    @BindView(R.id.notificationBtn)
    ImageView notificationButton;

    @BindView(R.id.messageButton)
    ImageView messageButton;

    @BindView(R.id.logo)
    TextView toProfile;

    String token;
    String email;
    String imageUri;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static String FirebaseRegId = null;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //save the current users fragment
        savedInstanceState.putString("currentFragment", "currentFragment");

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MobileAds.initialize(this, "ca-app-pub-9476521556541591~7305076193");


        //white notification bar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        loadFragment(new HomeFragment());


        notificationButton = findViewById(R.id.notificationBtn);

        messageButton = findViewById(R.id.messageButton);

        toProfile = findViewById(R.id.logo);


        whiteNotificationBar(toolbar);

        //default fragment
        loadFragment(new HomeFragment());


        //bottom navigation View
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //notification
        notificationButton.setOnClickListener(v ->
                loadFragment(new NotificationFragment())
        );

        //messages
        messageButton.setOnClickListener(v ->
                loadFragment(new MessagesFragment()));

        //invoke profile
        toProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ShopProfileActivity.class)));


        token = getIntent().getStringExtra("token");
        email = getIntent().getStringExtra("email");

        getFirebaseId();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case (R.id.navigation_shop):
                    toProfile.setText(getResources().getString(R.string.title_home));
                    loadFragment(new HomeFragment());
                    return true;
                case (R.id.navigation_search):
                    toProfile.setText(getResources().getString(R.string.title_search));
                    loadFragment(new SearchFragment());

                    return true;
                case (R.id.navigation_cart):
                    toProfile.setText(getResources().getString(R.string.title_cart));
                    loadFragment(new CartFragment());
                    return true;
                case (R.id.navigation_profile):
                    toProfile.setText(getResources().getString(R.string.title_profile));
                    Bundle bundle = new Bundle();
                    bundle.putString("token", token);
                    bundle.putString("email", email);
                    bundle.putString("image", imageUri);
                    ProfileFragment fragment = new ProfileFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
                case (R.id.navigation_more):
                    toProfile.setText(getResources().getString(R.string.title_more));
                    loadFragment(new MoreFragment());
                    return true;
            }

            return false;
        }

    };


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if its first time launch
        AsyncTask.execute(()->{
            SharedPreferences getPrefs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean isFirstTime=getPrefs.getBoolean("first-time",true);
            if (isFirstTime) {
             Intent intent=new Intent(MainActivity.this,IntroActivity.class);
                runOnUiThread(()->startActivity(intent));
                startActivity(intent);
                SharedPreferences.Editor editor=getPrefs.edit();
                editor.putBoolean("first-time",false);
                editor.apply();
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public String getFirebaseId() {
        SharedPreferences prefs = getSharedPreferences(Config.SHARED_PREF, 0);
        FirebaseRegId = prefs.getString("regId", null);
//        Log.d(TAG, FirebaseRegId);
        return FirebaseRegId;
    }


}
