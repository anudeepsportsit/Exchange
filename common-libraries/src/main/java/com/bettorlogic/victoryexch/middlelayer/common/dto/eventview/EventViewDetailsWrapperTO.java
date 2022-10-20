package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EventViewDetailsWrapperTO {
    private Integer countryId;
    private String countryName;
    private Integer leagueId;
    private String leagueName;
    private Integer homeTeamId;
    private String homeTeamName;
    private Integer awayTeamId;
    private String awayTeamName;
    private String homeRunRate;
    private String awayRunRate;
    @JsonProperty(SportsBookConstants.KICK_OFF_TIME)
    private String eventKickOff;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sportId;

    private String sportName;
    private String timePeriod;
    private Integer isLive;
    private String eventName;

    @JsonProperty(SportsBookConstants.LIVE_SCORE_DETAILS)
    private List<LiveScoreDetailsTO> liveScoreDetailsList = new ArrayList<>();

    @JsonProperty(SportsBookConstants.MATCH_ODDS)
    private MatchOddDetailsTO matchOddDetails;

    @JsonProperty(SportsBookConstants.MATCH_ODDS_1)
    private MatchOddDetailsTO matchOdd1Details;

    @JsonProperty(SportsBookConstants.FANCY_BETS)
    private FancyMarketDetailsWrapperTO fancyBetDetails;

    @JsonProperty(SportsBookConstants.FANCY_BETS_1)
    private FancyMarketDetailsWrapperTO fancyBet1Details;

    @JsonProperty(SportsBookConstants.POPULAR)
    private List<MatchOddDetailsTO> popularMarketDetailsList;

    @JsonProperty(SportsBookConstants.GOALS_MARKET_GROUP_NAME)
    private List<MatchOddDetailsTO> goalMarketDetailsList;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public MatchOddDetailsTO getMatchOddDetails() {
        return matchOddDetails;
    }

    public void setMatchOddDetails(MatchOddDetailsTO matchOddDetails) {
        this.matchOddDetails = matchOddDetails;
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

    public String getHomeRunRate() {
        return homeRunRate;
    }

    public void setHomeRunRate(String homeRunRate) {
        this.homeRunRate = homeRunRate;
    }

    public String getAwayRunRate() {
        return awayRunRate;
    }

    public void setAwayRunRate(String awayRunRate) {
        this.awayRunRate = awayRunRate;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public FancyMarketDetailsWrapperTO getFancyBetDetails() {
        return fancyBetDetails;
    }

    public void setFancyBetDetails(FancyMarketDetailsWrapperTO fancyBetDetails) {
        this.fancyBetDetails = fancyBetDetails;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<LiveScoreDetailsTO> getLiveScoreDetailsList() {
        return liveScoreDetailsList;
    }

    public void setLiveScoreDetailsList(List<LiveScoreDetailsTO> liveScoreDetailsList) {
        this.liveScoreDetailsList = liveScoreDetailsList;
    }

    public List<MatchOddDetailsTO> getPopularMarketDetailsList() {
        return popularMarketDetailsList;
    }

    public void setPopularMarketDetailsList(List<MatchOddDetailsTO> popularMarketDetailsList) {
        this.popularMarketDetailsList = popularMarketDetailsList;
    }

    public List<MatchOddDetailsTO> getGoalMarketDetailsList() {
        return goalMarketDetailsList;
    }

    public void setGoalMarketDetailsList(List<MatchOddDetailsTO> goalMarketDetailsList) {
        this.goalMarketDetailsList = goalMarketDetailsList;
    }

    public String getEventKickOff() {
        return eventKickOff;
    }

    public void setEventKickOff(String eventKickOff) {
        this.eventKickOff = eventKickOff;
    }

    public FancyMarketDetailsWrapperTO getFancyBet1Details() {
        return fancyBet1Details;
    }

    public void setFancyBet1Details(FancyMarketDetailsWrapperTO fancyBet1Details) {
        this.fancyBet1Details = fancyBet1Details;
    }

    public MatchOddDetailsTO getMatchOdd1Details() {
        return matchOdd1Details;
    }

    public void setMatchOdd1Details(MatchOddDetailsTO matchOdd1Details) {
        this.matchOdd1Details = matchOdd1Details;
    }
}