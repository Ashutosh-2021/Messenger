package com.example.sociania_messenger.Models;

public class MessageModel {

    // Id of message to get the message in firebase
    String uId, message;
    Long TimesTemp;


    // Create a constructor
    public MessageModel(String uId, String message, Long timesTemp) {
        this.uId = uId;
        this.message = message;
        TimesTemp = timesTemp;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }
    // Empty Constructor
    public MessageModel(){

    }

    // Create Getter and Setter Method
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimesTemp() {
        return TimesTemp;
    }

    public void setTimesTemp(Long timesTemp) {
        TimesTemp = timesTemp;
    }
}
