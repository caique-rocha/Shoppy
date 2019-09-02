package com.google.codelabs.appauth.Room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_table")
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")

    private Integer messageId;

    @ColumnInfo(name="text")
    private String messageText;

    @ColumnInfo(name="sender")
    private String messageSender;

    @ColumnInfo(name="timestamp")
    private String messageTimestamp;

    public MessageEntity(
             String messageText,
            String messageSender, String messageTimestamp) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.messageTimestamp = messageTimestamp;
    }

    @NonNull
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(@NonNull Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }
}
