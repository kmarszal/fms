package com.example.mapwithmarker;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapwithmarker.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class RequestSender {
    private final static String LOGIN_URL = "http://88.99.191.188:8170/api/Authorization/Login";
    private final static String ACCOUNTS_URL = "http://88.99.191.188:8170/api/Accounts";
    private final static String CARS_URL = "http://88.99.191.188:8170/api/Cars";
    private final static String RIDES_URL = "http://88.99.191.188:8170/api/Rides";
    private final static String TELEMETRY_DATA_URL = "http://88.99.191.188:8170/api/TelemetryData";
    private final static String MAIL_URL = ACCOUNTS_URL + "/IsMailAvailable";
    private final RequestQueue queue;
    private UserInfo userInfo;

    public RequestSender(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void sendLogin(final LoginParams params){
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL,
                params.getResponseListener(),
                params.getErrorListener()){
                    @Override
                    public byte[] getBody(){
                        String body = "{" +
                                "\"email\": \"" + params.getEmail() + "\"," +
                                "\"password\": \"" + params.getPassword() + "\"" +
                                "}";
                        return body.getBytes();
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
        };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        15 * 1000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void sendRegister(final RegisterParams params){
        StringRequest request = new StringRequest(Request.Method.POST, ACCOUNTS_URL, params.getResponseListener(), params.getErrorListener()){
            @Override
            public byte[] getBody(){
                String body = "{" +
                        "\"email\": \"" + params.getEmail() + "\"," +
                        "\"password\": \"" + params.getPassword() + "\"," +
                        "\"firstName\": \"" + params.getFirstName() + "\"," +
                        "\"lastName\": \"" + params.getLastName() + "\"" +
                        "}";
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(request);
    }

    public void sendRequestForCars(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, CARS_URL, responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + userInfo.getAccessToken());
                return headers;
            }
        };
        queue.add(request);
    }

    public void sendRequestForRides(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, RIDES_URL, responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + userInfo.getAccessToken());
                return headers;
            }
        };
        queue.add(request);
    }

    public void sendRequestForTelemetryData(Response.Listener<String> responseListener, Response.ErrorListener errorListener, final int rideId) {
        StringRequest request = new StringRequest(Request.Method.GET, TELEMETRY_DATA_URL, responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + userInfo.getAccessToken());
                return headers;
            }
            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("rideId", Integer.toString(rideId));
                return params;
            }
        };
        queue.add(request);
    }

    public void isMailAvailable(final String email, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, MAIL_URL, responseListener, errorListener){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };
        queue.add(request);
    }

    public void setUserInfo(UserInfo info) {
        this.userInfo = info;
    }
}