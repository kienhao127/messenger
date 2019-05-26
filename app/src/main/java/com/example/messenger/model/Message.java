package com.example.messenger.model;

public class Message {
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int VIDEO = 3;
    public static final int LINK = 4;
    public static final int FILE = 5;

    public int type;
    public int id;
    public int senderId;
    public String content;
    public double sendTime;
    public String topicId;

    public Message(int type, int id, int senderId, String content, double sendTime, String topicId) {
        this.type = type;
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.sendTime = sendTime;
        this.topicId = topicId;
    }

    public Message() {
    }
}
