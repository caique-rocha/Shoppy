package com.google.codelabs.appauth.saf;

import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.STKPush;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken(@Header("authorization") String auth);

    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush,
                           @Header("authorization") String bearer);
}
