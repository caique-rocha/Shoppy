package com.google.codelabs.appauth.activities;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.Room.viewmodel.CartViewModel;
import com.google.codelabs.appauth.adapters.TabLayoutAdapter;
import com.google.codelabs.appauth.fragments.FragmentDetails;
import com.google.codelabs.appauth.fragments.ProductFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, ProductFragment.OnFragmentInteractionListener {

    @BindView(R.id.productBack)
    ImageView back;

    @BindView(R.id.tabProduct)
    TabLayout tabLayout;

    @BindView(R.id.viewPagerProduct)
    ViewPager viewPager;

    @BindView(R.id.slider_product)
    SliderLayout sliderLayout;

    @BindView(R.id.tVName)
    TextView mName;

    @BindView(R.id.tVPrice)
    TextView mPrice;

    @BindView(R.id.addtocart)
    Button mAddCart;


    private String id, name, category, image, price;

    CartViewModel mCartViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("category");
        image = getIntent().getStringExtra("image");
        price = getIntent().getStringExtra("price");

        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("added", Context.MODE_PRIVATE);
        boolean isAddedToCart = mPrefs.getBoolean("added", false);
        if (isAddedToCart) {
            mAddCart.setVisibility(View.GONE);
        }

        mName.setText(name);
        mPrice.setText("$".concat(price));

        back.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        //viewpager
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        HashMap<String, String> file_maps = new HashMap<>();
        file_maps.put("Product", image);
//        file_maps.put("House of Cards", R.drawable.catthree);
//        file_maps.put("Game of Thrones", R.drawable.cattwo);

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
            whiteNotificationBar(sliderLayout);
            mAddCart.setOnClickListener(v -> addToCart());

        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    private void addToCart() {
        CartEntity cartEntity = new CartEntity(id, name, category, price, image);
        mCartViewModel = new CartViewModel(getApplication());
        mCartViewModel.insert(cartEntity);

        Context context = getApplicationContext();
        SharedPreferences mAddedToCart = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mAddedToCart.edit();
        editor.putBoolean("added", true);
        editor.apply();

        mAddCart.setVisibility(View.GONE);
    }

    private void setUpViewPager(ViewPager viewPager) {
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new FragmentDetails(), "Details");
        adapter.addFragment(new ProductReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
