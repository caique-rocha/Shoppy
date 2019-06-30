package com.google.codelabs.appauth.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
import com.google.codelabs.appauth.push.activity.PushActivity;
import com.google.codelabs.appauth.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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


    @BindView(R.id.notificationButton)
    ImageView notificationButton;

    @BindView(R.id.messageButton)
    ImageView messageButton;

    @BindView(R.id.logo)
    ImageView toProfile;

    String token;
    String email;
    String imageUri;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final  String ACTION="com.google.codelabs.appauth.activities.MainActivity";

    private static final Fragment currentFragment=new HomeFragment();
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //save the current users fragment
       savedInstanceState.putString("currentFragment","currentFragment");

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         ButterKnife.bind(this);
        MobileAds.initialize(this,"ca-app-pub-9476521556541591~7305076193");


        //white notificationbar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        whiteNotificationBar(toolbar);

        //default fragment
//        loadFragment(new HomeFragment());


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
                startActivity(new Intent(MainActivity.this,ShopProfileActivity.class)));


        token = getIntent().getStringExtra("token");
        email= getIntent().getStringExtra("email");




    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case (R.id.navigation_shop):
                    loadFragment(new HomeFragment());
                    return true;
                case (R.id.navigation_search):
                    loadFragment(new SearchFragment());
//                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    return true;
                case (R.id.navigation_cart):
                    loadFragment(new CartFragment());
                    return true;
                case (R.id.navigation_profile):

                    Bundle bundle = new Bundle();
                    bundle.putString("token", token);
                    bundle.putString("email", email);
                    bundle.putString("image",imageUri);
                    ProfileFragment fragment = new ProfileFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
//                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                    intent.setAction(ACTION);
//                    startActivity(intent);
                    return true;
                case (R.id.navigation_more):
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
}
