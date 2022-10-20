package com.bettorlogic.victoryexch.middlelayer.common.dto;
import java.math.BigDecimal;

public class UserTransactionDetailsTO {
    private Integer transactionId;
    private String transactionDate;
    private String date;
    private String time;
    private String transactionType;
    private Integer eventId;
    private String eventName;
    private String outcomeName;
    private BigDecimal profitLossAmount;
    private BigDecimal balance;
    private String betPlacedDate;

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getProfitLossAmount() {
        return profitLossAmount;
    }

    public void setProfitLossAmount(BigDecimal profitLossAmount) {
        this.profitLossAmount = profitLossAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBetPlacedDate() {
        return betPlacedDate;
    }

    public void setBetPlacedDate(String betPlacedDate) {
        this.betPlacedDate = betPlacedDate;
    }
}
