package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import java.util.ArrayList;
import java.util.List;

public class AgentUpdateOddsTO {

    private String token;
    private Integer eventId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private  String marketType;
    private Integer agentId;
    private String loginToken;
    private List<BookmakerOddsTO> outcomes = new ArrayList<>();

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public List<BookmakerOddsTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<BookmakerOddsTO> outcomes) {
        this.outcomes = outcomes;
    }
}
