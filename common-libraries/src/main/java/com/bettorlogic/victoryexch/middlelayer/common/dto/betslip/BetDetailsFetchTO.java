package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

public class BetDetailsFetchTO {

    private Integer isFancy;
    private Double minStake;
    private Double maxStake;
    private Integer providerId;
    private String homeTeamName;
    private String awayTeamName;
    private String clientMarketId;
    private Integer isLive;
    private String selectionId;
    private String subMarket;

    @NotNull(message = ExceptionConstants.INVALID_EVENT_ID_ENTERED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer eventId;

    @NotNull(message = ExceptionConstants.INVALID_MARKET_ID_ENTERED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketId;

    //    @NotNull(message = ExceptionConstants.INVALID_OUTCOME_ID_ENTERED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer outcomeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String eventName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String outcomeName;

    @NotNull(message = ExceptionConstants.ODDS_NULL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double odds;

    @NotNull(message = ExceptionConstants.INVALID_ODD_TYPE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oddType;

    @JsonIgnore
    private boolean marketExists = true;
    @JsonIgnore
    private boolean eventExists = true;

    public String getSubMarket() {
        return subMarket;
    }

    public void setSubMarket(String subMarket) {
        this.subMarket = subMarket;
    }

    public String getClientMarketId() {
        return clientMarketId;
    }

    public void setClientMarketId(String clientMarketId) {
        this.clientMarketId = clientMarketId;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public boolean isMarketExists() {
        return marketExists;
    }

    public void setMarketExists(boolean marketExists) {
        this.marketExists = marketExists;
    }

    public boolean isEventExists() {
        return eventExists;
    }

    public void setEventExists(boolean eventExists) {
        this.eventExists = eventExists;
    }

    public Integer getIsFancy() {
        return isFancy;
    }

    public void setIsFancy(Integer isFancy) {
        this.isFancy = isFancy;
    }
}