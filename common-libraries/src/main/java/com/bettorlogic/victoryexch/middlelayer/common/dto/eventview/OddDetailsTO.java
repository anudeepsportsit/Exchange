package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OddDetailsTO {

    private Double odds;

    @JsonProperty(SportsBookConstants.ID)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer outcomeId;

    @JsonProperty(SportsBookConstants.NAME)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String outcomeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double outcome;

    @JsonIgnore
    private Integer marketSuspended;

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public Double getOutcome() {
        return outcome;
    }

    public void setOutcome(Double outcome) {
        this.outcome = outcome;
    }
}