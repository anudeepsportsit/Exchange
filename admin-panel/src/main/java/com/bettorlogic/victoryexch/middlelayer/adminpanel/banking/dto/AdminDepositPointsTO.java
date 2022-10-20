package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

import java.math.BigDecimal;

public class AdminDepositPointsTO {
    private String userLoginToken;
    private BigDecimal amount;
    private BigDecimal conversionRate;
    private BigDecimal transPoints;
    private String reference;

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getTransPoints() {
        return transPoints;
    }

    public void setTransPoints(BigDecimal transPoints) {
        this.transPoints = transPoints;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
