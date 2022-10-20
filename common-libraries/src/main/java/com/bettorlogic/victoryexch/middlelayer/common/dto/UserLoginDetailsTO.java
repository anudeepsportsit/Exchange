package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotBlank;

public class UserLoginDetailsTO {
    @NotBlank(message = ExceptionConstants.INVALID_EMAIL_ID_ENTERED)
    private String emailId;

    @NotBlank(message = ExceptionConstants.INVALID_PASSWORD_ENTERED)
    private String password;

    private Integer userId;
    private String userName;
    private Double userBalance;
    private Double exposure;
    private String loginToken;
    private Integer roleId;
    private Integer statusId;
    private Integer currencyId;
    private String currencyCode;
    private String timeZone;
    private String sessionToken;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "UserLoginDetailsTO{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userBalance=" + userBalance +
                ", exposure=" + exposure +
                ", loginToken='" + loginToken + '\'' +
                ", roleId=" + roleId +
                ", statusId=" + statusId +
                '}';
    }
}
