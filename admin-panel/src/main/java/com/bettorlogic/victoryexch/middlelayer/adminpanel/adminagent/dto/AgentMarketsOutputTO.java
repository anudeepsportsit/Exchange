package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AgentMarketsOutputTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String agentName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketResult;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean marketStatus;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getMarketResult() {
        return marketResult;
    }

    public void setMarketResult(String marketResult) {
        this.marketResult = marketResult;
    }

    public boolean isMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(boolean marketStatus) {
        this.marketStatus = marketStatus;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sportId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sportName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer eventId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String eventName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketSelection;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer agentMarketId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String selectionName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AgentFancyOutcomeTO> outcomes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer betDelay;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minStake;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxStake;

    public Integer getMinStake() {
        return minStake;
    }

    public void setMinStake(Integer minStake) {
        this.minStake = minStake;
    }

    public Integer getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(Integer maxStake) {
        this.maxStake = maxStake;
    }

    public Integer getBetDelay() {
        return betDelay;
    }

    public void setBetDelay(Integer betDelay) {
        this.betDelay = betDelay;
    }

    public Integer getAgentMarketId() {
        return agentMarketId;
    }

    public void setAgentMarketId(Integer agentMarketId) {
        this.agentMarketId = agentMarketId;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public List<AgentFancyOutcomeTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<AgentFancyOutcomeTO> outcomes) {
        this.outcomes = outcomes;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    @JsonProperty("isMarketSuspended")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isMarketSuspended;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getMarketSelection() {
        return marketSelection;
    }

    public void setMarketSelection(String marketSelection) {
        this.marketSelection = marketSelection;
    }


    public void setMarketSuspended(Boolean marketSuspended) {
        isMarketSuspended = marketSuspended;
    }
}
