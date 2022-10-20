package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.Date;
import java.util.List;

public class RunnerTO {
    private Long selectionId;
    private Double handicap;
    private String status;
    private Double adjustmentFactor;
    private Double lastPriceTraded;
    private Double totalMatched;
    private Date removalDate;
    private StartingPricesTO sp;
    private ExchangePricesTO ex;
    private List<OrderTO> orders;
    private List<MatchTO> matches;

    public Long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Long selectionId) {
        this.selectionId = selectionId;
    }

    public Double getHandicap() {
        return handicap;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setAdjustmentFactor(Double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

    public Double getLastPriceTraded() {
        return lastPriceTraded;
    }

    public void setLastPriceTraded(Double lastPriceTraded) {
        this.lastPriceTraded = lastPriceTraded;
    }

    public Double getTotalMatched() {
        return totalMatched;
    }

    public void setTotalMatched(Double totalMatched) {
        this.totalMatched = totalMatched;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public StartingPricesTO getSp() {
        return sp;
    }

    public void setSp(StartingPricesTO sp) {
        this.sp = sp;
    }

    public ExchangePricesTO getEx() {
        return ex;
    }

    public void setEx(ExchangePricesTO ex) {
        this.ex = ex;
    }

    public List<OrderTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderTO> orders) {
        this.orders = orders;
    }

    public List<MatchTO> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchTO> matches) {
        this.matches = matches;
    }

    public String toString() {
        return "{" + "" + "selectionId=" + getSelectionId() + "," + "handicap="
                + getHandicap() + "," + "status=" + getStatus() + ","
                + "adjustmentFactor=" + getAdjustmentFactor() + ","
                + "lastPriceTraded=" + getLastPriceTraded() + ","
                + "totalMatched=" + getTotalMatched() + "," + "removalDate="
                + getRemovalDate() + "," + "sp=" + getSp() + "," + "ex="
                + getEx() + "," + "orders=" + getOrders() + "," + "matches="
                + getMatches() + "," + "}";
    }

}
