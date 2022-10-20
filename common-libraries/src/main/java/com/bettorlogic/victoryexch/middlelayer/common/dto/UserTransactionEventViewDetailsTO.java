package com.bettorlogic.victoryexch.middlelayer.common.dto;

import java.math.BigDecimal;

public class UserTransactionEventViewDetailsTO {

    private String transactionDate;
    private Long betId;
    private String selectName;
    private String oddType;
    private BigDecimal odds;
    private BigDecimal betStake;
    private BigDecimal betReturns;
    private BigDecimal transactionAmount;
    private BigDecimal profitLossAmount;
    private BigDecimal balance;
    private BigDecimal commission;
    private String betStatus;
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public BigDecimal getBetStake() {
        return betStake;
    }

    public void setBetStake(BigDecimal betStake) {
        this.betStake = betStake;
    }

    public BigDecimal getBetReturns() {
        return betReturns;
    }

    public void setBetReturns(BigDecimal betReturns) {
        this.betReturns = betReturns;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }
}
