package com.example.messenger.model;

public class MessagePhoto extends Message {
    public String photoURL;

    public MessagePhoto(int type, int id, int senderId, String content, double sendTime, String topicId, String photoURL){
        super(type, id, senderId, content, sendTime, topicId);
        this.photoURL = photoURL;
    }
}
