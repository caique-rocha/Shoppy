package com.google.codelabs.appauth.Room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.Room.repos.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository mRepository;
    private LiveData<List<CartEntity>>mAllCart;
    public CartViewModel(@NonNull Application application) {
        super(application);
        mRepository=new CartRepository(application);
        mAllCart=mRepository.getmAllCart();
    }

    public LiveData<List<CartEntity>> getmAllCart(){
        return mAllCart;
    }

    public void insert(CartEntity cartEntity){
        mRepository.insert(cartEntity);
    }
    public void deleteCartEntity(CartEntity cartEntity){
        mRepository.delete(cartEntity);
    }
}
