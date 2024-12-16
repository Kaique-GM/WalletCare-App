package com.financeX.utils;

public class Session {

    private static Session instance;
    private String username;
    private Integer userID;

    private Session(){

    }

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void clearSession(){
        username = null;
        userID = 0;
    }
}
