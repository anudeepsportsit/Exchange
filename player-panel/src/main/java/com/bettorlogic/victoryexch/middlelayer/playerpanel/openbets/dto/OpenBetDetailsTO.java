package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.PlaceInstructionReport;

public class OpenBetDetailsTO {

    private Integer betId;
    private String outcomeName;
    private String oddType;
    private String odds;
    private String stake;
    private String returns;
    private String profitLiability;
    private String marketName;
    private String exposure;
    private String betPlacedTime;
    private Integer isFancy;
    private String minStake;
    private String maxStake;
    private Integer providerId;
    private String clientMarketId;
    private String selectionId;
    private String originalOdds;
    private Integer outcomeId;
    private Integer isLive;
    private Integer oddsManualChangeFlag;
    private Integer sportId;
    private Integer marketId;
    private String sportName;
    private String betfairBetId;
    private Integer eventId;
    private String eventName;
    private Integer awayTeamId;
    private Integer homeTeamId;
    private String awayTeam;
    private String homeTeam;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getSubMarket() {
        return subMarket;
    }

    public void setSubMarket(String subMarket) {
        this.subMarket = subMarket;
    }

    private String subMarket;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getBetfairBetId() {
        return betfairBetId;
    }

    public void setBetfairBetId(String betfairBetId) {
        this.betfairBetId = betfairBetId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getClientMarketId() {
        return clientMarketId;
    }

    public void setClientMarketId(String clientMarketId) {
        this.clientMarketId = clientMarketId;
    }

    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }

    public String getOriginalOdds() {
        return originalOdds;
    }

    public void setOriginalOdds(String originalOdds) {
        this.originalOdds = originalOdds;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getOddsManualChangeFlag() {
        return oddsManualChangeFlag;
    }

    public void setOddsManualChangeFlag(Integer oddsManualChangeFlag) {
        this.oddsManualChangeFlag = oddsManualChangeFlag;
    }

    public Integer getIsFancy() {
        return isFancy;
    }

    public void setIsFancy(Integer isFancy) {
        this.isFancy = isFancy;
    }

    public String getMinStake() {
        return minStake;
    }

    public void setMinStake(String minStake) {
        this.minStake = minStake;
    }

    public String getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(String maxStake) {
        this.maxStake = maxStake;
    }

    public String getBetPlacedTime() {
        return betPlacedTime;
    }

    public void setBetPlacedTime(String betPlacedTime) {
        this.betPlacedTime = betPlacedTime;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getStake() {
        return stake;
    }

    public void setStake(String stake) {
        this.stake = stake;
    }

    public String getProfitLiability() {
        return profitLiability;
    }

    public void setProfitLiability(String profitLiability) {
        this.profitLiability = profitLiability;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    @Override
    public String toString() {
        return "OpenBetDetailsTO{" +
                "betId=" + betId +
                ", outcomeName='" + outcomeName + '\'' +
                ", oddType='" + oddType + '\'' +
                ", odds='" + odds + '\'' +
                ", stake='" + stake + '\'' +
                ", returns='" + returns + '\'' +
                ", profitLiability='" + profitLiability + '\'' +
                ", marketName='" + marketName + '\'' +
                '}';
    }
}