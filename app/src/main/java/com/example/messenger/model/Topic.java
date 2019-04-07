package com.example.messenger.model;

public class Topic {
    public User[] users;
    public String lastMess;
    public long sendTime;
    public String topicID;
    public boolean hasNewMessage;

    public Topic(User[] users, String lastMess, long sendTime, String topicID, boolean hasNewMessage) {
        this.users = users;
        this.lastMess = lastMess;
        this.sendTime = sendTime;
        this.topicID = topicID;
        this.hasNewMessage = hasNewMessage;
    }

    public Topic(){}
}
