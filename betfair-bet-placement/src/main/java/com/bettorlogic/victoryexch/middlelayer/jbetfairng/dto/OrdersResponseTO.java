package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class OrdersResponseTO {
    private String betId;
    private String marketId;
    private String status;

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
