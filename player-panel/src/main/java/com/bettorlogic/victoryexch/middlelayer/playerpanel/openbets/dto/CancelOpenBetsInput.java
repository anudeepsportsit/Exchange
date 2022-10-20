package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CancelOpenBetsInput {

    @NotEmpty(message = ExceptionConstants.BET_IDS_NULL)
    private List<Integer> betIdList;

    @NotEmpty(message = ExceptionConstants.INVALID_LOGIN_TOKEN)
    private String userToken;

    public List<Integer> getBetIdList() {
        return betIdList;
    }

    public void setBetIdList(List<Integer> betIdList) {
        this.betIdList = betIdList;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
