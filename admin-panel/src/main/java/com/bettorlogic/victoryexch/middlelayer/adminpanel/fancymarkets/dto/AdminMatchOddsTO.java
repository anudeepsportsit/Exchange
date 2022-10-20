package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

public class AdminMatchOddsTO {
    private Integer eventId;
    private Integer sportId;

    private StringBuilder oddsList;

    public StringBuilder getOddsList() {
        return oddsList;
    }

    public void setOddsList(StringBuilder oddsList) {
        this.oddsList = oddsList;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }
}
