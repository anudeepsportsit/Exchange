package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

public class BetHistoryFetchDetailsTO {
    private String userToken;
    private String fromDate;
    private String toDate;
    private Integer transactionType;
    private String betSlipStatus;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getBetSlipStatus() {
        return betSlipStatus;
    }

    public void setBetSlipStatus(String betSlipStatus) {
        this.betSlipStatus = betSlipStatus;
    }


}
