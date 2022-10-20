package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import java.util.List;

public class MarketSuspensionTO {

    private List<AgentMarketsInput> markets;
    private String status;
    private String token;

    public List<AgentMarketsInput> getMarkets() {
        return markets;
    }

    public void setMarkets(List<AgentMarketsInput> markets) {
        this.markets = markets;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
