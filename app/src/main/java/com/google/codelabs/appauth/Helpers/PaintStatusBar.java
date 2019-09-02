package com.google.codelabs.appauth.Helpers;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

public class PaintStatusBar extends Application {
    public void  whiteStatusBar(View view){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            int flags=view.getSystemUiVisibility();
            flags|=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
//            getWindow().setStatusBarColor(Color.WHITE);


        }
    }
}
