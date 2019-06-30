package com.google.codelabs.appauth.product;

import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ProductInterface {

    @POST("product")
    Call<ResponseBody> addProduct(@Body Product product);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image,
                                 @Part ("upload")RequestBody name,
                                 @Part ("name") RequestBody title,
                                 @Part("price")RequestBody price,
                                 @Part("color") RequestBody color,
                                 @Part("category") RequestBody category,
                                 @Part("size") RequestBody size
                                 );

    @GET("/search")
    Call<List<Product>> searchByName(@Query("name") String name);

    @POST("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> requestToken(RequestBody requestBody);

}
