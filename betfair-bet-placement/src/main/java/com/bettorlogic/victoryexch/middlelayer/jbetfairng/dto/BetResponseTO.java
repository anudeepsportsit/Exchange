package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import org.springframework.stereotype.Component;

@Component
public class BetResponseTO {
    private String betStatus;
    private String errorCode;
    private String betId;
    private String betPlacedDate;
    private String marketId;
    private Double betfairStakeAmount;
    private Double betPlacedOdds;
    private String orderStatus;

    public Double getBetfairStakeAmount() {
        return betfairStakeAmount;
    }

    public void setBetfairStakeAmount(Double betfairStakeAmount) {
        this.betfairStakeAmount = betfairStakeAmount;
    }

    public Double getBetPlacedOdds() {
        return betPlacedOdds;
    }

    public void setBetPlacedOdds(Double betPlacedOdds) {
        this.betPlacedOdds = betPlacedOdds;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getBetPlacedDate() {
        return betPlacedDate;
    }

    public void setBetPlacedDate(String betPlacedDate) {
        this.betPlacedDate = betPlacedDate;
    }
}
