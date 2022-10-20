package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class UserCoinDetailsTO {

    private Integer stake;
    private Integer isHighlight;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userToken;
    private List<CoinDetailsTO> userCoinsDetailsList;
    private Integer[] userCoins;

    public Integer getStake() {
        return stake;
    }

    public void setStake(Integer stake) {
        this.stake = stake;
    }

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<CoinDetailsTO> getUserCoinsDetailsList() {
        return userCoinsDetailsList;
    }

    public void setUserCoinsDetailsList(List<CoinDetailsTO> userCoinsDetailsList) {
        this.userCoinsDetailsList = userCoinsDetailsList;
    }

    public Integer[] getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(Integer[] userCoins) {
        this.userCoins = userCoins;
    }

    @Override
    public String toString() {
        return "UserCoinDetails{" +
                "userCoinsDetailsList=" + userCoinsDetailsList +
                '}';
    }

}
