package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto;

public class CreditLimitTO {

    private Integer userId;
    private Double creditLimit;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
