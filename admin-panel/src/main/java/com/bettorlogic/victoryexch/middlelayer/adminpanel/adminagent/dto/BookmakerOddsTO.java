package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class BookmakerOddsTO {
    private String id;
    private Double backOdds;
    private Double layOdds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(Double backOdds) {
        this.backOdds = backOdds;
    }

    public Double getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(Double layOdds) {
        this.layOdds = layOdds;
    }
}
