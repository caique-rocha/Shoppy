package com.google.codelabs.appauth.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.google.codelabs.appauth.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creating slider page and provide title,description, background color and image
        SliderPage firstFragment = new SliderPage();
        firstFragment.setTitle("Access  Amazing products ");
        firstFragment.setDescription("Get the opportunity " +
                "to surf a whole world of first class commodities ");
        firstFragment.setImageDrawable(R.drawable.ic_food);
        firstFragment.setBgColor(getResources().getColor(R.color.colorGreen));
        addSlide(AppIntroFragment.newInstance(firstFragment));

        askForPermissions(new String[]{Manifest.permission.INTERNET}, 1);

        SliderPage secondFragment = new SliderPage();
        secondFragment.setTitle("Choose your items ");
        secondFragment.setDescription("Do your Shopping by simply adding items to  the cart");
        secondFragment.setImageDrawable(R.drawable.ic_discount);
        secondFragment.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(secondFragment));

        askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);


        SliderPage thirdFragment = new SliderPage();
        thirdFragment.setTitle("Secure and simple checkout");
        thirdFragment.setDescription("Submit your payments and have items delivered");
        thirdFragment.setImageDrawable(R.drawable.ic_travel);
        thirdFragment.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(thirdFragment));

        askForPermissions(new String[]{Manifest.permission.READ_SMS}, 3);

        setSeparatorColor(Color.parseColor("#ffffff"));
        setFadeAnimation();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
    }

}
