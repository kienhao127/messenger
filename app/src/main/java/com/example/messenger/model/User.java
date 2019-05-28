package com.example.messenger.model;

public class User {
    public String avatar;
    public int id;
    public String fullname;

    public User(String avatar, int id, String fullname) {
        this.avatar = avatar;
        this.id = id;
        this.fullname = fullname;
    }

    public User(){

    }

    public User(User user){
        this.avatar = user.avatar;
        this.id = user.id;
        this.fullname = user.fullname;

    }
}
