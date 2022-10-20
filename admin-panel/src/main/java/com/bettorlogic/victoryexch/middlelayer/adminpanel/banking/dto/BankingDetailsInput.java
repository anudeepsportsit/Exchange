package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

public class BankingDetailsInput {
    private Integer userId;
    private Character transactionType;
    private double amount;
    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Character getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Character transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
