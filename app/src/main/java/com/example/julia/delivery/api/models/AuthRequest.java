package com.example.julia.delivery.api.models;

/**
 * Created by Lavrov on 16.05.2018.
 */

public class AuthRequest {
    private String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
