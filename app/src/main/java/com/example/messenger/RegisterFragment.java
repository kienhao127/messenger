package com.example.messenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Button;

import com.example.messenger.model.response.RegisterReponse;
import com.example.messenger.utils.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.navigation.Navigation;
import cz.msebera.android.httpclient.Header;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Toolbar myToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("Đăng ký");
        toolbar.setBackgroundColor(Color.BLACK);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.register_button).setOnClickListener(this);
    }

    private EditText getEmail(){
        return (EditText) getView().findViewById(R.id.email);
    }

    private EditText getPassword(){
        return (EditText) getView().findViewById(R.id.password);
    }

    private EditText getRePpassword(){
        return (EditText) getView().findViewById(R.id.rePpassword);
    }

    private EditText getEditTextFullname(){
        return (EditText) getView().findViewById(R.id.editTextFullname);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                String validateString = validate();
                if (validateString.isEmpty()){
                    onRegisterPress();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage(validateString);
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
        }
    }

    private String validate(){
        if (getEditTextFullname().getText().toString().isEmpty() || getPassword().getText().toString().isEmpty() || getRePpassword().getText().toString().isEmpty() || getEmail().getText().toString().isEmpty()){
            return "Thông tin không được để trống";
        }
        if (!getPassword().getText().toString().equals(getRePpassword().getText().toString())){
            return "Mật khẩu nhập lại không khớp";
        }
        return "";
    }

    private void onRegisterPress(){
        RequestParams rp = new RequestParams();
        rp.add("email", getEmail().getText().toString()); rp.add("password", getPassword().getText().toString());

        HttpUtils.post("register", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("REGISTER REPONSE", String.valueOf(response));
                Gson gson = new Gson();

                RegisterReponse registerReponse = gson.fromJson(String.valueOf(response), RegisterReponse.class);
                if (registerReponse.result == 0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage(registerReponse.msg);
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Đăng ký thành công!");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    getActivity().onBackPressed();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }
}
