package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountStatementTO {

    private String updatedDateTime;

    @JsonProperty(AdminPanelConstants.USER_BALANCE)
    private String userBalance;

    @JsonProperty(AdminPanelConstants.DEPOSIT)
    private Double depositAmount;

    @JsonProperty(AdminPanelConstants.WITHDRAW)
    private Double withdrawAmount;

    @JsonProperty(AdminPanelConstants.REMARK)
    private String userRemarks;

    @JsonProperty(AdminPanelConstants.FROM_ACCOUNT)
    private String fromAccountName;

    @JsonProperty(AdminPanelConstants.TO_ACCOUNT)
    private String toAccountName;

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getUserRemarks() {
        return userRemarks;
    }

    public void setUserRemarks(String userRemarks) {
        this.userRemarks = userRemarks;
    }

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    @Override
    public String toString() {
        return "AccountStatement {" +
                "updatedDateTime='" + updatedDateTime + '\'' +
                ", depositAmount=" + depositAmount +
                ", withdrawAmount=" + withdrawAmount +
                ", userRemarks='" + userRemarks + '\'' +
                ", fromAccountName='" + fromAccountName + '\'' +
                ", toAccountName='" + toAccountName + '\'' +
                '}';
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }
}