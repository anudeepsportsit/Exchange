package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class TransactionDetailsInputTO {
    private String userToken;
    private String transactionType;
    private String transactionDate;
    private String betPlaced;
    private Integer eventId;
    private String outcomeName;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }
}
