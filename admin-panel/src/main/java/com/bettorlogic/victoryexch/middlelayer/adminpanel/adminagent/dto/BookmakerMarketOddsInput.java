package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class BookmakerMarketOddsInput {

    private Integer sportId;
    private Integer eventid;
    private Integer agentMarketId;
    private String selectionName;
    private Double bookMakerBackOdds;
    private Double bookMakerLayOdds;

    private Double oldBackOdds;
    private Double oldLayOdds;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Double getOldBackOdds() {
        return oldBackOdds;
    }

    public void setOldBackOdds(Double oldBackOdds) {
        this.oldBackOdds = oldBackOdds;
    }

    public Double getOldLayOdds() {
        return oldLayOdds;
    }

    public void setOldLayOdds(Double oldLayOdds) {
        this.oldLayOdds = oldLayOdds;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
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

    public Double getBookMakerBackOdds() {
        return bookMakerBackOdds;
    }

    public void setBookMakerBackOdds(Double bookMakerBackOdds) {
        this.bookMakerBackOdds = bookMakerBackOdds;
    }

    public Double getBookMakerLayOdds() {
        return bookMakerLayOdds;
    }

    public void setBookMakerLayOdds(Double bookMakerLayOdds) {
        this.bookMakerLayOdds = bookMakerLayOdds;
    }
}
