package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public class BankingDetailsOutput {
    private Integer userId;
    private String name;
    private BigDecimal balance;
    private BigDecimal availableDW;
    private BigDecimal exposure;
    private BigDecimal exposureLimit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isDeposited;

    private BigDecimal creditLimit;

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private BigDecimal adminBalanceConversion;

    public BigDecimal getAdminBalanceConversion() {
        return adminBalanceConversion;
    }

    public void setAdminBalanceConversion(BigDecimal adminBalanceConversion) {
        this.adminBalanceConversion = adminBalanceConversion;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAvailableDW() {
        return availableDW;
    }

    public void setAvailableDW(BigDecimal availableDW) {
        this.availableDW = availableDW;
    }

    public BigDecimal getExposure() {
        return exposure;
    }

    public void setExposure(BigDecimal exposure) {
        this.exposure = exposure;
    }

    public BigDecimal getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(BigDecimal exposureLimit) {
        this.exposureLimit = exposureLimit;
    }

    public Boolean getDeposited() {
        return isDeposited;
    }

    public void setDeposited(Boolean deposited) {
        isDeposited = deposited;
    }
}
