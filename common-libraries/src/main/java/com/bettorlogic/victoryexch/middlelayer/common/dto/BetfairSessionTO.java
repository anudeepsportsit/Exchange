package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class BetfairSessionTO {

    private String betFairAppKey;
    private String betFairSession;

    public String getBetFairAppKey() {
        return betFairAppKey;
    }

    public void setBetFairAppKey(String betFairAppKey) {
        this.betFairAppKey = betFairAppKey;
    }

    public String getBetFairSession() {
        return betFairSession;
    }

    public void setBetFairSession(String betFairSession) {
        this.betFairSession = betFairSession;
    }
}
