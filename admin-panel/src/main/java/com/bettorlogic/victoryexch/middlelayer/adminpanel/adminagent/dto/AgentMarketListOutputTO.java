package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import java.math.BigDecimal;

public class AgentMarketListOutputTO {
    private Integer agentMarketId;
    private Integer sportId;
    private Integer eventId;
    private String sportName;
    private String eventName;
    private String marketName;
    private String subMarketName;
    private String marketSelection;
    private String Available;
    private String Status;
    private Boolean Suspend;
    private String agentName;
    private String marketResult;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getMarketResult() {
        return marketResult;
    }

    public void setMarketResult(String marketResult) {
        this.marketResult = marketResult;
    }

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

    public Boolean getSuspend() {
        return Suspend;
    }

    public void setSuspend(Boolean suspend) {
        Suspend = suspend;
    }

    public String getSubMarketName() {
        return subMarketName;
    }

    public void setSubMarketName(String subMarketName) {
        this.subMarketName = subMarketName;
    }
}
