package com.example.mapwithmarker;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class RegisterView {
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText registerPasswordEdit;
    private Button registerButton;
    private RequestSender requestSender;
    private MapsMarkerActivity mainActivity;

    RegisterView(EditText firstNameEdit, EditText lastNameEdit, EditText emailEdit, EditText registerPasswordEdit, Button registerButton, RequestSender requestSender, MapsMarkerActivity mainActivity){
        this.emailEdit = emailEdit;
        this.firstNameEdit = firstNameEdit;
        this.lastNameEdit = lastNameEdit;
        this.registerPasswordEdit = registerPasswordEdit;
        this.registerButton = registerButton;
        this.requestSender = requestSender;
        this.mainActivity = mainActivity;
    }

    public void init() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSender.sendRegister(RegisterParams
                        .builder()
                        .setEmail(emailEdit.getText().toString())
                        .setPassword(registerPasswordEdit.getText().toString())
                        .setFirstName(firstNameEdit.getText().toString())
                        .setLastName(lastNameEdit.getText().toString())
                        .setResponseListener(new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        })
                        .setErrorListener(new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mainActivity.toast(error);
                            }
                        })
                        .build()
                );
                mainActivity.setView(ViewCode.ACTIVATION);
            }
        });
    }
}
