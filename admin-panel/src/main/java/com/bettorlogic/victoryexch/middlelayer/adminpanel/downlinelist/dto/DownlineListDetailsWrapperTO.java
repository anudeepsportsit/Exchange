package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DownlineListDetailsWrapperTO {

    private Double balance;
    private Double availableBalance;
    private Double totalBalance;
    private Double totalExposure;
    private Double totalAvailableBalance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double totalProfitLoss;

    public Double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(Double totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double creditLimmit;

    public Double getCreditLimmit() {
        return creditLimmit;
    }

    public void setCreditLimmit(Double creditLimmit) {
        this.creditLimmit = creditLimmit;
    }

    public Double getCreditDistributed() {
        return creditDistributed;
    }

    public void setCreditDistributed(Double creditDistributed) {
        this.creditDistributed = creditDistributed;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double creditDistributed;

    @JsonProperty(DownLineListConstants.DOWN_LINE_LIST_USER_DETAILS)
    private List<DownLineUserDetailsTO> userDetailsList = new ArrayList<>();

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalExposure() {
        return totalExposure;
    }

    public void setTotalExposure(Double totalExposure) {
        this.totalExposure = totalExposure;
    }

    public List<DownLineUserDetailsTO> getUserDetailsList() {
        return userDetailsList;
    }

    public void setUserDetailsList(List<DownLineUserDetailsTO> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    public Double getTotalAvailableBalance() {
        return totalAvailableBalance;
    }

    public void setTotalAvailableBalance(Double totalAvailableBalance) {
        this.totalAvailableBalance = totalAvailableBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }
}