package com.example.messenger.model;

public class MessagePhoto extends Message {
    public String photoURL;

    public MessagePhoto(int type, int id, User user, String content, double sendTime, String topicId, String photoURL){
        super(type, id, user, content, sendTime, topicId);
        this.photoURL = photoURL;
    }
}
