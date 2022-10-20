package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

import java.util.List;

public class BankingLogsOutputTO {
    private String userName;
    private List<BankingDetailsTO> bankingLogs;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<BankingDetailsTO> getBankingLogs() {
        return bankingLogs;
    }

    public void setBankingLogs(List<BankingDetailsTO> bankingLogs) {
        this.bankingLogs = bankingLogs;
    }
}