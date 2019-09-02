package com.google.codelabs.appauth.service;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.codelabs.appauth.push.activity.PushActivity;
import com.google.codelabs.appauth.saf.Config;
import com.google.codelabs.appauth.Utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From:" + remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }

        //check if message contains notification payload
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body" + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
//        check if data contains a data payload
        if (remoteMessage.getData().size() > 0) {

            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(jsonObject);
            } catch (Exception e) {
                Log.e(TAG, "Exception " + e.getMessage());
            }
        }
    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            //app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);

            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            //play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            //app in background, firebase handles the notification itself

        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json:" + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");

            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title" + title);
            Log.e(TAG, "message" + message);
            Log.e(TAG, "isBackground" + isBackground);
            Log.e(TAG, "imageUrl" + imageUrl);
            Log.e(TAG, "payload" + payload);
            Log.e(TAG, "timestamp" + timestamp);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                //app is in foreground so broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                //play Notification Sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

            } else {
                //app is in background show notification in notifs tray
                Intent resultIntent = new Intent(getApplicationContext(), PushActivity.class);
                resultIntent.putExtra("message",message);

                //check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(),
                            title, message, timestamp, resultIntent);
                } else {
                    //image is present show notification with image
                    showNotificationMessageWithBigImage(
                            getApplicationContext(), title, message,
                            timestamp, resultIntent, imageUrl);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception :" + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e(TAG, " Exception :" + e.getLocalizedMessage());

        }


    }

    //show notification with text only
    private void showNotificationMessage
    (Context context, String title, String message,
     String timestamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timestamp, intent);

    }

    //show notification image with big text
    private void showNotificationMessageWithBigImage
    (Context context, String title, String message,
     String timestamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timestamp, intent, imageUrl);
    }


}
