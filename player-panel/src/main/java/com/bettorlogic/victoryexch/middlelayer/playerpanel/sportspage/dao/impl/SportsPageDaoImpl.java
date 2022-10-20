package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.LiveScoreDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dao.SportsPageDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public class SportsPageDaoImpl implements SportsPageDao {

    private static final String PROC_PROCESS_HIGHLIGHTS = "select * from get_sports_highlights_view(?,?,?,?) order by event_kickoff,event_id,odd_dictionary_id";
    private static final String PROC_PROCESS_LEAGUE_WISE_EVENTS = "select * from get_league_wise_events(?,?,?)";
    private static final String PROC_PROCESS_GET_SPORT_WISE_LEAGUES = "select * from get_sport_wise_leagues(?,?)";
    private static final String PROC_PROCESS_GET_POPULAR_LEAGUES = "select * from get_popular_leagues(?)";
    private static final String PROC_PROCESS_GET_LIVE_EVENTS = "select * from get_sports_livenow(?,?) order by league_id,event_kickoff,event_id,odd_dictionary_id";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public HighlightsWrapperTO getHighlights(Integer sportId, Integer leagueId, String userToken, Integer minutes) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_HIGHLIGHTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setInt(2, leagueId);
            cs.setString(3, userToken);
            cs.setInt(4, minutes);
            return cs;
        }, (CallableStatement cs) -> this.getHighlights(cs.executeQuery()));
    }

    private HighlightsWrapperTO getHighlights(ResultSet rs) throws SQLException {
        HighlightsWrapperTO highlightsTO = new HighlightsWrapperTO();
        EventDetailsTO eventDetailsTO;
        MarketDetailsTO marketDetailsTO;
        OutcomeDetailsTO outcomeDetailsTO;
        LiveScoreDetailsTO scoresDetailsTO;
        List<LiveScoreDetailsTO> scoresDetailsTOS = null;
        List<String> scoresList = new ArrayList();
        List<HighlightsDetailsTO> highlightsDetailsTOS = new ArrayList<>();
        List<EventDetailsTO> highlightsEventTOS = new ArrayList<>();
        List<MarketDetailsTO> highlightsMarketTOS = null;
        List<OutcomeDetailsTO> highlightsOddsTOS = null;
        HighlightsDetailsTO highlightsDetailsTO = new HighlightsDetailsTO();
        int eventId = 0;
        String marketName = "";
        int oddsId = 0;
        String date1 = "";
        String title = "";
        int sport = 0;
        while (rs.next()) {
            sport = rs.getInt(ColumnLabelConstants.SPORT_ID);
            String date2 = rs.getString(ColumnLabelConstants.EVENT_KICKOFF).substring(0, 10);
            int event = rs.getInt(ColumnLabelConstants.EVENT_ID);
            if ((!date1.equalsIgnoreCase(date2)) && date1.length() > 1 && event != eventId) {
                highlightsDetailsTOS.add(highlightsDetailsTO);
                highlightsDetailsTO = new HighlightsDetailsTO();
                highlightsEventTOS = new ArrayList<>();
            }
            if (event != eventId && eventId != 0) {
                if(sport == 4 && highlightsOddsTOS.size() != 3){
                    outcomeDetailsTO = new OutcomeDetailsTO();
                    outcomeDetailsTO.setOutcomeId(0);
                    outcomeDetailsTO.setOutcomeName("X");
                    outcomeDetailsTO.setBackOdds(0.0);
                    outcomeDetailsTO.setLayOdds(0.0);
                    highlightsOddsTOS.add(outcomeDetailsTO);
                    //Collections.swap(highlightsOddsTOS, 1,2);
                }
                if(sport == 4) {
                    Collections.swap(highlightsOddsTOS, 1, 2);
                }
            }
            eventDetailsTO = new EventDetailsTO();
            this.populateEvents(eventDetailsTO, rs);
            if (event != eventId) {
                scoresList.clear();
                scoresDetailsTOS = new ArrayList<>();
                highlightsEventTOS.add(eventDetailsTO);
                highlightsMarketTOS = new ArrayList<>();
                marketName = "";
            }
            eventId = event;
            marketDetailsTO = new MarketDetailsTO();
            String market = rs.getString(ColumnLabelConstants.MARKET_NAME);

            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && sport == 1 && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    highlightsMarketTOS.add(marketDetailsTO);
                    highlightsOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(highlightsMarketTOS);
                    marketName = market;
                    getOutcomesFootball(rs, marketDetailsTO, highlightsOddsTOS);
                    continue;
                }
            }

            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && (sport == 2 || sport == 4) && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    highlightsMarketTOS.add(marketDetailsTO);
                    highlightsOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(highlightsMarketTOS);
                    getOutcomesOther(rs, marketDetailsTO, highlightsOddsTOS, market);
                    continue;
                }
                marketName = market;
            }
            this.populateMarkets(marketDetailsTO, rs);
            String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
            scoresDetailsTO = new LiveScoreDetailsTO();
            if (!(scorettile == null)) {
                if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                    this.populateScores(scoresDetailsTO, rs);
                    scoresDetailsTOS.add(scoresDetailsTO);
                    eventDetailsTO.setScores(scoresDetailsTOS);
                }
            }
            scoresList.add(scorettile);
            title = scorettile;
            if (market != null) {
                if (!market.equalsIgnoreCase(marketName)) {
                    if (event == eventId) {
                        highlightsMarketTOS.add(marketDetailsTO);
                        highlightsOddsTOS = new ArrayList<>();
                        eventDetailsTO.setMarketsList(highlightsMarketTOS);
                        oddsId = 0;
                    }
                }
            }
            marketName = market;
            outcomeDetailsTO = new OutcomeDetailsTO();
            int oddDictionary = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID);
            if (!rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.OVER) && !rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.UNDER)) {
                this.populateOutcomes(outcomeDetailsTO, rs);
                if (oddsId != oddDictionary) {
                    highlightsOddsTOS.add(outcomeDetailsTO);
                    marketDetailsTO.setOutcomesList(highlightsOddsTOS);
                }
            }
            oddsId = oddDictionary;
            highlightsDetailsTO.setDate(date2.substring(0, 10));
            highlightsDetailsTO.setEventDetailsList(highlightsEventTOS);
            date1 = date2;
        }
        populateDrawOutcome(highlightsOddsTOS, sport);
        if(highlightsOddsTOS != null && sport == 4) { Collections.swap(highlightsOddsTOS, 1,2);}
        highlightsDetailsTOS.add(highlightsDetailsTO);
        highlightsTO.setHighlightsDetailsList(highlightsDetailsTOS);
        return highlightsTO;
    }

    private String getOutcomesOther(ResultSet rs, MarketDetailsTO marketDetailsTO, List<OutcomeDetailsTO> highlightsOddsTOS, String market) throws SQLException {
        String marketName;
        OutcomeDetailsTO outcomeDetailsTO;
        marketName = market;
        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.ONE);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.TWO);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);
        marketDetailsTO.setOutcomesList(highlightsOddsTOS);
        return marketName;
    }

    private void getOutcomesFootball(ResultSet rs, MarketDetailsTO marketDetailsTO, List<OutcomeDetailsTO> highlightsOddsTOS) throws SQLException {
        OutcomeDetailsTO outcomeDetailsTO;
        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.ONE);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.X);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.TWO);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);
        marketDetailsTO.setOutcomesList(highlightsOddsTOS);
    }

    @Override
    public LiveEventWrapperTO getEventDetails(Integer sportId, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_LIVE_EVENTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.extractLiveEventsFromResultSet(cs.executeQuery()));
    }

    private LiveEventWrapperTO extractLiveEventsFromResultSet(ResultSet rs) throws SQLException {
        LiveEventWrapperTO liveEventWrapperTO = null;
        List<LiveEventDetailsTO> liveEventDetailsTOS = new ArrayList<>();
        EventDetailsTO eventDetailsTO = new EventDetailsTO();
        MarketDetailsTO marketDetailsTO;
        OutcomeDetailsTO outcomeDetailsTO;
        LiveScoreDetailsTO scoresDetailsTO;
        List<EventDetailsTO> liveEventTOS = new ArrayList<>();
        List<MarketDetailsTO> liveMarketTOS = new ArrayList<>();
        List<OutcomeDetailsTO> liveOddsTOS = new ArrayList<>();
        LiveEventDetailsTO liveEventDetailsTO = new LiveEventDetailsTO();
        List<LiveScoreDetailsTO> scoresDetailsTOS = new ArrayList<>();
        List<String> scoresList = new ArrayList();
        int eventId = 0;
        String marketName = "";
        int oddsId = 0;
        int leagueId = 0;
        String title = "";
        int sport = 0;
        //String countryName = "";
        while (rs.next()) {
            int league = rs.getInt(ColumnLabelConstants.LEAGUE_ID);
            sport = rs.getInt(ColumnLabelConstants.SPORT_ID);
            String leagueName = rs.getString(ColumnLabelConstants.LEAGUE_NAME);
            String country = rs.getString(ColumnLabelConstants.COUNTRY_NAME);
            int event = rs.getInt(ColumnLabelConstants.EVENT_ID);
            if (leagueId != 0 && league != leagueId) {

                /*if(sport == 4) {
                    populateDrawOutcome(liveOddsTOS, sport);
                    Collections.swap(liveOddsTOS, 1, 2);
                }*/

                liveEventDetailsTOS.add(liveEventDetailsTO);
                liveEventTOS = new ArrayList<>();
                liveEventDetailsTO = new LiveEventDetailsTO();
                scoresDetailsTOS = new ArrayList<>();
            }
            if (event != eventId ) {
                if(sport == 4 && eventId != 0) {
                    populateDrawOutcome(liveOddsTOS, sport);
                    Collections.swap(liveOddsTOS, 1, 2);
                }
                scoresList = new ArrayList<>();
                eventDetailsTO = new EventDetailsTO();
                this.populateEvents(eventDetailsTO, rs);
                liveEventTOS.add(eventDetailsTO);
                liveMarketTOS = new ArrayList<>();
                marketName = "";
            }
            eventId = event;
            marketDetailsTO = new MarketDetailsTO();
            String market = rs.getString(ColumnLabelConstants.MARKET_NAME);
            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && sport == 1 && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    liveMarketTOS.add(marketDetailsTO);
                    liveOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(liveMarketTOS);
                    marketName = market;
                    getOutcomesFootball(rs, marketDetailsTO, liveOddsTOS);
                    leagueId = league;
                    continue;
                }
            }
            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && (sport == 2 || sport == 4) && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    liveMarketTOS.add(marketDetailsTO);
                    marketName = market;
                    liveOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(liveMarketTOS);
                    getOutcomesOther(rs, marketDetailsTO, liveOddsTOS, market);
                    leagueId = league;
                    continue;
                }
            }
            String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
            scoresDetailsTO = new LiveScoreDetailsTO();
            if (!(scorettile == null)) {
                if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                    this.populateScores(scoresDetailsTO, rs);
                    scoresDetailsTOS.add(scoresDetailsTO);
                    eventDetailsTO.setScores(scoresDetailsTOS);
                }
            }
            scoresList.add(scorettile);
            title = scorettile;
            if (market != null) {
                if (!market.equalsIgnoreCase(marketName)) {
                    if (event == eventId) {
                        this.populateMarkets(marketDetailsTO, rs);
                        liveMarketTOS.add(marketDetailsTO);
                        liveOddsTOS = new ArrayList<>();
                        eventDetailsTO.setMarketsList(liveMarketTOS);
                        oddsId = 0;
                    }
                }
            }
            marketName = market;
            outcomeDetailsTO = new OutcomeDetailsTO();
            int oddDictionary = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID);
            if (!rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.OVER) && !rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.UNDER)) {
                if (oddsId != oddDictionary) {
                    this.populateOutcomes(outcomeDetailsTO, rs);
                    liveOddsTOS.add(outcomeDetailsTO);
                    marketDetailsTO.setOutcomesList(liveOddsTOS);
                }
            }
            oddsId = oddDictionary;
            liveEventDetailsTO.setLeagueId(league);
            liveEventDetailsTO.setLeagueName(leagueName);
            liveEventDetailsTO.setCountryName(country);
            liveEventDetailsTO.setEventList(liveEventTOS);
            leagueId = league;
        }

        populateDrawOutcome(liveOddsTOS, sport);
        if(sport == 4){Collections.swap(liveOddsTOS, 1,2);}
        if (liveEventDetailsTO.getLeagueId() != null) {
            liveEventWrapperTO = new LiveEventWrapperTO();
            liveEventDetailsTOS.add(liveEventDetailsTO);
            liveEventWrapperTO.setLiveEventDetails(liveEventDetailsTOS);
        }
        return liveEventWrapperTO;
    }

    private void populateDrawOutcome(List<OutcomeDetailsTO> liveOddsTOS, int sport) {
        OutcomeDetailsTO outcomeDetailsTO;
        if (sport == 4 && liveOddsTOS.size() != 3) {
            outcomeDetailsTO = new OutcomeDetailsTO();
            outcomeDetailsTO.setOutcomeId(0);
            outcomeDetailsTO.setOutcomeName("X");
            outcomeDetailsTO.setBackOdds(0.0);
            outcomeDetailsTO.setLayOdds(0.0);
            liveOddsTOS.add(outcomeDetailsTO);
        }
    }

    private void populateScores(LiveScoreDetailsTO scoresDetailsTO, ResultSet rs) throws SQLException {
        scoresDetailsTO.setScoreTitle(rs.getString(ColumnLabelConstants.SCORE_TITLE));
        scoresDetailsTO.setHomeTeamScore(rs.getString(ColumnLabelConstants.HOME_TEAM_SCORE));
        scoresDetailsTO.setAwayTeamScore(rs.getString(ColumnLabelConstants.AWAY_TEAM_SCORE));
        scoresDetailsTO.setHomeTeamRunRate(rs.getString(ColumnLabelConstants.HOME_TEAM_RUNRATE));
        scoresDetailsTO.setAwayTeamRunRate(rs.getString(ColumnLabelConstants.AWAY_TEAM_RUNRATE));
        scoresDetailsTO.setMatchMinutes(rs.getString(ColumnLabelConstants.MATCH_MINUTES));
    }


    @Override
    public PopularLeaguesWrapperTO getPopularLeagues(Integer sportId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_POPULAR_LEAGUES,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            return cs;
        }, (CallableStatement cs) -> this.extractPopularLeaguesFromResultSet(cs.executeQuery()));
    }

    private PopularLeaguesWrapperTO extractPopularLeaguesFromResultSet(ResultSet executeQuery) throws SQLException {
        PopularLeaguesWrapperTO popularLeaguesWrapperTO = new PopularLeaguesWrapperTO();
        List<LeagueDetailsTO> leaguesList = new ArrayList<>();
        while (executeQuery.next()) {
            LeagueDetailsTO details = new LeagueDetailsTO();
            details.setLeagueId(executeQuery.getInt(HomePageConstants.LEAGUE_ID));
            details.setLeagueName(executeQuery.getString(HomePageConstants.LEAGUE_NAME));
            details.setCountryId(executeQuery.getInt(HomePageConstants.COUNTRY_ID));
            details.setCountryName(executeQuery.getString(HomePageConstants.COUNTRY_NAME));
            leaguesList.add(details);
        }
        popularLeaguesWrapperTO.setPopularLeagues(leaguesList);
        return popularLeaguesWrapperTO;
    }

    @Override
    public LeagueEventDetailsWrapperTO getLeagueWiseEvents(Integer leagueId, Integer timeInterval, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_LEAGUE_WISE_EVENTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, leagueId);
            cs.setInt(2, timeInterval);
            cs.setString(3, userToken);
            return cs;
        }, (CallableStatement cs) -> this.extractLeagueWiseEventsFromResultSet(cs.executeQuery()));
    }

    @Override
    public CountryDetailsWrapperTO getSportWiseLeagues(Integer sportId, Integer timeInterval) {

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_SPORT_WISE_LEAGUES,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setInt(2, timeInterval);
            return cs;
        }, (CallableStatement cs) -> this.extractSportWiseLeaguesFromResultSet(cs.executeQuery()));
    }

    private CountryDetailsWrapperTO extractSportWiseLeaguesFromResultSet(ResultSet rs) throws SQLException {
        CountryDetailsWrapperTO countrySportDetails = new CountryDetailsWrapperTO();
        CountryDetailsTO countryDetails = null;
        int lastCountryId = 0;
        LeagueDetailsTO leagueDetails;
        while (rs.next()) {
            int countryId = rs.getInt(ColumnLabelConstants.COUNTRY_ID);
            if (countryId == lastCountryId) {
                assert countryDetails != null;
                countryDetails.setCountryId(rs.getInt(ColumnLabelConstants.COUNTRY_ID));
                countryDetails.setCountryName(rs.getString(ColumnLabelConstants.COUNTRY_NAME));
                leagueDetails = new LeagueDetailsTO();
                leagueDetails.setLeagueId(rs.getInt(ColumnLabelConstants.LEAGUE_ID));
                leagueDetails.setLeagueName(rs.getString(ColumnLabelConstants.LEAGUE_NAME));
                countryDetails.getLeagueDetailsList().add(leagueDetails);
            } else {
                lastCountryId = rs.getInt(ColumnLabelConstants.COUNTRY_ID);
                rs.previous();
                countryDetails = new CountryDetailsTO();
                countrySportDetails.getCountriesList().add(countryDetails);
            }
        }
        return countrySportDetails;
    }

    private LeagueEventDetailsWrapperTO extractLeagueWiseEventsFromResultSet(ResultSet rs) throws SQLException {
        LeagueEventDetailsWrapperTO leagueEventDetailsWrapper = new LeagueEventDetailsWrapperTO();
        MarketDetailsTO marketDetails = null;
        int lastEventId = 0;
        EventDetailsTO eventDetails = null;
        int lastMarketId = 0;
        int lastOutcomeId = 0;
        HighlightsDetailsTO highlightsDetails = null;
        String lastDate = null;
        EventViewOutcomeDetailsTO outcomeDetails = null;
        this.populateLeagueWiseEventsToResultSet(rs, leagueEventDetailsWrapper,
                lastEventId, eventDetails, lastMarketId, lastDate, marketDetails, lastOutcomeId, highlightsDetails, outcomeDetails);
        return leagueEventDetailsWrapper;
    }

    private void populateLeagueWiseEventsToResultSet(ResultSet rs, LeagueEventDetailsWrapperTO leagueEventDetailsWrapper,
                                                     int lastEventId, EventDetailsTO eventDetails, int lastMarketId,
                                                     String lastDate, MarketDetailsTO marketDetails, int lastOutcomeId,
                                                     HighlightsDetailsTO highlightsDetails, EventViewOutcomeDetailsTO outcomeDetails) throws SQLException {
        int sportId = 0;
        while (rs.next()) {
            String date = rs.getString(ColumnLabelConstants.EVENT_KICKOFF).substring(0, 10);
            if (date.equals(lastDate)) {
                highlightsDetails.setDate(rs.getString(ColumnLabelConstants.EVENT_KICKOFF).substring(0, 10));
                int eventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                int marketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                sportId = rs.getInt(ColumnLabelConstants.SPORT_ID);
                if (eventId == lastEventId && marketId == lastMarketId) {
                    assert eventDetails != null;
                    this.populateEventsForLeagueEvents(eventDetails, rs);
                    this.populateMarketsForLeagueEvents(marketDetails, rs);
                    int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                    if (outcomeId == lastOutcomeId) {
                        assert outcomeDetails != null;
                        this.populateOutcomeDetails(outcomeDetails, rs);
                    } else {
                        lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                        rs.previous();
                        outcomeDetails = new EventViewOutcomeDetailsTO();
                        marketDetails.getEventViewOutcomeDetailsList().add(outcomeDetails);
                    }
                } else {
                    if(lastEventId != 0) {
                        if (sportId == 4 && marketDetails.getEventViewOutcomeDetailsList().size() != 3) {
                            outcomeDetails = new EventViewOutcomeDetailsTO();
                            this.populateNullOutcomes(outcomeDetails);
                            marketDetails.getEventViewOutcomeDetailsList().add(outcomeDetails);
                        }
                        if(sportId == 4){
                            Collections.swap(marketDetails.getEventViewOutcomeDetailsList(), 1, 2);
                        }
                    }
                    lastEventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                    lastMarketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                    rs.previous();
                    eventDetails = new EventDetailsTO();
                    marketDetails = new MarketDetailsTO();
                    eventDetails.getMarketsList().add(marketDetails);
                    highlightsDetails.getEventDetailsList().add(eventDetails);
                }
            } else {
                lastDate = rs.getString(ColumnLabelConstants.EVENT_KICKOFF).substring(0, 10);
                rs.previous();
                highlightsDetails = new HighlightsDetailsTO();
                leagueEventDetailsWrapper.getLeagueDetailsList().add(highlightsDetails);
            }
        }
        if(sportId == 4 && marketDetails.getEventViewOutcomeDetailsList().size() != 3){
            outcomeDetails = new EventViewOutcomeDetailsTO();
            this.populateNullOutcomes(outcomeDetails);
            marketDetails.getEventViewOutcomeDetailsList().add(outcomeDetails);
        }
        if(sportId == 4){
            Collections.swap(marketDetails.getEventViewOutcomeDetailsList(), 1, 2);
        }
    }

    private void populateNullOutcomes(EventViewOutcomeDetailsTO outcomeDetails){
        outcomeDetails.setOutcomeId(0);
        outcomeDetails.setClOutcomeId("0");
        outcomeDetails.setOutcomeName("X");
        OddDetailsTO backOddDetails = new OddDetailsTO();
        backOddDetails.setOdds(0.0);
        backOddDetails.setSize(0.0);
        OddDetailsTO layOddDetails = new OddDetailsTO();
        layOddDetails.setOdds(0.0);
        layOddDetails.setSize(0.0);
    }

    private void populateOutcomeDetails(EventViewOutcomeDetailsTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        OddDetailsTO backOddDetails = new OddDetailsTO();
        backOddDetails.setOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.BACK_ODDS))));
        backOddDetails.setSize(rs.getDouble(ColumnLabelConstants.BACK_ODDS_SIZE));
        backOddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        if (backOddDetails.getOdds() != null && backOddDetails.getSize() != null) {
            outcomeDetails.getBackOddList().add(backOddDetails);
        }
        OddDetailsTO layOddDetails = new OddDetailsTO();
        layOddDetails.setOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.LAY_ODDS))));
        layOddDetails.setSize(rs.getDouble(ColumnLabelConstants.LAY_ODDS_SIZE));
        layOddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        if (layOddDetails.getOdds() != null && layOddDetails.getSize() != null) {
            outcomeDetails.getLayOddList().add(layOddDetails);
        }
    }

    private void populateOutcomes(OutcomeDetailsTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setLayOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.LAY_ODDS))));
        outcomeDetails.setBackOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.BACK_ODDS))));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
    }

    private void populateMarketsForLeagueEvents(MarketDetailsTO marketDetails, ResultSet rs) throws SQLException {
        marketDetails.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        marketDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        marketDetails.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
        marketDetails.setSubMarketValue(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));
        marketDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        marketDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
    }

    private void populateMarkets(MarketDetailsTO marketDetails, ResultSet rs) throws SQLException {
        marketDetails.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        marketDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        marketDetails.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
        marketDetails.setSubMarketValue(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));
        marketDetails.setMarketSuspendedFlag(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        marketDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        marketDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
    }

    private void populateEventsForLeagueEvents(EventDetailsTO eventDetails, ResultSet rs) throws SQLException {
        eventDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        eventDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        eventDetails.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
        eventDetails.setClEventId(rs.getString(ColumnLabelConstants.CL_EVENT_ID));
        eventDetails.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        eventDetails.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        eventDetails.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        eventDetails.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        eventDetails.setIsFancy(rs.getInt(ColumnLabelConstants.EVENT_ISFANCY));
//        eventDetails.setEventBlockedFlag(rs.getInt(ColumnLabelConstants.EVENT_BLOCKED));
        eventDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        eventDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
    }

    private void populateEvents(EventDetailsTO eventDetails, ResultSet rs) throws SQLException {
        eventDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        eventDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        eventDetails.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
        eventDetails.setLeagueId(rs.getInt(ColumnLabelConstants.LEAGUE_ID));
        eventDetails.setClEventId(rs.getString(ColumnLabelConstants.CL_EVENT_ID));
        eventDetails.setLeagueName(rs.getString(ColumnLabelConstants.LEAGUE_NAME));
        eventDetails.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        eventDetails.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        eventDetails.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        eventDetails.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        eventDetails.setIsFancy(rs.getInt(ColumnLabelConstants.EVENT_ISFANCY));
//        eventDetails.setEventBlockedFlag(rs.getInt(ColumnLabelConstants.EVENT_BLOCKED));
//        eventDetails.setEventSuspendedFlag(rs.getInt(ColumnLabelConstants.EVENT_SUSPENDED));
        eventDetails.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
        eventDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PIN));
        eventDetails.setMatchMinutes(rs.getString(ColumnLabelConstants.MATCH_MINUTES));
        eventDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
    }

}
