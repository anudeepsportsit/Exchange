package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class AgentBookMakerTO {

    private Integer eventId;
    private Integer oddDictionaryId;
    private Integer agentId;
    private String providerOutcome;
    private Double backOdds;
    private Double layOdds;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOddDictionaryId() {
        return oddDictionaryId;
    }

    public void setOddDictionaryId(Integer oddDictionaryId) {
        this.oddDictionaryId = oddDictionaryId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getProviderOutcome() {
        return providerOutcome;
    }

    public void setProviderOutcome(String providerOutcome) {
        this.providerOutcome = providerOutcome;
    }

    public Double getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(Double backOdds) {
        this.backOdds = backOdds;
    }

    public Double getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(Double layOdds) {
        this.layOdds = layOdds;
    }
}
