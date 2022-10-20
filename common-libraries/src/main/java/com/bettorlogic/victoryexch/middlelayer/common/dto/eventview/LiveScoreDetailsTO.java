package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LiveScoreDetailsTO {

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String scoreTitle;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamScore;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamScore;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamRunRate;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamRunRate;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String matchMinutes;

    @JsonIgnore
    public boolean isEmpty() {
        return scoreTitle == null && homeTeamScore == null && awayTeamScore == null && awayTeamRunRate == null && homeTeamRunRate == null && matchMinutes == null;
    }

    public String getHomeTeamRunRate() {
        return homeTeamRunRate;
    }

    public void setHomeTeamRunRate(String homeTeamRunRate) {
        this.homeTeamRunRate = homeTeamRunRate;
    }

    public String getAwayTeamRunRate() {
        return awayTeamRunRate;
    }

    public void setAwayTeamRunRate(String awayTeamRunRate) {
        this.awayTeamRunRate = awayTeamRunRate;
    }

    public String getMatchMinutes() {
        return matchMinutes;
    }

    public void setMatchMinutes(String matchMinutes) {
        this.matchMinutes = matchMinutes;
    }

    public String getScoreTitle() {
        return scoreTitle;
    }

    public void setScoreTitle(String scoreTitle) {
        this.scoreTitle = scoreTitle;
    }

    public String getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }
}