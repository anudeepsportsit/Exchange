package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory;

public class BettingHistoryDetailsOutput {

    private Integer betId;
    private String playerId;
    private String sportName;
    private String eventName;
    private String marketName;
    private String profitLoss;
    private String selection;
    private String betPlaced;
    private String type;
    private String oddReq;
    private Double stake;
    private Double avgOddsMatched;
    private String userCancel;

    private BetDetailsTO betDetails;

    public String getUserCancel() {
        return userCancel;
    }

    public void setUserCancel(String userCancel) {
        this.userCancel = userCancel;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(String profitLoss) {
        this.profitLoss = profitLoss;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOddReq() {
        return oddReq;
    }

    public void setOddReq(String oddReq) {
        this.oddReq = oddReq;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }


    public BetDetailsTO getBetDetails() {
        return betDetails;
    }

    public void setBetDetails(BetDetailsTO betDetails) {
        this.betDetails = betDetails;
    }

    public Double getAvgOddsMatched() {
        return avgOddsMatched;
    }

    public void setAvgOddsMatched(Double avgOddsMatched) {
        this.avgOddsMatched = avgOddsMatched;
    }

    @Override
    public String toString() {
        return "BettingHistoryDetailsOutput{" +
                "betId=" + betId +
                ", playerId='" + playerId + '\'' +
                ", sportName='" + sportName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", marketName='" + marketName + '\'' +
                ", profitLoss='" + profitLoss + '\'' +
                ", selection='" + selection + '\'' +
                ", betPlaced='" + betPlaced + '\'' +
                ", type='" + type + '\'' +
                ", oddReq='" + oddReq + '\'' +
                ", stake=" + stake +
                ", avgOddsMatched=" + avgOddsMatched +
                ", betDetails=" + betDetails +
                '}';
    }
}
