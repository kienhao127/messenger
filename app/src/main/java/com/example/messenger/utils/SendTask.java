package com.example.messenger.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import static com.example.messenger.utils.ConstUtils.BASE_URL;

public class SendTask extends AsyncTask<String, Void, Void> {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(BASE_URL);
        } catch (URISyntaxException e) {
            Log.e("Socket Exception", e.toString());
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        mSocket.emit("MESSAGE_FROM_USER", strings[0]);
        return null;
    }
}
