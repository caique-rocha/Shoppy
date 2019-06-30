package com.google.codelabs.appauth.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.codelabs.appauth.adapters.TabLayoutAdapter;
import com.google.codelabs.appauth.fragments.FragmentDetails;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

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


    private String name, price,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        image=getIntent().getStringExtra("image");

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
        file_maps.put("Big Bang Theory", "https://www.sccpre.cat/mypng/detail/13-134844_transparent-background-laptop-png.png");
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

        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }
    private void setUpViewPager(ViewPager viewPager) {
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new FragmentDetails(), "Details");
        adapter.addFragment(new ProductReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
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
