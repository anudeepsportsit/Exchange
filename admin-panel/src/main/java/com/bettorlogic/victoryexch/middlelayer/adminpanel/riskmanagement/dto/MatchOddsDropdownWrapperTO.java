package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class MatchOddsDropdownWrapperTO {
    List<OutcomesDetailsTO> outcomes;
    private Integer eventId;
    private String eventName;
    private String stakeAmount;
    private String matchedAmount;
    private Integer sportId;

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(String stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public String getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(String matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public List<OutcomesDetailsTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<OutcomesDetailsTO> outcomes) {
        this.outcomes = outcomes;
    }
}
