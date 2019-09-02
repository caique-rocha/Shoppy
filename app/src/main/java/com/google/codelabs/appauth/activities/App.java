package com.google.codelabs.appauth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.codelabs.appauth.Helpers.NetworkUtil;
import com.google.codelabs.appauth.R;

import steelkiwi.com.library.DotsLoaderView;

public class App extends AppCompatActivity {
    DotsLoaderView dotsLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        dotsLoaderView = findViewById(R.id.dotsLoaderView);

        View view=findViewById(R.id.layout);
        initWhiteNotificationBar(view);

        dotsLoaderView.show();

        if (NetworkUtil.getConnectivityStatusString(this).equals("Wifi data enabled") ||
                NetworkUtil.getConnectivityStatusString(this).equals("Mobile data enabled")) {
            dotsLoaderView.hide();
        }

    }

    private void initWhiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            int flags=view.getSystemUiVisibility();
            flags |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
