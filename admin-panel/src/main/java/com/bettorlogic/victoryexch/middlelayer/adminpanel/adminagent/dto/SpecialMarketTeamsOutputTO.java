package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

public class SpecialMarketTeamsOutputTO {
    private Integer teamId;
    private String teamName;
    private Integer boxCount;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(Integer boxCount) {
        this.boxCount = boxCount;
    }
}
