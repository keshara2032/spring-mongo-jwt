package com.springboot.springmongooauthjwt.model;


public class JWTResponse {
    public String token;

    public JWTResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
