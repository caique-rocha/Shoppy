package com.google.codelabs.appauth.product;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {
    static Retrofit retrofit=null;
    private static String BASE_URL="http://192.168.1.66:5000/";

    public static Retrofit getProductClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }
}
