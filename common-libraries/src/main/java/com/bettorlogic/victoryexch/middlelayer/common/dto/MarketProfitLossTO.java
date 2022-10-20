package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MarketProfitLossTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ExceptionConstants.INVALID_MARKET_ID_ENTERED)
    private Integer marketId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sportName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String eventName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMarketName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String outcomeName;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getSubMarketName() {
        return subMarketName;
    }

    public void setSubMarketName(String subMarketName) {
        this.subMarketName = subMarketName;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ExceptionConstants.INVALID_EVENT_ID_ENTERED)
    private Integer eventId;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isFancy;

    @NotNull(message = ExceptionConstants.HOME_PROFIT_LOSS_EMPTY)
    private BigDecimal homeTeamProfitLoss;

    @NotNull(message = ExceptionConstants.AWAY_PROFIT_LOSS_EMPTY)
    private BigDecimal awayTeamProfitLoss;

    @NotNull(message = ExceptionConstants.DRAW_PROFIT_LOSS_EMPTY)
    @JsonProperty(SportsBookConstants.DRAW_PROFIT_LOSS)
    private BigDecimal drawProfitLoss;

    @NotNull(message = ExceptionConstants.EMPTY_PROVIDER_ID_ENTERED)
    private Integer providerId;

    @NotNull(message = ExceptionConstants.SUBMARKET_NAME)
    private String subMarket;

    public void setFancy(boolean fancy) {
        isFancy = fancy;
    }

    public String getSubMarket() {
        return subMarket;
    }

    public void setSubMarket(String subMarket) {
        this.subMarket = subMarket;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @JsonProperty(SportsBookConstants.IS_FANCY)
    public boolean isFancy() {
        return isFancy;
    }

    public void setIsFancy(boolean isFancy) {
        this.isFancy = isFancy;
    }

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

    public BigDecimal getDrawProfitLoss() {
        return drawProfitLoss;
    }

    public void setDrawProfitLoss(BigDecimal drawProfitLoss) {
        this.drawProfitLoss = drawProfitLoss;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
