package com.example.messenger.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.messenger.model.User;
import com.google.gson.Gson;

public class UserUtils {
    public static User getCurrentUser(Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String userInfo = sharedPref.getString("USER_INFO", "");
        Gson gson = new Gson();
        User user = gson.fromJson(userInfo, User.class);
        return user;
    }
}
