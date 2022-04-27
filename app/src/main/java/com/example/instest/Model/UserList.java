package com.example.instest.Model;

import java.util.ArrayList;

public class UserList {
    private User user;
    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }
}
