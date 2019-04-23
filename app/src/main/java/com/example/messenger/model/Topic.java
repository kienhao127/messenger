package com.example.messenger.model;

public class Topic {
    public String[] name;
    public String lastMess;
    public long sendTime;
    public String topicId;
    public boolean hasNewMessage;

    public Topic(String[] name, String lastMess, long sendTime, String topicId, boolean hasNewMessage) {
        this.name = name;
        this.lastMess = lastMess;
        this.sendTime = sendTime;
        this.topicId = topicId;
        this.hasNewMessage = hasNewMessage;
    }

    public Topic(){}
}
