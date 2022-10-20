package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

public class FancyMarketTO {
    private Integer fancyMarketId;
    private String fancyMarketName;
    private String fancyOutcomeYes;
    private String fancyOutcomeNo;
    private Double oddsYes;
    private Double oddsNo;
    private String fancyStatus;
    private Double fancyMinStake;
    private Double fancyMaxStake;
    private boolean isSuspended;

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public Integer getFancyMarketId() {
        return fancyMarketId;
    }

    public void setFancyMarketId(Integer fancyMarketId) {
        this.fancyMarketId = fancyMarketId;
    }

    public String getFancyMarketName() {
        return fancyMarketName;
    }

    public void setFancyMarketName(String fancyMarketName) {
        this.fancyMarketName = fancyMarketName;
    }

    public String getFancyOutcomeYes() {
        return fancyOutcomeYes;
    }

    public void setFancyOutcomeYes(String fancyOutcomeYes) {
        this.fancyOutcomeYes = fancyOutcomeYes;
    }

    public String getFancyOutcomeNo() {
        return fancyOutcomeNo;
    }

    public void setFancyOutcomeNo(String fancyOutcomeNo) {
        this.fancyOutcomeNo = fancyOutcomeNo;
    }

    public Double getOddsYes() {
        return oddsYes;
    }

    public void setOddsYes(Double oddsYes) {
        this.oddsYes = oddsYes;
    }

    public Double getOddsNo() {
        return oddsNo;
    }

    public void setOddsNo(Double oddsNo) {
        this.oddsNo = oddsNo;
    }

    public String getFancyStatus() {
        return fancyStatus;
    }

    public void setFancyStatus(String fancyStatus) {
        this.fancyStatus = fancyStatus;
    }

    public Double getFancyMinStake() {
        return fancyMinStake;
    }

    public void setFancyMinStake(Double fancyMinStake) {
        this.fancyMinStake = fancyMinStake;
    }

    public Double getFancyMaxStake() {
        return fancyMaxStake;
    }

    public void setFancyMaxStake(Double fancyMaxStake) {
        this.fancyMaxStake = fancyMaxStake;
    }
}
