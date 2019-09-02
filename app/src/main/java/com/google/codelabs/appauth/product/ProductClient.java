package com.google.codelabs.appauth.product;

import com.google.codelabs.appauth.Utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {
    private static final String TAG = ProductClient.class.getSimpleName();
    static Retrofit retrofit = null;
    static OkHttpClient okHttpClient;
    static int REQUEST_TIME_OUT = 60;

    public static Retrofit getProductClient() {
        if (okHttpClient == null) {
            initOkHttpClient();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

       builder.addInterceptor(chain -> {
           Request original=chain.request();
           Request.Builder requestBuilder=original.newBuilder()
                   .addHeader("Accept","application/json")
                   .addHeader("Request-Type","Android")
                   .addHeader("Content-Type","application/json");
           Request request=requestBuilder.build();
           return chain.proceed(request);


       });
       okHttpClient=builder.build();
    }

   private void resetClient(){
        okHttpClient=null;
        retrofit=null;
   }
}
