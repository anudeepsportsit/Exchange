package com.bettorlogic.victoryexch.middlelayer.common.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserTransactionDetailsWrapperTO {
    private BigDecimal userBalance;
    private Integer userTransactionsCount;

    public Integer getUserTransactionsCount() {
        return userTransactionsCount;
    }

    public void setUserTransactionsCount(Integer userTransactionsCount) {
        this.userTransactionsCount = userTransactionsCount;
    }

    private List<UserTransactionDetailsTO> userTransList;

    public List<UserTransactionDetailsTO> getUserTransList() {
        return userTransList;
    }

    public void setUserTransList(List<UserTransactionDetailsTO> userTransList) {
        this.userTransList = userTransList;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }
}

