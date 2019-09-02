package com.google.codelabs.appauth.product;

import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.models.AccessToken;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.models.Review;
import com.google.codelabs.appauth.models.TopItemModel;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;
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

    @GET("search")
    Single<List<Product>> getContacts(@Query("name") String name);

    @GET("/search")
    Call<List<CartEntity>> getByCategory(@Query("name") String name);

    @GET("/search")
    Call<List<TopItemModel>>getByLabel(@Query("name") String name);

    @POST("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> requestToken(RequestBody requestBody);

    @POST("/reviews")
    Call<ResponseBody> addShopReview(@Body Review review);

    @GET("/reviews")
    Call<List<Review>> getAllShopReviews();

    @POST("/hooks/mpesa/status")
    Call<ResponseBody> transactionStatus(@Query("firebaseId") String mFirebaseId);
}
