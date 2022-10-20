package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsTiedTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.OUTCOMES)
    private List<MultiMarketsTiedDetailsTO> tiedMatchDetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketSuspended;


    private String clMarketId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double minStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double maxStake;
    private Integer providerId;
    private String providerName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMarketValue;

    public String getClMarketId() {
        return clMarketId;
    }

    public void setClMarketId(String clMarketId) {
        this.clMarketId = clMarketId;
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

    public String getSubMarketValue() {
        return subMarketValue;
    }

    public void setSubMarketValue(String subMarketValue) {
        this.subMarketValue = subMarketValue;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }

    public List<MultiMarketsTiedDetailsTO> getTiedMatchDetailsList() {
        return tiedMatchDetailsList;
    }

    public void setTiedMatchDetailsList(List<MultiMarketsTiedDetailsTO> tiedMatchDetailsList) {
        this.tiedMatchDetailsList = tiedMatchDetailsList;
    }
}
