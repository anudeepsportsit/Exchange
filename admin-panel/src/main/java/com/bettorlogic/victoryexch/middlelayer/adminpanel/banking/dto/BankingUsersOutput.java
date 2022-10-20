package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

public class BankingUsersOutput {
    private Integer userid;
    private String userName;
    private String message;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}