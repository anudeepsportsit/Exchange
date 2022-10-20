package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MyReportMarketTO {
    private String marketName;
    @NotNull
    private BigDecimal stake;
    @NotNull
    private BigDecimal playerProfitLoss;
    @NotNull
    private BigDecimal downlineProfitLoss;
    @NotNull
    private BigDecimal commision;
    @NotNull
    private BigDecimal uplineProfitLoss;

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getPlayerProfitLoss() {
        return playerProfitLoss;
    }

    public void setPlayerProfitLoss(BigDecimal playerProfitLoss) {
        this.playerProfitLoss = playerProfitLoss;
    }

    public BigDecimal getDownlineProfitLoss() {
        return downlineProfitLoss;
    }

    public void setDownlineProfitLoss(BigDecimal downlineProfitLoss) {
        this.downlineProfitLoss = downlineProfitLoss;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public BigDecimal getUplineProfitLoss() {
        return uplineProfitLoss;
    }

    public void setUplineProfitLoss(BigDecimal uplineProfitLoss) {
        this.uplineProfitLoss = uplineProfitLoss;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }


}
