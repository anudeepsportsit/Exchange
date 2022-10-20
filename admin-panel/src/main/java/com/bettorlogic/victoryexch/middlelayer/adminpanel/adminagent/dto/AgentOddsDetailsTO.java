package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class AgentOddsDetailsTO {
    private Double betFairBackOdds;
    private Double betFairLayOdds;
    private Double bookMakerBackOdds;
    private Double bookMakerLayOdds;

    public Double getBetFairBackOdds() {
        return betFairBackOdds;
    }

    public void setBetFairBackOdds(Double betFairBackOdds) {
        this.betFairBackOdds = betFairBackOdds;
    }

    public Double getBetFairLayOdds() {
        return betFairLayOdds;
    }

    public void setBetFairLayOdds(Double betFairLayOdds) {
        this.betFairLayOdds = betFairLayOdds;
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