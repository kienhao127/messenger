package com.example.messenger.model;

public class Message {
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int VIDEO = 3;
    public static final int LINK = 4;
    public static final int FILE = 5;

    public int type;
    public int id;
    public User user;
    public String content;
    public double sendTime;
    public String topicID;

    public Message(int type, int id, User user, String content, double sendTime, String topicID) {
        this.type = type;
        this.id = id;
        this.user = user;
        this.content = content;
        this.sendTime = sendTime;
        this.topicID = topicID;
    }

    public Message() {
    }
}
