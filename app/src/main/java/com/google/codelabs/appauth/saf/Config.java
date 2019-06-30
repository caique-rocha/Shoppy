package com.google.codelabs.appauth.saf;

public class Config {

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
    public static final String CALLBACKURL = "https://spurquoteapp.ga/pusher/pusher.php?title=stk_push&message=result&push_type=individual&regId=";





}
