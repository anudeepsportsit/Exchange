package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DownLineUserDetailsTO {
    private Integer userId;
    private String account;
    private Integer roleId;
    private Double balance;
    private Double playerBalance;
    private Double exposure;
    private Double availableBalance;
    private Double exposureLimit;
    private Integer statusId;
    private Double refProfitLoss;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double creditLimmit;

    public Double getCreditLimmit() {
        return creditLimmit;
    }

    public void setCreditLimmit(Double creditLimmit) {
        this.creditLimmit = creditLimmit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(Double playerBalance) {
        this.playerBalance = playerBalance;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(Double exposureLimit) {
        this.exposureLimit = exposureLimit;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Double getRefProfitLoss() {
        return refProfitLoss;
    }

    public void setRefProfitLoss(Double refProfitLoss) {
        this.refProfitLoss = refProfitLoss;
    }
}
