package com.example.messenger.model;

public class Topic {
    public User user;
    public String lastMess;
    public long sendTime;
    public String topicID;
    public boolean hasNewMessage;

    public Topic(User user, String lastMess, long sendTime, String topicID, boolean hasNewMessage) {
        this.user = user;
        this.lastMess = lastMess;
        this.sendTime = sendTime;
        this.topicID = topicID;
        this.hasNewMessage = hasNewMessage;
    }

    public Topic(){}
}
