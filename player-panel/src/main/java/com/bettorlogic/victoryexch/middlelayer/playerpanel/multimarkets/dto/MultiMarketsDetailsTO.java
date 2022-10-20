package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsDetailsTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.OUTCOMES)
    private List<MultiMarketsOutcomesTO> outcomesList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMarketValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double minStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double maxStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketSuspended;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;

    private String clMarketId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketId;
    private Integer providerId;
    private String providerName;

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getClMarketId() {
        return clMarketId;
    }

    public void setClMarketId(String clMarketId) {
        this.clMarketId = clMarketId;
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

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
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

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public List<MultiMarketsOutcomesTO> getOutcomesList() {
        return outcomesList;
    }

    public void setOutcomesList(List<MultiMarketsOutcomesTO> outcomesList) {
        this.outcomesList = outcomesList;
    }
}
