package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FancyBetDetailsTO {

    private Integer marketId;

    private String marketName;
    private Integer marketSuspended;
    @JsonProperty(SportsBookConstants.OUTCOMES)
    private List<OddDetailsTO> outcomeDetailsList = new ArrayList<>();
    private Double minStake;
    private Double maxStake;

    @JsonIgnore
    private Integer providerId;

    @JsonIgnore
    private String providerName;

    public FancyBetDetailsTO() {
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
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

    public List<OddDetailsTO> getOutcomeDetailsList() {
        return outcomeDetailsList;
    }

    public void setOutcomeDetailsList(List<OddDetailsTO> outcomeDetailsList) {
        this.outcomeDetailsList = outcomeDetailsList;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}