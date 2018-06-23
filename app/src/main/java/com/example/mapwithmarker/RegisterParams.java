package com.example.mapwithmarker;

import com.android.volley.Response;

public class RegisterParams{
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Response.Listener<String> responseListener;
    private final Response.ErrorListener errorListener;

    RegisterParams(String firstName, String lastName, String email, String password, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        this.email = email;
        this.errorListener = errorListener;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.responseListener = responseListener;
    }

    static Builder builder(){
        return new Builder();
    }

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public Response.Listener<String> getResponseListener() {
        return responseListener;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Response.Listener<String> responseListener;
        private Response.ErrorListener errorListener;


        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setResponseListener(Response.Listener<String> responseListener) {
            this.responseListener = responseListener;
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public RegisterParams build(){
            return new RegisterParams(firstName, lastName, email, password, responseListener, errorListener);
        }
    }
}
