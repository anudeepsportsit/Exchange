package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class SelectionDetailsTO {

    @JsonProperty(SportsBookConstants.BETS)
    private List<BetDetailsTO> betDetailsList;
    private String sportName;
    private String eventName;
    private String marketName;
    private String startTime;
    private String settledDate;
    private BigDecimal amount;
    private Integer isProfit;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(Integer isProfit) {
        this.isProfit = isProfit;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(String settledDate) {
        this.settledDate = settledDate;
    }

    public List<BetDetailsTO> getBetDetailsList() {
        return betDetailsList;
    }

    public void setBetDetailsList(List<BetDetailsTO> betDetailsList) {
        this.betDetailsList = betDetailsList;
    }
}
