package com.example.messenger.model;

public class MessageFile extends Message {
    public String filename;
    public String downloadURL;

    public MessageFile(int type, int id, int senderId, String content, double sendTime, String topicId, String filename, String downloadURL){
        super(type, id, senderId, content, sendTime, topicId);
        this.filename = filename;
        this.downloadURL = downloadURL;
    }
}
