package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.utils.MultiMarketsConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsEventsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(MultiMarketsConstants.MARKET_GROUPS)
    private List<MultiMarketsDetailsTO> marketGroupsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.MATCH_ODDS)
    private MultiMarketsDetailsTO matchOddsDetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.MATCH_ODDS_1)
    private MultiMarketsDetailsTO matchOdds1DetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.FANCY_BETS)
    private MultiMarketsFancyDetailsTO fancyBetDetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.FANCY_BETS_1)
    private MultiMarketsFancyDetailsTO fancyBet1DetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MultiMarketsTiedTO tiedMatch;

    private List<MultiMarketsGoalsTO> goals;

    private List<MultiMarketsGoalsTO> popular;

    public List<MultiMarketsGoalsTO> getPopular() {
        return popular;
    }

    public void setPopular(List<MultiMarketsGoalsTO> popular) {
        this.popular = popular;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String eventName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sportId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isLive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer homeTeamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer awayTeamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamName;
    private String sportName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer eventId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.KICK_OFF_TIME)
    private String eventKickOff;

    public MultiMarketsDetailsTO getMatchOdds1DetailsList() {
        return matchOdds1DetailsList;
    }

    public void setMatchOdds1DetailsList(MultiMarketsDetailsTO matchOdds1DetailsList) {
        this.matchOdds1DetailsList = matchOdds1DetailsList;
    }

    public MultiMarketsFancyDetailsTO getFancyBet1DetailsList() {
        return fancyBet1DetailsList;
    }

    public void setFancyBet1DetailsList(MultiMarketsFancyDetailsTO fancyBet1DetailsList) {
        this.fancyBet1DetailsList = fancyBet1DetailsList;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<MultiMarketsGoalsTO> getGoals() {
        return goals;
    }

    public void setGoals(List<MultiMarketsGoalsTO> goals) {
        this.goals = goals;
    }

    public MultiMarketsFancyDetailsTO getFancyBetDetailsList() {
        return fancyBetDetailsList;
    }

    public void setFancyBetDetailsList(MultiMarketsFancyDetailsTO fancyBetDetailsList) {
        this.fancyBetDetailsList = fancyBetDetailsList;
    }

    public MultiMarketsTiedTO getTiedMatch() {
        return tiedMatch;
    }

    public void setTiedMatch(MultiMarketsTiedTO tiedMatch) {
        this.tiedMatch = tiedMatch;
    }

    public MultiMarketsDetailsTO getMatchOddsDetailsList() {
        return matchOddsDetailsList;
    }

    public void setMatchOddsDetailsList(MultiMarketsDetailsTO matchOddsDetailsList) {
        this.matchOddsDetailsList = matchOddsDetailsList;
    }

    public String getEventKickOff() {
        return eventKickOff;
    }

    public void setEventKickOff(String eventKickOff) {
        this.eventKickOff = eventKickOff;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<MultiMarketsDetailsTO> getMarketGroupsList() {
        return marketGroupsList;
    }

    public void setMarketGroupsList(List<MultiMarketsDetailsTO> marketGroupsList) {
        this.marketGroupsList = marketGroupsList;
    }
}
