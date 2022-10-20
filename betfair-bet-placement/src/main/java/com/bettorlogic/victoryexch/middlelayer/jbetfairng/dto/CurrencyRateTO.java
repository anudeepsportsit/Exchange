package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class CurrencyRateTO {
    private String currencyCode;
    private double rate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

