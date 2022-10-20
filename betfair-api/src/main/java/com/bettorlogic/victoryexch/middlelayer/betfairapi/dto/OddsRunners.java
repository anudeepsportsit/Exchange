package com.bettorlogic.victoryexch.middlelayer.betfairapi.dto;

public class OddsRunners {
    private int selectionId;

    private int handicap;

    private String status;

    private double lastPriceTraded;

    private int totalMatched;

    private OddsEx ex;

    public int getSelectionId() {
        return this.selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public int getHandicap() {
        return this.handicap;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLastPriceTraded() {
        return this.lastPriceTraded;
    }

    public void setLastPriceTraded(double lastPriceTraded) {
        this.lastPriceTraded = lastPriceTraded;
    }

    public int getTotalMatched() {
        return this.totalMatched;
    }

    public void setTotalMatched(int totalMatched) {
        this.totalMatched = totalMatched;
    }

    public OddsEx getEx() {
        return this.ex;
    }

    public void setEx(OddsEx ex) {
        this.ex = ex;
    }

    @Override
    public String toString() {
        return "OddsRunners{" +
                "selectionId=" + selectionId +
                ", handicap=" + handicap +
                ", status='" + status + '\'' +
                ", lastPriceTraded=" + lastPriceTraded +
                ", totalMatched=" + totalMatched +
                ", ex=" + ex +
                '}';
    }
}