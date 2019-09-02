package com.google.codelabs.appauth.Room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.codelabs.appauth.Room.entities.CartEntity;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartEntity cartEntity);

    @Query("SELECT * FROM  cart_table")
    LiveData<List<CartEntity>>getAllCartItems();

    @Delete
    void deleteCartEntity(CartEntity cartEntity);

}
