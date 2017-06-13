package com.mengka.springboot.moxie_02.component;

public class AuthToken {
    private String apiKey;
    private String token;

    public AuthToken(String apiKey, String token) {
        this.apiKey = apiKey;
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
