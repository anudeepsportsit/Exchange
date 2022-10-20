package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BettingProfitLossOutput {
    private String sportName;
    private String eventName;
    private String marketName;
    private String startTime;
    private String settledDate;

    @JsonProperty(SportsBookConstants.BET_DETAILS)
    private ProfitLossBetDetailsTO profitLossBetDetails;

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

    public ProfitLossBetDetailsTO getProfitLossBetDetails() {
        return profitLossBetDetails;
    }

    public void setProfitLossBetDetails(ProfitLossBetDetailsTO profitLossBetDetails) {
        this.profitLossBetDetails = profitLossBetDetails;
    }

    @Override
    public String toString() {
        return "BettingProfitLossOutput{" +
                "sportName='" + sportName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", marketName='" + marketName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", settledDate='" + settledDate + '\'' +
                ", profitLossBetDetails=" + profitLossBetDetails +
                '}';
    }
}