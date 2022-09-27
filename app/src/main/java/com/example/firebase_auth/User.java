package com.example.firebase_auth;

public class User {
    public String fullName;
    public String email;
    public User(String email) {

    }
    public User (String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
