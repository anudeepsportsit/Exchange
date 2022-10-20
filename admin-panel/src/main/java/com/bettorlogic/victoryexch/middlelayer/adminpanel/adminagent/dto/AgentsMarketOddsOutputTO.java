package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class AgentsMarketOddsOutputTO {
    private String selectionName;
    private AgentOddsDetailsTO agentOddsDetails;

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public AgentOddsDetailsTO getAgentOddsDetails() {
        return agentOddsDetails;
    }

    public void setAgentOddsDetails(AgentOddsDetailsTO agentOddsDetails) {
        this.agentOddsDetails = agentOddsDetails;
    }
}