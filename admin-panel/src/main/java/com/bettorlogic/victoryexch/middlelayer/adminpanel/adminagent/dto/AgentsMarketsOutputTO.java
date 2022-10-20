package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AgentsMarketsOutputTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer agentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String AgentName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sportName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String eventName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketSelection;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String Available;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String Status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketResult;

    public String getMarketResult() {
        return marketResult;
    }

    public void setMarketResult(String marketResult) {
        this.marketResult = marketResult;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
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
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketSelection() {
        return marketSelection;
    }

    public void setMarketSelection(String marketSelection) {
        this.marketSelection = marketSelection;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
