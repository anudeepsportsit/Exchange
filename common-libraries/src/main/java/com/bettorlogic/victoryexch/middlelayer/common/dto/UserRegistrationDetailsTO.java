package com.bettorlogic.victoryexch.middlelayer.common.dto;


import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotBlank;

public class UserRegistrationDetailsTO {

    @NotBlank(message = ExceptionConstants.INVALID_USER_NAME_ENTERED)
    private String userName;

    @NotBlank(message = ExceptionConstants.INVALID_EMAIL_ID_ENTERED)
    private String emailId;

    @NotBlank(message = ExceptionConstants.INVALID_PASSWORD_ENTERED)
    private String password;

    private String phoneNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}