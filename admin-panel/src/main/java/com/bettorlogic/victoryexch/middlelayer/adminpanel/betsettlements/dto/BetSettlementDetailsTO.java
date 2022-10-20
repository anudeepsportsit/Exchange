package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto;

public class BetSettlementDetailsTO {
    private Integer[] betSettlementIds;
    private String userLoginToken;
    private String betSettlementBulkData;

    public Integer[] getBetSettlementIds() {
        return betSettlementIds;
    }

    public void setBetSettlementIds(Integer[] betSettlementIds) {
        this.betSettlementIds = betSettlementIds;
    }

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public String getBetSettlementBulkData() {
        return betSettlementBulkData;
    }

    public void setBetSettlementBulkData(String betSettlementBulkData) {
        this.betSettlementBulkData = betSettlementBulkData;
    }
}