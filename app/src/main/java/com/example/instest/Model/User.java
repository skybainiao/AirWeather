package com.example.instest.Model;

public class User {
    private String username;
    private String password;

    public User(String username,String password){
        this.username = username;
        this.password = password;
    }

    public User(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
