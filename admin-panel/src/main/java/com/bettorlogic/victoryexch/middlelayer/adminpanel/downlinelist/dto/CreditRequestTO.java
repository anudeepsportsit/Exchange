package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto;

import java.util.List;

public class CreditRequestTO {

    private String loginToken;
    private String password;
    private List<CreditLimitTO> creditList;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CreditLimitTO> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<CreditLimitTO> creditList) {
        this.creditList = creditList;
    }
}
