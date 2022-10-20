package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

import java.util.List;

public class BankingTansactionsOutputTO {
    private List<BankingUsersOutput> successUser;
    private List<BankingUsersOutput> failedUser;

    public List<BankingUsersOutput> getSuccessUser() {
        return successUser;
    }

    public void setSuccessUser(List<BankingUsersOutput> successUser) {
        this.successUser = successUser;
    }

    public List<BankingUsersOutput> getFailedUser() {
        return failedUser;
    }

    public void setFailedUser(List<BankingUsersOutput> failedUser) {
        this.failedUser = failedUser;
    }
}