package com.example.messenger.model;

public class User {
    public String avatar;
    public int id;
    public String name;

    public User(String avatar, int id, String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }

    public User(){

    }

    public User(User user){
        this.avatar = user.avatar;
        this.id = user.id;
        this.name = user.name;

    }
}
