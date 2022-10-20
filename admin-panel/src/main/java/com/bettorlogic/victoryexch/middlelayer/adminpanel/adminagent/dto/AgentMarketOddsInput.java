package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class AgentMarketOddsInput {
    private Integer agentMarketId;
    private Integer sportId;
    private Integer eventId;

    public Integer getAgentMarketId() {
        return agentMarketId;
    }

    public void setAgentMarketId(Integer agentMarketId) {
        this.agentMarketId = agentMarketId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "AgentMarketsInput{" +
                "agentMarketId=" + agentMarketId +
                ", sportId=" + sportId +
                ", eventId=" + eventId +
                '}';
    }

    public AgentMarketOddsInput(Integer agentMarketId, Integer sportId, Integer eventId, String sportName, String eventName, String marketName, String marketSelection) {
        this.agentMarketId = agentMarketId;
        this.sportId = sportId;
        this.eventId = eventId;
    }
}