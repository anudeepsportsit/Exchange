package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BetSlipResponseTO {

    private Integer eventId;
    private Double odds;
    private String betStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String betfairBetId;
    private boolean isMatched;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String selectionId;

    private String oddType;

    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public String getBetfairBetId() {
        return betfairBetId;
    }

    public void setBetfairBetId(String betfairBetId) {
        this.betfairBetId = betfairBetId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
