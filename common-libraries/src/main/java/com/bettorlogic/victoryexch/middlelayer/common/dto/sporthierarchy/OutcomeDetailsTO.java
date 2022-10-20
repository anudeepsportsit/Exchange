package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutcomeDetailsTO {

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double layOdds;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double backOdds;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clOutcomeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.ID)
    private Integer outcomeId;

    @JsonProperty(SportsBookConstants.NAME)
    private String outcomeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double backOddsSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double layOddsSize;

    @JsonIgnore
    private int outcomeOrder;

    @JsonIgnore
    private Integer marketSuspended;

    public Double getBackOddsSize() {
        return backOddsSize;
    }

    public void setBackOddsSize(Double backOddsSize) {
        this.backOddsSize = backOddsSize;
    }

    public Double getLayOddsSize() {
        return layOddsSize;
    }

    public void setLayOddsSize(Double layOddsSize) {
        this.layOddsSize = layOddsSize;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Double getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(Double layOdds) {
        this.layOdds = layOdds;
    }

    public Double getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(Double backOdds) {
        this.backOdds = backOdds;
    }

    public String getClOutcomeId() {
        return clOutcomeId;
    }

    public void setClOutcomeId(String clOutcomeId) {
        this.clOutcomeId = clOutcomeId;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public int getOutcomeOrder() {
        return outcomeOrder;
    }

    public void setOutcomeOrder(int outcomeOrder) {
        this.outcomeOrder = outcomeOrder;
    }
}
