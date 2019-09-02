package com.google.codelabs.appauth.Room.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.codelabs.appauth.Room.ShopDatabase;
import com.google.codelabs.appauth.Room.daos.MessageDao;
import com.google.codelabs.appauth.Room.entities.MessageEntity;

import java.util.List;

public class MessageRepository {
    private MessageDao mMessageDao;
    private LiveData<List<MessageEntity>> mAllMessage;

    public MessageRepository(Application application) {
        ShopDatabase db = ShopDatabase.getDatabase(application);
        mMessageDao = db.messageDao();
        mAllMessage = mMessageDao.getAllMessages();
    }

    public LiveData<List<MessageEntity>> getmAllMessage() {
        return mAllMessage;
    }

    public void addMessage(MessageEntity messageEntity) {
        new insertAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void deleteMessage(MessageEntity messageEntity) {
        new deleteAsyncTask(mMessageDao).execute(messageEntity);
    }

    private static class insertAsyncTask extends AsyncTask<MessageEntity, Void, Void> {
        private MessageDao mAsyncTask;

        insertAsyncTask(MessageDao mAsyncTask) {
            this.mAsyncTask = mAsyncTask;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            mAsyncTask.addMessage(messageEntities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<MessageEntity, Void, Void> {
        private MessageDao mAsyncTask;

        deleteAsyncTask(MessageDao mAsyncTask) {
            this.mAsyncTask = mAsyncTask;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            mAsyncTask.deleteMessage(messageEntities[0]);
            return null;
        }
    }
}
