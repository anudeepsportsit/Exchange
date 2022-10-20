package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BetDetailsTO {

    @NotNull(message = ExceptionConstants.INVALID_EVENT_ID_ENTERED)
    private Integer eventId;

    private Integer providerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer homeTeamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer awayTeamId;

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    @NotNull(message = ExceptionConstants.INVALID_MARKET_ID_ENTERED)
    private Integer marketId;

    @NotNull(message = ExceptionConstants.ODDS_NULL)
    private BigDecimal odds;

    @NotBlank(message = ExceptionConstants.ODD_TYPE_NULL)
    private String oddType;

    @NotNull(message = ExceptionConstants.INVALID_STAKE_AMOUNT_ENTERED)
    private Double stakeAmount;

    @NotNull(message = ExceptionConstants.INVALID_RETURNS_AMOUNT_ENTERED)
    private Double returns;

    //    @NotNull(message = ExceptionConstants.INVALID_EXPOSURE_AMOUNT_ENTERED)
    private Double exposure;

    private Integer oddsManualChangeFlag;
    private BigDecimal originalOdds;
    private BigDecimal homeTeamProfitLoss;
    private BigDecimal awayTeamProfitLoss;
    private Integer outcomeId;

    /*
     * modified by anudeep for betfair API Starts
     */

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientMarketId;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String selectionId;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private Long clientBetId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientBetPlacedDate;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Double clientStakeAmount;
    private Integer isMatched;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal betFairOdds;
    private Integer isLive;

    public BigDecimal getBetFairOdds() {
        return betFairOdds;
    }

    public void setBetFairOdds(BigDecimal betFairOdds) {
        this.betFairOdds = betFairOdds;
    }

    public Integer getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(Integer isMatched) {
        this.isMatched = isMatched;
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

    public Long getClientBetId() {
        return clientBetId;
    }

    public void setClientBetId(Long clientBetId) {
        this.clientBetId = clientBetId;
    }

    public String getClientBetPlacedDate() {
        return clientBetPlacedDate;
    }

    public void setClientBetPlacedDate(String clientBetPlacedDate) {
        this.clientBetPlacedDate = clientBetPlacedDate;
    }

    public Double getClientStakeAmount() {
        return clientStakeAmount;
    }

    public void setClientStakeAmount(Double clientStakeAmount) {
        this.clientStakeAmount = clientStakeAmount;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    /*
     * modified by anudeep for betfair API Ends
     */

    public BigDecimal getHomeTeamProfitLoss() {
        return homeTeamProfitLoss;
    }

    public void setHomeTeamProfitLoss(BigDecimal homeTeamProfitLoss) {
        this.homeTeamProfitLoss = homeTeamProfitLoss;
    }

    public BigDecimal getAwayTeamProfitLoss() {
        return awayTeamProfitLoss;
    }

    public void setAwayTeamProfitLoss(BigDecimal awayTeamProfitLoss) {
        this.awayTeamProfitLoss = awayTeamProfitLoss;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Double getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(Double stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public Double getReturns() {
        return returns;
    }

    public void setReturns(Double returns) {
        this.returns = returns;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public Integer getOddsManualChangeFlag() {
        return oddsManualChangeFlag;
    }

    public void setOddsManualChangeFlag(Integer oddsManualChangeFlag) {
        this.oddsManualChangeFlag = oddsManualChangeFlag;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public BigDecimal getOriginalOdds() {
        return originalOdds;
    }

    public void setOriginalOdds(BigDecimal originalOdds) {
        this.originalOdds = originalOdds;
    }
}