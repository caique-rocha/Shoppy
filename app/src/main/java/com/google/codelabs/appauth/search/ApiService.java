package com.google.codelabs.appauth.search;

import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.Contact;


import java.util.List;

import io.reactivex.Single;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("contacts.php")
    Single<List<Contact>> getContacts(@Query("source") String source, @Query("search") String query);

    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken();

}
