package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

public class AdminDepositDetailsTO {

    private String createdDate;
    private Double depositAmount;
    private Double conversionRate;
    private Double amountPoints;
    private Double balancePoints;
    private String reference;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Double getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(Double amountPoints) {
        this.amountPoints = amountPoints;
    }

    public Double getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(Double balancePoints) {
        this.balancePoints = balancePoints;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
