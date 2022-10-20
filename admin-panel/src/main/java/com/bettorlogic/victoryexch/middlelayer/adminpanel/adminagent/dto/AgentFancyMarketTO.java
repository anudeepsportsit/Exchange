package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

public class AgentFancyMarketTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fancyMarketName;
    private Integer eventId;
    private String eventName;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer fancyMarketId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<AgentFancyOutcomeTO> outcomeList;

    public Set<AgentFancyOutcomeTO> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(Set<AgentFancyOutcomeTO> outcomeList) {
        this.outcomeList = outcomeList;
    }

    public String getFancyMarketName() {
        return fancyMarketName;
    }

    public void setFancyMarketName(String fancyMarketName) {
        this.fancyMarketName = fancyMarketName;
    }

    public Integer getFancyMarketId() {
        return fancyMarketId;
    }

    public void setFancyMarketId(Integer fancyMarketId) {
        this.fancyMarketId = fancyMarketId;
    }

}
