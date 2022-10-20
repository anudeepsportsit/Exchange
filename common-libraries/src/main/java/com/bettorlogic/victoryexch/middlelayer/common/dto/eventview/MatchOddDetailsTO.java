package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MatchOddDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clMarketId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMarket;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double minStake;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double maxStake;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketSuspended;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double matched;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    @JsonIgnore
    private String homeTeam;

    @JsonIgnore
    private String awayTeam;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer providerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String providerName;

    @JsonProperty(SportsBookConstants.OUTCOMES)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EventViewOutcomeDetailsTO> outcomeDetailsList = new ArrayList<>();

    @JsonIgnore
    private Set<Double> matchedList;

    public Set<Double> getMatchedList() {
        return matchedList;
    }

    public void setMatchedList(Set<Double> matchedList) {
        this.matchedList = matchedList;
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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }

    public List<EventViewOutcomeDetailsTO> getOutcomeDetailsList() {
        return outcomeDetailsList;
    }

    public void setOutcomeDetailsList(List<EventViewOutcomeDetailsTO> outcomeDetailsList) {
        this.outcomeDetailsList = outcomeDetailsList;
    }

    public String getClMarketId() {
        return clMarketId;
    }

    public void setClMarketId(String clMarketId) {
        this.clMarketId = clMarketId;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public Double getMatched() {
        return matched;
    }

    public void setMatched(Double matched) {
        this.matched = matched;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }


    public String getSubMarket() {
        return subMarket;
    }

    public void setSubMarket(String subMarket) {
        this.subMarket = subMarket;
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