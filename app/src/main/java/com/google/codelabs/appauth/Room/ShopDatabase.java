package com.google.codelabs.appauth.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.gms.nearby.messages.Message;
import com.google.codelabs.appauth.Room.daos.CartDao;
import com.google.codelabs.appauth.Room.daos.MessageDao;
import com.google.codelabs.appauth.Room.entities.CartEntity;
import com.google.codelabs.appauth.Room.entities.MessageEntity;

@Database(entities = {CartEntity.class, MessageEntity.class},version =3)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
    public abstract MessageDao messageDao();
    public static volatile ShopDatabase INSTANCE;
   public static ShopDatabase getDatabase(final Context context){
       if (INSTANCE==null) {
           synchronized (ShopDatabase.class){
               if (INSTANCE==null) {
                   INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                           ShopDatabase.class,"shop_database")
                           .fallbackToDestructiveMigration()
                           .build();
               }
           }
       }
       return INSTANCE;
    }



}
