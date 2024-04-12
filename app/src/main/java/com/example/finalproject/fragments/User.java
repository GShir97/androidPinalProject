package com.example.finalproject.fragments;

public class User {
    private String email;
    private Boolean isDj;
    private String userId;

    public User() {
    }

    public User(String email, Boolean isDj, String userId) {
        this.email = email;
        this.isDj = isDj;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDj() {
        return isDj;
    }

    public void setDj(Boolean dj) {
        isDj = dj;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}



