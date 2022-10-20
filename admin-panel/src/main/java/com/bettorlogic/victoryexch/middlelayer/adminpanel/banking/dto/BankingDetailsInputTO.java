package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BankingDetailsInputTO {

    @NotNull(message = SportsBookConstants.BANKING_TRANSACTIONS_LIST_NULL)
    @NotEmpty
    private List<BankingDetailsInput> bankingDetailsList;

    @NotNull(message = SportsBookConstants.USER_LOGIN_TOKEN_NULL)
    private String userLoginToken;

    @NotNull(message = SportsBookConstants.USER_LOGIN_PASSWORD_NULL)
    private String userPassword;

    public List<BankingDetailsInput> getBankingDetailsList() {
        return bankingDetailsList;
    }

    public void setBankingDetailsInputList(List<BankingDetailsInput> bankingDetailsList) {
        this.bankingDetailsList = bankingDetailsList;
    }

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
