package com.google.codelabs.appauth.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdView;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.adapters.TabLayoutAdapter;
import com.google.codelabs.appauth.fragments.ShopHomeFragment;
import com.google.codelabs.appauth.fragments.ShopProductsFragment;
import com.google.codelabs.appauth.fragments.ShopReviewsFragment;

import java.util.HashMap;

public class ShopProfileActivity extends AppCompatActivity
        implements ShopHomeFragment.OnFragmentInteractionListener,
        ShopProductsFragment.OnFragmentInteractionListener,

        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView backbutton;
    private AdView mAdView;

    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);


        //viewpager
        viewPager = findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        //tablayout
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        whiteNotificationBar(tabLayout);

        //back button
        backbutton = findViewById(R.id.backimage);
        backbutton.setOnClickListener((View v) -> {
            startActivity(new Intent
                    (ShopProfileActivity.this,
                            MainActivity.class));
        });

        //slider
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
//
//        HashMap<String, String> url_maps = new HashMap<>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put("Hannibal", R.drawable.catone);
        file_maps.put("Big Bang Theory", R.drawable.cattwo);
        file_maps.put("House of Cards", R.drawable.catthree);
        file_maps.put("Game of Thrones", R.drawable.cattwo);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (String name : file_maps.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(this);
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            //init slider layout
            textSliderView
                    .image(file_maps.get(name))
//                    .description(name)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add extra info
            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);


    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);

        }
    }
    private void setUpViewPager(ViewPager viewPager) {
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShopHomeFragment(), "Home");
        adapter.addFragment(new ShopProductsFragment(), "Products");
        adapter.addFragment(new ShopReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        //To prevent memory leak on rotation, make sure to call stopAutoCycle() before
        //destroying the fragment
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Slider demo", "Page changed" + position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
