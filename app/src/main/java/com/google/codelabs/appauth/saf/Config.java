package com.google.codelabs.appauth.saf;

import com.google.codelabs.appauth.activities.MainActivity;
import com.google.codelabs.appauth.fragments.NotificationFragment;

public class Config {
    public static final String regId=MainActivity.FirebaseRegId;
    //global topic to receive app wide push notifs
    public static final String TOPIC_GLOBAL="global";

    //broadcast receiver intent filters
    public static  final String REGISTRATION_COMPLETE="registrationComplete";
    public static final String PUSH_NOTIFICATION="pushNotification";

    //id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID=100;
    public static final int NOTIFICATION_ID_BIG_IMAGE=101;
    public static  final String SHARED_PREF="ah_firebase";

    public static final int CONNECT_TIMEOUT=60 *1000;
    public static final int READ_TIMEOUT=60 *1000;
    public static final int WRITE_TIMEOUT=60 *1000;
    public static final String BASE_URL="https://sandbox.safaricom.co.ke/";

    //stk push properties
    public static final String BUSINESS_SHORT_CODE="174379";
    public static final String PASSKEY="bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String TRANSACTION_TYPE="CustomerPayBillOnline";
    public static final String PARTYB="174379";
    public static final String CALLBACKURL = "https://serene-shenandoah-49935.herokuapp.com/hooks";





}
