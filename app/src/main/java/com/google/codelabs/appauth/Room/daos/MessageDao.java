package com.google.codelabs.appauth.Room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.codelabs.appauth.Room.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void addMessage(MessageEntity messageEntity);

    @Query("SELECT * FROM message_table ORDER BY timestamp ASC")
    LiveData<List<MessageEntity>> getAllMessages();

    @Delete
    void deleteMessage(MessageEntity messageEntity);
}
