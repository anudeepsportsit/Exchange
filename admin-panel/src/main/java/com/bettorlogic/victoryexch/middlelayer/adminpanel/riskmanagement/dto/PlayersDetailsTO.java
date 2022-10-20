package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PlayersDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userExposure;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userAmount;

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

    public String getUserExposure() {
        return userExposure;
    }

    public void setUserExposure(String userExposure) {
        this.userExposure = userExposure;
    }

    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }
}
