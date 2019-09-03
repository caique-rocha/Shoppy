package com.google.codelabs.appauth.Room.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.codelabs.appauth.Room.ShopDatabase;
import com.google.codelabs.appauth.Room.daos.CartDao;
import com.google.codelabs.appauth.Room.entities.CartEntity;

import java.util.List;

public class CartRepository {
    private CartDao mCartDao;
    private LiveData<List<CartEntity>> mAllCart;

    public CartRepository(Application application) {
        ShopDatabase db = ShopDatabase.getDatabase(application);
        mCartDao = db.cartDao();
        mAllCart = mCartDao.getAllCartItems();
    }

    public LiveData<List<CartEntity>> getmAllCart() {
        return mAllCart;
    }

    public void insert(CartEntity cartEntity) {
        new insertAsyncTask(mCartDao).execute(cartEntity);
    }

    public void delete(CartEntity cartEntity){
        new deleteAsyncTask(mCartDao).execute(cartEntity);
    }

    private static  class insertAsyncTask extends AsyncTask<CartEntity, Void, Void> {
        private CartDao mAsyncTask;

        insertAsyncTask(CartDao dao) {
            this.mAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(CartEntity... cartEntities) {
            mAsyncTask.insert(cartEntities[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<CartEntity,Void,Void>{
        private  CartDao mAsyncTask;

        public deleteAsyncTask(CartDao mAsyncTask) {
            this.mAsyncTask = mAsyncTask;
        }

        @Override
        protected Void doInBackground(CartEntity... cartEntities) {
            mAsyncTask.deleteCartEntity(cartEntities[0]);
            return  null;
        }
    }

}
