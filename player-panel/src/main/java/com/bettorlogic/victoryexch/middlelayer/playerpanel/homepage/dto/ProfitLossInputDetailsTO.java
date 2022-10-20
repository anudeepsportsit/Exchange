package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotEmpty;

public class ProfitLossInputDetailsTO {

    @NotEmpty(message = ExceptionConstants.SESSION_EXPIRED)
    private String userToken;
    private String startDate;
    private String endDate;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
