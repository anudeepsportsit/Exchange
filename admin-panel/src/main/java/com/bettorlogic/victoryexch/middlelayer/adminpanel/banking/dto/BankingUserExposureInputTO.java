package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

public class BankingUserExposureInputTO {
    private String userLoginToken;
    private Integer userId;
    private Double exposureLimit;

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(Double exposureLimit) {
        this.exposureLimit = exposureLimit;
    }
}
