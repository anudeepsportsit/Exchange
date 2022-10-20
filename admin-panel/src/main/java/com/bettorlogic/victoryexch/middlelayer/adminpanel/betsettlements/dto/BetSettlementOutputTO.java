package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto;

public class BetSettlementOutputTO {
    private Integer betSettlementId;
    private Integer eventId;
    private String eventName;
    private String marketName;
    private String submarketName;
    private String outcomeName;

    public Integer getBetSettlementId() {
        return betSettlementId;
    }

    public void setBetSettlementId(Integer betSettlementId) {
        this.betSettlementId = betSettlementId;
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

    public String getSubmarketName() {
        return submarketName;
    }

    public void setSubmarketName(String submarketName) {
        this.submarketName = submarketName;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }
}
