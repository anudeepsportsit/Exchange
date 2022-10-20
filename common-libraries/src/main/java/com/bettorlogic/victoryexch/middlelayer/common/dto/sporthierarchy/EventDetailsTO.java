package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.FancyMarketDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.LiveScoreDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.ID)
    private Integer eventId;

    private Integer providerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sportId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clEventId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String scoreTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamScore;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamScore;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamRunRate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamRunRate;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(SportsBookConstants.KICK_OFF_TIME)
    private String eventKickOff;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer homeTeamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer awayTeamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isLive;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(SportsBookConstants.IS_BLOCKED)
    private Integer eventBlockedFlag = 0;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(SportsBookConstants.IS_SUSPENDED)
    private Integer eventSuspendedFlag = 0;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String homeTeamName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String awayTeamName;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(SportsBookConstants.NAME)
    private String eventName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer leagueId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String leagueName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isFancy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String score;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String matchMinutes;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(SportsBookConstants.MARKETS)
    private List<MarketDetailsTO> marketsList = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LiveScoreDetailsTO> scores = new ArrayList<>();
    @JsonProperty(SportsBookConstants.FANCY_BETS)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private FancyMarketDetailsWrapperTO fancyBetDetails;

    @JsonProperty(SportsBookConstants.FANCY_BETS_1)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private FancyMarketDetailsWrapperTO fancyBet1Details;

    @JsonIgnore
    private FancyMarketDetailsWrapperTO fancyMarketDetailsWrapper;


    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public FancyMarketDetailsWrapperTO getFancyBetDetails() {
        return fancyBetDetails;
    }

    public void setFancyBetDetails(FancyMarketDetailsWrapperTO fancyBetDetails) {
        this.fancyBetDetails = fancyBetDetails;
    }

    public FancyMarketDetailsWrapperTO getFancyBet1Details() {
        return fancyBet1Details;
    }

    public void setFancyBet1Details(FancyMarketDetailsWrapperTO fancyBet1Details) {
        this.fancyBet1Details = fancyBet1Details;
    }

    public FancyMarketDetailsWrapperTO getFancyMarketDetailsWrapper() {
        return fancyMarketDetailsWrapper;
    }

    public void setFancyMarketDetailsWrapper(FancyMarketDetailsWrapperTO fancyMarketDetailsWrapper) {
        this.fancyMarketDetailsWrapper = fancyMarketDetailsWrapper;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public List<LiveScoreDetailsTO> getScores() {
        return scores;
    }

    public void setScores(List<LiveScoreDetailsTO> scores) {
        this.scores = scores;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatchMinutes() {
        return matchMinutes;
    }

    public void setMatchMinutes(String matchMinutes) {
        this.matchMinutes = matchMinutes;
    }

    public Integer getIsFancy() {
        return isFancy;
    }

    public void setIsFancy(Integer isFancy) {
        this.isFancy = isFancy;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public List<MarketDetailsTO> getMarketsList() {
        return marketsList;
    }

    public void setMarketsList(List<MarketDetailsTO> marketsList) {
        this.marketsList = marketsList;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventKickOff() {
        return eventKickOff;
    }

    public void setEventKickOff(String eventKickOff) {
        this.eventKickOff = eventKickOff;
    }

    public Integer getEventSuspendedFlag() {
        return eventSuspendedFlag;
    }

    public void setEventSuspendedFlag(Integer eventSuspendedFlag) {
        this.eventSuspendedFlag = eventSuspendedFlag;
    }

    public Integer getEventBlockedFlag() {
        return eventBlockedFlag;
    }

    public void setEventBlockedFlag(Integer eventBlockedFlag) {
        this.eventBlockedFlag = eventBlockedFlag;
    }

    public String getClEventId() {
        return clEventId;
    }

    public void setClEventId(String clEventId) {
        this.clEventId = clEventId;
    }

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }
}
