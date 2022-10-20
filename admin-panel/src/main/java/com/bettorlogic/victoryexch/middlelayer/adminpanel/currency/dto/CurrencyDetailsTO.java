package com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CurrencyDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currencyId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currencyName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currencyCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double conversionRate;

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
