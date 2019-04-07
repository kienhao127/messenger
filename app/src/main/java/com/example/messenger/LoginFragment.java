package com.example.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messenger.model.User;
import com.example.messenger.model.response.LoginResponse;
import com.example.messenger.utils.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView textViewHello;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_button).setOnClickListener(this);
        view.findViewById(R.id.register_button).setOnClickListener(this);
    }

    private EditText getEmail(){
        return (EditText) getView().findViewById(R.id.email);
    }

    private EditText getPassword(){
        return (EditText) getView().findViewById(R.id.password);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                onLoginPress();
                break;
            case R.id.register_button:
                //TODO implement
                break;
        }
    }

    private void onLoginPress(){
        RequestParams rp = new RequestParams();
        rp.add("email", "haolk"); rp.add("password", "123456");

        HttpUtils.post("user/login", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("LOGINRESPONSE", String.valueOf(response));
                Gson gson = new Gson();

                LoginResponse loginResponse = gson.fromJson(String.valueOf(response), LoginResponse.class);
                User user = (User) loginResponse.user;
                Log.d("CURRENT USER", user.name);

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("USER_INFO", gson.toJson(user));
                editor.commit();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }
}
