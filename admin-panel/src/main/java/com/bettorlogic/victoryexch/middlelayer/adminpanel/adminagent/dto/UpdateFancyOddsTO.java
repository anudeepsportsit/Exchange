package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class UpdateFancyOddsTO {

    private String fancyMarketName;

    public String getFancyMarketName() {
        return fancyMarketName;
    }

    public void setFancyMarketName(String fancyMarketName) {
        this.fancyMarketName = fancyMarketName;
    }

    private String outcome;
    private Double odds;

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }
}
