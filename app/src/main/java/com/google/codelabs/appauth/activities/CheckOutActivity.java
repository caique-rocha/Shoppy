package com.google.codelabs.appauth.activities;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.codelabs.appauth.BuildConfig;
import com.google.codelabs.appauth.R;

import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.STKPush;
import com.google.codelabs.appauth.saf.RetrofitInstance;
import com.google.codelabs.appauth.saf.RetrofitInterface;
import com.google.codelabs.appauth.saf.Utils;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.google.codelabs.appauth.saf.Config.BUSINESS_SHORT_CODE;
import static com.google.codelabs.appauth.saf.Config.CALLBACKURL;
import static com.google.codelabs.appauth.saf.Config.PARTYB;
import static com.google.codelabs.appauth.saf.Config.PASSKEY;
import static com.google.codelabs.appauth.saf.Config.TRANSACTION_TYPE;

public class CheckOutActivity extends AppCompatActivity {

    Button mButton;
  public   String token;
  public String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        mButton=findViewById(R.id.checkout_button);
        mButton.setOnClickListener((View v)->{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Enter Safaricoms number to checkout Kshs..");
            final EditText input=new EditText(this);
            input.setHint("07xxxxxxxx");
            builder.setView(input);

            builder.setPositiveButton(android.R.string.ok,(dialog, which) -> {
               phone_number=input.getText().toString();
                obtainToken();


            });
            builder.setNegativeButton("Clear Cart",(dialog, which) -> {
                dialog.cancel();
            });
            builder.show();

        });


    }



    public  void obtainToken() {

        try {
            String app_key= BuildConfig.CONSUMER_KEY;
            String app_secret=BuildConfig.CONSUMER_SECRET;
            String appKeySecret=app_key +":"+app_secret;
            byte[] bytes=appKeySecret.getBytes("ISO-8859-1");
            String auth= Base64.encodeToString(bytes,Base64.NO_WRAP);

            RetrofitInterface retrofitInterface= RetrofitInstance
                                                .initRetrofit()
                                                 .create(RetrofitInterface.class);
            Call<AccessToken> call=retrofitInterface.getAccessToken("Basic " +auth);
            call.enqueue(new  Callback<AccessToken>(){

                @Override
                public void onResponse
                        (@NotNull Call<AccessToken> call,
                         @NotNull Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        token=response.body().accessToken;
                        Log.e( "onResponse: ", response.body().accessToken);
                        sendStkPush(phone_number);
                    }


                }

                @Override
                public void onFailure(@NotNull Call<AccessToken> call, @NotNull Throwable t) {
                    Log.e( "onFailure: ",t.getMessage());
                }
            });


    }
        catch (Exception e){
          e.printStackTrace();
        }
    }

    public void sendStkPush(String phone_number) {
//        Log.e( "sendStkPush: ",token );
        String timestamp= Utils.getTimeStamp();
        STKPush stkPush=new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE,PASSKEY,timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(100),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "test",
                "test"
        );
//        Log.d("sendStkPush: ",token);

        RetrofitInterface retrofitInterface=RetrofitInstance
                                           .initRetrofit()
                                           .create(RetrofitInterface.class);
        Call<ResponseBody>  call=retrofitInterface.sendPush(stkPush,"Bearer" + " " +token);
   call.enqueue(new Callback<ResponseBody>() {
       @Override
       public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
           if (response.isSuccessful()) {
               Log.d("onResponse: ",response.body().toString());
           }
           if (response.code()==1032) {
               Log.e("Error","Unaccepted");
           }
           else{
               Log.e("Unknown error","Error" +response.code());
           }
       }

       @Override
       public void onFailure(Call<ResponseBody> call, Throwable t) {

       }
   });

    }
}


