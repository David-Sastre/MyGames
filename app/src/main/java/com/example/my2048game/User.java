package com.example.my2048game;

public class User {
    int id;
    String username, password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public boolean isNull(){
        if (username.equals("") && password.equals("")){
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return "Usuario: ("+
                "Id: " + id +
                ", Usuario: " + username +
                ", Password: " + password +")";
    }

    public User() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
