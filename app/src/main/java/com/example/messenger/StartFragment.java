package com.example.messenger;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.messenger.model.User;
import com.example.messenger.utils.UserUtils;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import androidx.navigation.Navigation;


public class StartFragment extends Fragment {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.6:3000");
        } catch (URISyntaxException e) {
            Log.e("Socket Exception", e.toString());
        }
    }

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userInfo = sharedPref.getString("USER_INFO", "");
        if (userInfo.isEmpty()){
            Navigation.findNavController(view).navigate(R.id.loginFragment, null);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("USER_INFO", userInfo);
            User currentUser = UserUtils.getCurrentUser(getActivity());
            mSocket.emit("USER_LOGIN", currentUser.id);
            Navigation.findNavController(view).navigate(R.id.listTopicFragment, bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }
}
