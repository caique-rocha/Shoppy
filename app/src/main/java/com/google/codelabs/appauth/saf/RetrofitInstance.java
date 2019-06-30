package com.google.codelabs.appauth.saf;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
  public static   Retrofit retrofit=null;

    public static  Retrofit initRetrofit(){
        if (retrofit==null) {
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://sandbox.safaricom.co.ke/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
