package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserPasswordUpdateDetailsTO {

    private String userToken;
    private String loginPassword;
    private String currentPassword;
    private String newPassword;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String emailId;
    private String isUser;

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
