package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class CompetitionResult {
    private CompetitionTO competition;
    private int marketCount;

    public CompetitionTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionTO competition) {
        this.competition = competition;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }
}


