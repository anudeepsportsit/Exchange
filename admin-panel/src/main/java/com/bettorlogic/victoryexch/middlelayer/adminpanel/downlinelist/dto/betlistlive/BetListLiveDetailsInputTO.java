package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotBlank;

public class BetListLiveDetailsInputTO {

    @NotBlank(message = ExceptionConstants.INVALID_LOGIN_TOKEN)
    private String userToken;
    private Integer orderOfDisplay;
    private Integer order;
    private Integer betStatus;
    private Integer[] sportId;
    private Integer transactionLimit;


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getOrderOfDisplay() {
        return orderOfDisplay;
    }

    public void setOrderOfDisplay(Integer orderOfDisplay) {
        this.orderOfDisplay = orderOfDisplay;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(Integer betStatus) {
        this.betStatus = betStatus;
    }

    public Integer[] getSportId() {
        return sportId;
    }

    public void setSportId(Integer[] sportId) {
        this.sportId = sportId;
    }

    public Integer getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Integer transactionLimit) {
        this.transactionLimit = transactionLimit;
    }
}
