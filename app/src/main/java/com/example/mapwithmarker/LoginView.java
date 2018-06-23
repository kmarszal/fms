package com.example.mapwithmarker;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mapwithmarker.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginView {
    private Button loginButton;
    private EditText loginEdit;
    private EditText passwordEdit;
    private RequestSender requestSender;
    private MapsMarkerActivity mainActivity;
    private Button newAccountButton;
    private Gson gson;

    public LoginView(Button loginButton, Button newAccountButton, EditText loginEdit, EditText passwordEdit, RequestSender requestSender, MapsMarkerActivity mainActivity){
        this.loginButton = loginButton;
        this.newAccountButton = newAccountButton;
        this.loginEdit = loginEdit;
        this.mainActivity = mainActivity;
        this.passwordEdit = passwordEdit;
        this.requestSender = requestSender;
        this.gson = new GsonBuilder().create();
    }

    public void init(){
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setView(ViewCode.REGISTER);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSender.sendLogin(
                        LoginParams.builder()
                                .setEmail(loginEdit.getText().toString())
                                .setPassword(passwordEdit.getText().toString())
                                .setErrorListener(new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        mainActivity.toast(error);
                                    }
                                })
                                .setResponseListener(
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                UserInfo info = gson.fromJson(response, UserInfo.class);
                                                requestSender.setUserInfo(info);
                                                mainActivity.toast(R.string.login_successful);
                                                mainActivity.setView(ViewCode.MAP);
                                                mainActivity.startMapRefresher();
                                                mainActivity.sendRequestForRides();
                                            }
                                        })
                                .build()
                );
            }
        });
    }
}
