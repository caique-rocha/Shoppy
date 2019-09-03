package com.google.codelabs.appauth.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.codelabs.appauth.activities.SplashActivity;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
String status=NetworkUtil.getConnectivityStatusString(context);
        if (status.isEmpty()) {
            status="No Internet Connection";
        }
        Toast.makeText(context,status,Toast.LENGTH_LONG).show();

        if (status.equals("No Internet Connection")) {
            Intent goToSplash=new Intent(context, SplashActivity.class);
            context.startActivity(goToSplash);
        }
    }
}
