package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class UserBalanceDetailsTO {

    @JsonIgnore
    private String userToken;

    @JsonIgnore
    private Double transactionAmount;

    @JsonIgnore
    private String fromTo;

    @JsonIgnore
    private String transactionType;

    @JsonIgnore
    private String transactionRemarks;

    @JsonIgnore
    private Integer transactionTypeId;

    private Boolean isBetPlaced = false;

    @JsonIgnore
    private String remarks;

    private Double remainingBalance;

    private List<BetSlipResponseTO> betSlipResponseList;

    public Boolean getBetPlaced() {
        return isBetPlaced;
    }

    public void setBetPlaced(Boolean betPlaced) {
        isBetPlaced = betPlaced;
    }

    public List<BetSlipResponseTO> getBetSlipResponseList() {
        return betSlipResponseList;
    }

    public void setBetSlipResponseList(List<BetSlipResponseTO> betSlipResponseList) {
        this.betSlipResponseList = betSlipResponseList;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionRemarks() {
        return transactionRemarks;
    }

    public void setTransactionRemarks(String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }

    public Boolean getIsBetPlaced() {
        return isBetPlaced;
    }

    public void setIsBetPlaced(Boolean isBetPlaced) {
        this.isBetPlaced = isBetPlaced;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(Double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}