package com.google.codelabs.appauth.push.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Utils.NotificationUtils;
import com.google.codelabs.appauth.saf.Config;
import com.google.firebase.messaging.FirebaseMessaging;


public class PushActivity extends AppCompatActivity {
    private static final String TAG = PushActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadCastReceiver;
    private TextView textRegId, txtMessage, tvResponse;
    private Button mButtonCheckout;
    private String mFirebaseRegId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        textRegId = findViewById(R.id.txt_reg_id);
        txtMessage = findViewById(R.id.txt_push_message);
        mButtonCheckout = findViewById(R.id.checkout);
        tvResponse = findViewById(R.id.tvResponse);




        mRegistrationBroadCastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    //FCM registered successfully
                    //now subscribe to 'global' topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    //new push notification received
                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(),
                            "Push notification :" + message, Toast.LENGTH_LONG).show();
                    txtMessage.setText(message);

                }
            }

        };
        displayFirebaseRegId();
    }




    //fetches regId from shared preferences and displays it on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext()
                .getSharedPreferences(Config.SHARED_PREF, 0);
        mFirebaseRegId = pref.getString("regId", null);


        if (!TextUtils.isEmpty(mFirebaseRegId)) {
            textRegId.setText("Firebase Reg Id:" + mFirebaseRegId);
        } else {
            textRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //register fcm registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadCastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        //register new push messages receiver each time
        // a message arrives, the activity will be notified
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadCastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        //clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadCastReceiver);
        super.onPause();

    }
}
