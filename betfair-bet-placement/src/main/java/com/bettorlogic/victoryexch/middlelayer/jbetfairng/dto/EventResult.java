package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class EventResult {
    private EventTO event;
    private int marketCount;

    public EventTO getEvent() {
        return event;
    }

    public void setEvent(EventTO event) {
        this.event = event;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }

}
