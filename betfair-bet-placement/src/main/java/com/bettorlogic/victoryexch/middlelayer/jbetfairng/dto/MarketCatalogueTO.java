package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class MarketCatalogueTO {

    private String marketId;
    private String marketName;
    private MarketDescriptionTO description;
    private List<RunnerCatalogTO> runners;
    private EventTypeTO eventType;
    private CompetitionTO competition;
    private EventTO event;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public MarketDescriptionTO getDescription() {
        return description;
    }

    public void setDescription(MarketDescriptionTO description) {
        this.description = description;
    }

    public List<RunnerCatalogTO> getRunners() {
        return runners;
    }

    public void setRunners(List<RunnerCatalogTO> runners) {
        this.runners = runners;
    }

    public EventTypeTO getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeTO eventType) {
        this.eventType = eventType;
    }

    public CompetitionTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionTO competition) {
        this.competition = competition;
    }

    public EventTO getEvent() {
        return event;
    }

    public void setEvent(EventTO event) {
        this.event = event;
    }

    public String toString() {
        return getMarketName();
    }

}
