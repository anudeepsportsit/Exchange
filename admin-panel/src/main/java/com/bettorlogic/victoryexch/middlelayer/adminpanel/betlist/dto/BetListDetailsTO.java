package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto;

public class BetListDetailsTO {

    private Integer superId;

    private String superName;

    private Integer masterId;

    private String masterName;

    private Integer playerId;

    private String playerName;

    private Integer betId;

    private String betPlaced;

    private String ipAddress;

    private String sportName;

    private String eventName;

    private String MarketName;

    private String selection;

    private Double liability;

    private Double profitLoss;

    private Double oddsRequest;

    private String oddType;

    private Double stake;

    private String betStatus;

    public Integer getSuperId() {
        return superId;
    }

    public void setSuperId(Integer superId) {
        this.superId = superId;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

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
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public Double getLiability() {
        return liability;
    }

    public void setLiability(Double liability) {
        this.liability = liability;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public Double getOddsRequest() {
        return oddsRequest;
    }

    public void setOddsRequest(Double oddsRequest) {
        this.oddsRequest = oddsRequest;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }


    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }
}
