package com.example.messenger.model;

public class MessageFile extends Message {
    public String filename;
    public String downloadURL;

    public MessageFile(int type, int id, User user, String content, double sendTime, String topicId, String filename, String downloadURL){
        super(type, id, user, content, sendTime, topicId);
        this.filename = filename;
        this.downloadURL = downloadURL;
    }
}
