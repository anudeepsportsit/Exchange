package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

public class FancyMarketsSuspendDetailsTO {
    private Integer eventId;
    private String marketName;
    private Integer isActive;
    private Integer[] marketsList;
    private Integer isSuspend;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer[] getMarketsList() {
        return marketsList;
    }

    public void setMarketsList(Integer[] marketsList) {
        this.marketsList = marketsList;
    }

    public Integer getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(Integer isSuspend) {
        this.isSuspend = isSuspend;
    }
}
