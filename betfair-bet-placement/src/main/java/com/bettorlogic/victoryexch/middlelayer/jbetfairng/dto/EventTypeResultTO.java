package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


public class EventTypeResultTO {
    private EventTypeTO eventType;
    private int marketCount;

    public EventTypeTO getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeTO eventType) {
        this.eventType = eventType;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }

}
