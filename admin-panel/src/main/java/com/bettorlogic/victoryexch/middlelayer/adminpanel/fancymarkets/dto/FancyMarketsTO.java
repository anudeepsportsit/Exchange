package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import java.math.BigDecimal;

public class FancyMarketsTO {
    private Integer eventId;
    private String marketName;
    private String outcomeYes;
    private String outcomeNo;
    private BigDecimal oddsYes;
    private BigDecimal oddsNo;
    private BigDecimal minStake;
    private BigDecimal maxStake;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getOutcomeYes() {
        return outcomeYes;
    }

    public void setOutcomeYes(String outcomeYes) {
        this.outcomeYes = outcomeYes;
    }

    public String getOutcomeNo() {
        return outcomeNo;
    }

    public void setOutcomeNo(String outcomeNo) {
        this.outcomeNo = outcomeNo;
    }

    public BigDecimal getOddsYes() {
        return oddsYes;
    }

    public void setOddsYes(BigDecimal oddsYes) {
        this.oddsYes = oddsYes;
    }

    public BigDecimal getOddsNo() {
        return oddsNo;
    }

    public void setOddsNo(BigDecimal oddsNo) {
        this.oddsNo = oddsNo;
    }

    public BigDecimal getMinStake() {
        return minStake;
    }

    public void setMinStake(BigDecimal minStake) {
        this.minStake = minStake;
    }

    public BigDecimal getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(BigDecimal maxStake) {
        this.maxStake = maxStake;
    }
}
