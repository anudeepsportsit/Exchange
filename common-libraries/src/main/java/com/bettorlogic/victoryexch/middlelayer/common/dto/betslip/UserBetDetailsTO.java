package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import java.math.BigDecimal;

public class UserBetDetailsTO {

    private Integer roleId;
    private Integer statusId;
    private BigDecimal balance;
    private BigDecimal exposureLimit;
    private BigDecimal userExposure;
    private Integer userId;
    private BigDecimal settledBalance;

    public BigDecimal getSettledBalance() {
        return settledBalance;
    }

    public void setSettledBalance(BigDecimal settledBalance) {
        this.settledBalance = settledBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(BigDecimal exposureLimit) {
        this.exposureLimit = exposureLimit;
    }

    public BigDecimal getUserExposure() {
        return userExposure;
    }

    public void setUserExposure(BigDecimal userExposure) {
        this.userExposure = userExposure;
    }
}
