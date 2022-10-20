package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

public class FancyDownDownOutcomesTO {
    private Integer id;
    private String name;
    private Double odds;
    private Double size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
