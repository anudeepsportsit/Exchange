package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class FancyDropDownWrapperTO {
    List<FancyDownDownOutcomesTO> outcomes;
    private Integer eventId;
    private String marketName;
    private Double minStake;
    private Double maxStake;

    public List<FancyDownDownOutcomesTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<FancyDownDownOutcomesTO> outcomes) {
        this.outcomes = outcomes;
    }

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

    public Double getMinStake() {
        return minStake;
    }

    public void setMinStake(Double minStake) {
        this.minStake = minStake;
    }

    public Double getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(Double maxStake) {
        this.maxStake = maxStake;
    }
}
