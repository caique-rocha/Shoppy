package com.google.codelabs.appauth.service;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.codelabs.appauth.saf.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

       String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //saving regid to shared prefs
        storeRegIdInPref(refreshedToken);

        //send regid to your server
        sendregistrationToServer(refreshedToken);

        //notify UI that registration has completed to hide the progress indicator

        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendregistrationToServer(final String token) {
        //sending fcm to server
        Log.e(TAG, "sendingRegistrationToServer" + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("regId", token);
        editor.apply();

    }
}
