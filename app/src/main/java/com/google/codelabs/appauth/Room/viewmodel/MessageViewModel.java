package com.google.codelabs.appauth.Room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.codelabs.appauth.Room.entities.MessageEntity;
import com.google.codelabs.appauth.Room.repos.MessageRepository;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mRepository;
    private LiveData<List<MessageEntity>> mAllMessage;
    public MessageViewModel(@NonNull Application application) {
        super(application);
        mRepository=new MessageRepository(application);
        mAllMessage=mRepository.getmAllMessage();
    }

    public LiveData<List<MessageEntity>> getmAllMessage() {
        return mAllMessage;
    }

    public void addMessage(MessageEntity messageEntity){
        mRepository.addMessage(messageEntity);
    }

    public void deleteMessage(MessageEntity messageEntity){
        mRepository.deleteMessage(messageEntity);
    }
}
