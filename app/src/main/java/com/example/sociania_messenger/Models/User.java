package com.example.sociania_messenger.Models;

public class User {
    String profile_pic;
    String userName;
    String mail;
    String password;
    String userId;
    String lastMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public User(String profile_pic, String userName, String mail, String password, String userId, String lastMessage, String status) {
        this.profile_pic = profile_pic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    public User() {

    }

    // SignUp Constructor

    public User(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


    public void getUserId(String key) {
    }
}
