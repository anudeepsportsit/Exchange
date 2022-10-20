package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class AgentMarketSuspendInput {
    private String userLoginToken;
    private Integer agentMarketId;
    private String selectionName;
    private Boolean suspend;
    private String marketName;

    private Integer eventId;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    private Integer oddDictionaryId;

    public Integer getOddDictionaryId() {
        return oddDictionaryId;
    }

    public void setOddDictionaryId(Integer oddDictionaryId) {
        this.oddDictionaryId = oddDictionaryId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public Integer getAgentMarketId() {
        return agentMarketId;
    }

    public void setAgentMarketId(Integer agentMarketId) {
        this.agentMarketId = agentMarketId;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public Boolean getSuspend() {
        return suspend;
    }

    public void setSuspend(Boolean suspend) {
        this.suspend = suspend;
    }
}