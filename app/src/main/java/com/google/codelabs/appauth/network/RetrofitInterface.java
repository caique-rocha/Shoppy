package com.google.codelabs.appauth.network;

import com.google.codelabs.appauth.models.Message;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.models.Response;
import com.google.codelabs.appauth.models.Review;
import com.google.codelabs.appauth.models.Token;
import com.google.codelabs.appauth.models.User;
import com.google.codelabs.appauth.models.UserInfo;
import com.google.codelabs.appauth.models.CartItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface  RetrofitInterface {
    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);

    @POST("product")
    Observable<ResponseBody>addProduct(@Body Product product);

    @GET("products")
    Call<List<Product>> getAllProducts();

    //products by category
    @GET("products/{category}/products")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);

    //one product
    @GET("products/{category}/products")
    Call<Product> getSingleProduct(@Query("id") String productId, @Path("category") String productCategory);

    //registration endpoint
    @POST("auth/register")
    Call<User> registerUser(@Body User user);

    //login endpoint
    @POST("auth/login")
    Call<Token> loginUser(@Header("Authorization") String token);

    //cart endpoint
    @POST("cart")
    Call<List<CartItem>> postCartItems();

    //update cart


    //remove item from cart

    //get reviews endPoint
    @GET("product/{id}/reviews")
    Call<List<Review>> getReviewsById(@Path("id") String productId);

    //post Review endpoint
    @POST("product/{id}")
    Call<Review> addProductReview(@Query("reviews") String addReview);

    //search endpoint

    //categories endpoint

    // send message endpoint
    @POST("messages/{userid}/message")
    @GET("messages/{userid}/message")
    Call<Message> getMessagesById(@Path("userid") String userId);

    //

    //user info endpoint
    @GET("auth/{id}")
    Call<UserInfo> displayUserInfo(@Query("info") String userInfo);





}
