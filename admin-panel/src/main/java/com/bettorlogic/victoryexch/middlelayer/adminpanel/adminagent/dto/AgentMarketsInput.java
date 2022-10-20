package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import java.util.List;

public class AgentMarketsInput {
    private Integer agentId;
    private Integer sportId;
    private Integer eventId;
    private String sportName;
    private String eventName;
    private String marketName;
    private String subMarketName;
    private String marketSelection;
    private List<EventTeamsOutputTO> eventTeamsList;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public String getSubMarketName() {
        return subMarketName;
    }

    public void setSubMarketName(String subMarketName) {
        this.subMarketName = subMarketName;
    }

    public List<EventTeamsOutputTO> getEventTeamsList() {
        return eventTeamsList;
    }

    public void setEventTeamsList(List<EventTeamsOutputTO> eventTeamsList) {
        this.eventTeamsList = eventTeamsList;
    }
}
