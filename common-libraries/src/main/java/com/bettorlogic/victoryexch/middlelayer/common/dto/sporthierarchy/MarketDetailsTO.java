package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MarketDetailsTO {
    private Integer marketGroupId;
    private String subMarketValue;
    private String marketName;
    private String clMarketId;
    private Double minStake;
    private Double maxStake;

    @JsonProperty(SportsBookConstants.IS_SUSPENDED)
    private Integer marketSuspendedFlag;

    @JsonProperty(SportsBookConstants.IS_BLOCKED)
    private Integer marketBlockedFlag = 0;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isFancy;
    @JsonProperty(SportsBookConstants.OUTCOMES)
    private List<OutcomeDetailsTO> outcomesList = new ArrayList<>();

    @JsonIgnore
    private List<EventViewOutcomeDetailsTO> eventViewOutcomeDetailsList = new ArrayList<>();

    public Integer getMarketSuspendedFlag() {
        return marketSuspendedFlag;
    }

    public void setMarketSuspendedFlag(Integer marketSuspendedFlag) {
        this.marketSuspendedFlag = marketSuspendedFlag;
    }

    public Integer getMarketBlockedFlag() {
        return marketBlockedFlag;
    }

    public void setMarketBlockedFlag(Integer marketBlockedFlag) {
        this.marketBlockedFlag = marketBlockedFlag;
    }

    public Double getMinStake() {
        return minStake;
    }

    public void setMinStake(double minStake) {
        this.minStake = minStake;
    }

    public Double getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(double maxStake) {
        this.maxStake = maxStake;
    }

    public Integer getIsFancy() {
        return isFancy;
    }

    public void setIsFancy(Integer isFancy) {
        this.isFancy = isFancy;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public String getSubMarketValue() {
        return subMarketValue;
    }

    public void setSubMarketValue(String subMarketValue) {
        this.subMarketValue = subMarketValue;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public List<OutcomeDetailsTO> getOutcomesList() {
        return outcomesList;
    }

    public void setOutcomesList(List<OutcomeDetailsTO> outcomesList) {
        this.outcomesList = outcomesList;
    }

    public void setOutcomeDetailsList(List<OutcomeDetailsTO> outcomesList) {
        this.outcomesList = outcomesList;
    }

    public String getClMarketId() {
        return clMarketId;
    }

    public void setClMarketId(String clMarketId) {
        this.clMarketId = clMarketId;
    }

    public List<EventViewOutcomeDetailsTO> getEventViewOutcomeDetailsList() {
        return eventViewOutcomeDetailsList;
    }

    public void setEventViewOutcomeDetailsList(List<EventViewOutcomeDetailsTO> eventViewOutcomeDetailsList) {
        this.eventViewOutcomeDetailsList = eventViewOutcomeDetailsList;
    }
}
