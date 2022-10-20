package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class OrderDetailsTO {

    private List<String> betIds;
    private String appKey;
    private String sessionToken;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public List<String> getBetIds() {
        return betIds;
    }

    public void setBetIds(List<String> betIds) {
        this.betIds = betIds;
    }
}
