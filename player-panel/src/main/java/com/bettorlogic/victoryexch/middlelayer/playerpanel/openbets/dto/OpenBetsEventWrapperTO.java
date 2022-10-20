package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

public class OpenBetsEventWrapperTO {
    private Integer eventId;
    private String eventName;
    private Integer sportId;
    private String sportName;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    private BetDetailsSubWrapperTO matchedBets;
    private BetDetailsSubWrapperTO unMatchedBets;

    public BetDetailsSubWrapperTO getMatchedBets() {
        if (matchedBets != null) {
            return matchedBets;
        } else {
            return new BetDetailsSubWrapperTO();
        }
    }

    public void setMatchedBets(BetDetailsSubWrapperTO matchedBets) {
        this.matchedBets = matchedBets;
    }

    public BetDetailsSubWrapperTO getUnMatchedBets() {
        if (unMatchedBets != null) {
            return unMatchedBets;
        } else {
            return new BetDetailsSubWrapperTO();
        }

    }

    public void setUnMatchedBets(BetDetailsSubWrapperTO unMatchedBets) {
        this.unMatchedBets = unMatchedBets;
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

    @Override
    public String toString() {
        return "OpenBetsEventWrapperTO{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", sportId=" + sportId +
                ", sportName='" + sportName + '\'' +
                ", matchedBets=" + matchedBets +
                ", unMatchedBets=" + unMatchedBets +
                '}';
    }
}
