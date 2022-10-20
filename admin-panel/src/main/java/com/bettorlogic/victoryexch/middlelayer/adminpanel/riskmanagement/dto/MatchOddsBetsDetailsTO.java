package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class MatchOddsBetsDetailsTO {

    private Integer eventId;
    private String eventName;
    private String marketName;
    private String marketDate;
    private String outcomeName;
    private String odds;
    private Integer userId;
    private String userName;
    private String stakeAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oddType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String selectionName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public String getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(String stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public String getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(String marketDate) {
        this.marketDate = marketDate;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
