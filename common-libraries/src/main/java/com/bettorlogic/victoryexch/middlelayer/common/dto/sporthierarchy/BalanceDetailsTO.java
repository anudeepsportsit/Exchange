package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

public class BalanceDetailsTO {

    private Double userBalance;
    private Double exposure;
    private Integer currencyId;
    private String currencyShortName;
    private String timeZone;

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyShortName() {
        return currencyShortName;
    }

    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "BalanceDetailsTO{" +
                "userBalance=" + userBalance +
                ", exposure=" + exposure +
                ", currencyId=" + currencyId +
                ", currencyShortName='" + currencyShortName + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }

}