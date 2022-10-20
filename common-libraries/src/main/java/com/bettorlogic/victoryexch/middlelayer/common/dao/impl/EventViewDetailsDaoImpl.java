package com.bettorlogic.victoryexch.middlelayer.common.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dao.EventViewDetailsDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
class EventViewDetailsDaoImpl implements EventViewDetailsDao {

    private static final String GET_EVENT_VIEW_MATCH_ODD_DETAILS = "select * from get_event_view_match_odd_details(?,?) where provider_id = ?";
    private static final String GET_EVENT_VIEW_POPULAR_MARKET_DETAILS = "select * from get_event_view_popular_market_details(?,?)";
    private static final String GET_EVENT_VIEW_MATCH_SCORE_DETAILS = "select * from get_event_view_match_score_details(?)";
    private static final String GET_EVENT_VIEW_FANCY_BET_DETAILS = "select * from get_event_view_fancy_bet_details(?, ?, ?) where provider_id = ?";
    private static final String GET_EVENT_VIEW_GOAL_MARKET_DETAILS = "select * from get_event_view_goal_market_details(?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public EventViewDetailsWrapperTO getMatchScoreDetails(Integer eventId, String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_EVENT_VIEW_MATCH_SCORE_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, eventId);
            return cs;
        }, (CallableStatement cs) -> this.extractMatchScoreDetailsFromRs(cs.executeQuery()));
    }

    @Override
    public MatchOddDetailsTO getMatchOddDetails(Integer eventId, String userToken, Integer providerId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_EVENT_VIEW_MATCH_ODD_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, eventId);
            cs.setString(2, userToken);
            cs.setInt(3, providerId);
            return cs;
        }, (CallableStatement cs) -> this.extractMatchOddDetailsFromRs(cs.executeQuery()));
    }

    @Override
    public List<MatchOddDetailsTO> getGoalMarketDetails(Integer eventId, String userToken, Integer providerId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_EVENT_VIEW_GOAL_MARKET_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, eventId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.extractGoalMarketsFromResultSet(cs.executeQuery()));
    }

    @Override
    public List<MatchOddDetailsTO> getPopularMarketDetails(Integer eventId, String userToken, Integer marketTypeId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_EVENT_VIEW_POPULAR_MARKET_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, eventId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.extractPopularMarketsFromResultSet(cs.executeQuery()));
    }

    @Override
    public FancyMarketDetailsWrapperTO getFancyBetDetails(Integer eventId, String userToken, Integer providerId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_EVENT_VIEW_FANCY_BET_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, 0);
            cs.setInt(2, eventId);
            cs.setString(3, userToken);
            cs.setInt(4, providerId);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            FancyMarketDetailsWrapperTO fancyMarketDetails = new FancyMarketDetailsWrapperTO();
            List<FancyBetDetailsTO> fancyBetDetails = null;
            while (rs.next()) {
                fancyMarketDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
                fancyMarketDetails.setFancyMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
                fancyMarketDetails.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
                fancyMarketDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
                fancyBetDetails = this.extractEventViewFancyBetDetailsFromResultSet(rs);
            }

            fancyMarketDetails.setFancyBetDetailsList(fancyBetDetails);
            return fancyMarketDetails;
        });
    }

    private List<MatchOddDetailsTO> extractPopularMarketsFromResultSet(ResultSet rs) throws SQLException {
        List<MatchOddDetailsTO> popularMarketDetailsList = new ArrayList<>();
        int lastMarketId = 0;
        int lastOutcomeId = 0;
        EventViewOutcomeDetailsTO outcomeDetails = null;
        MatchOddDetailsTO matchOddDetails = null;
        Set<Double> matchedList = new HashSet<>();
        while (rs.next()) {
            int marketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            if (marketId == lastMarketId) {
                matchOddDetails.setCurrency(rs.getString(ColumnLabelConstants.CURRENCY));
                matchedList.add(rs.getDouble(ColumnLabelConstants.MATCHED));
                this.populateMatchOddDetails(rs, matchOddDetails);
                int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                if (outcomeId == lastOutcomeId) {
                    this.populateOutcomeDetails(rs, outcomeDetails);
                } else {
                    lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                    rs.previous();
                    outcomeDetails = new EventViewOutcomeDetailsTO();
                    matchOddDetails.getOutcomeDetailsList().add(outcomeDetails);
                }
            } else {
                lastMarketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                rs.previous();
                matchOddDetails = new MatchOddDetailsTO();
                popularMarketDetailsList.add(matchOddDetails);
            }
        }
        if (matchOddDetails != null && !matchedList.isEmpty()) {
            matchOddDetails.setMatched(matchedList.stream().mapToDouble(value -> value).sum());
        }
        return popularMarketDetailsList;
    }

    private List<FancyBetDetailsTO> extractEventViewFancyBetDetailsFromResultSet(ResultSet rs) throws SQLException {
        List<FancyBetDetailsTO> fancyBetDetailsList = new ArrayList<>();
        String lastMarketName = "";
        FancyBetDetailsTO fancyBetDetails = null;
        rs.previous();
        while (rs.next()) {
            String marketName = rs.getString(ColumnLabelConstants.MARKET_NAME);
            if (marketName.equals(lastMarketName)) {
                if (fancyBetDetails != null) {
                    this.populateFancyBetDetails(fancyBetDetails, rs);
                    fancyBetDetails.getOutcomeDetailsList().add(this.populateOddDetails(rs));
                }
            } else {
                lastMarketName = rs.getString(ColumnLabelConstants.MARKET_NAME);
                rs.previous();
                fancyBetDetails = new FancyBetDetailsTO();
                fancyBetDetailsList.add(fancyBetDetails);
            }
        }
        return fancyBetDetailsList;
    }

    private MatchOddDetailsTO extractMatchOddDetailsFromRs(ResultSet rs) throws SQLException {
        MatchOddDetailsTO matchOddDetails = new MatchOddDetailsTO();
        EventViewOutcomeDetailsTO outcomeDetails = null;
        int lastOutcomeId = 0;
        while (rs.next()) {
            this.populateMatchOddDetails(rs, matchOddDetails);
            int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
            if (outcomeId == lastOutcomeId) {
                this.populateMatchOddsOutcomeDetails(outcomeDetails, rs);
            } else {
                lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                rs.previous();
                outcomeDetails = new EventViewOutcomeDetailsTO();
                matchOddDetails.getOutcomeDetailsList().add(outcomeDetails);
            }
        }
        return matchOddDetails;
    }

    private void populateMatchOddsOutcomeDetails(EventViewOutcomeDetailsTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setMarketSuspended(rs.getInt("market_suspended"));
        this.populateOddDetails(rs, outcomeDetails, ColumnLabelConstants.BACK_ODDS,
                ColumnLabelConstants.BACK_ODDS_SIZE);
        this.populateOddDetails(rs, outcomeDetails, ColumnLabelConstants.LAY_ODDS,
                ColumnLabelConstants.LAY_ODDS_SIZE);
    }

    private List<MatchOddDetailsTO> extractGoalMarketsFromResultSet(ResultSet rs) throws SQLException {
        List<MatchOddDetailsTO> popularMarketDetailsList = new ArrayList<>();
        String lastSubMarketId = "";
        int lastMarketGroupId = 0;
        int lastOutcomeId = 0;
        EventViewOutcomeDetailsTO outcomeDetails = null;
        MatchOddDetailsTO matchOddDetails = null;
        while (rs.next()) {
            if (rs.getString(ColumnLabelConstants.BL_SUB_MARKET) != null) {
                String marketId = rs.getString(ColumnLabelConstants.BL_SUB_MARKET);
                if (marketId.equals(lastSubMarketId)) {
                    if (matchOddDetails != null) {
                        matchOddDetails.setSubMarket(rs.getString(ColumnLabelConstants.BL_SUB_MARKET));
                        this.populateMatchOddDetails(rs, matchOddDetails);
                    }
                    int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                    if (outcomeId == lastOutcomeId) {
                        if (outcomeDetails != null) {
                            this.populateOutcomeDetails(rs, outcomeDetails);
                        }
                    } else {
                        lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                        rs.previous();
                        outcomeDetails = new EventViewOutcomeDetailsTO();
                        if (matchOddDetails != null) {
                            matchOddDetails.getOutcomeDetailsList().add(outcomeDetails);
                        }
                    }
                } else {
                    lastSubMarketId = rs.getString(ColumnLabelConstants.BL_SUB_MARKET);
                    rs.previous();
                    matchOddDetails = new MatchOddDetailsTO();
                    popularMarketDetailsList.add(matchOddDetails);
                }
            } else {
                Integer marketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                if (marketId.equals(lastMarketGroupId)) {
                    if (matchOddDetails != null) {
                        this.populateMatchOddDetails(rs, matchOddDetails);
                    }
                    int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                    if (outcomeId == lastOutcomeId) {
                        if (outcomeDetails != null) {
                            this.populateOutcomeDetails(rs, outcomeDetails);
                        }
                    } else {
                        lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                        rs.previous();
                        outcomeDetails = new EventViewOutcomeDetailsTO();
                        if (matchOddDetails != null) {
                            matchOddDetails.getOutcomeDetailsList().add(outcomeDetails);
                        }
                    }
                } else {
                    lastMarketGroupId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                    rs.previous();
                    matchOddDetails = new MatchOddDetailsTO();
                    popularMarketDetailsList.add(matchOddDetails);
                }
            }
        }
        return popularMarketDetailsList;
    }

    private OddDetailsTO populateOddDetails(ResultSet rs) throws SQLException {
        OddDetailsTO oddDetails = new OddDetailsTO();
        oddDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        oddDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        int providerId = rs.getInt(ColumnLabelConstants.PROVIDER_ID);
        if (providerId == 3) {
            oddDetails.setOdds(rs.getDouble(ColumnLabelConstants.ODDS));
            oddDetails.setOutcome(rs.getDouble(ColumnLabelConstants.OUTCOME));
        } else if (providerId == 4) {
            oddDetails.setOdds(rs.getDouble(ColumnLabelConstants.BACK_LAY_ODDS));
            oddDetails.setSize(rs.getDouble(ColumnLabelConstants.BACK_LAY_ODDS_SIZE));
        }
        oddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        return oddDetails;
    }

    private void populateFancyBetDetails(FancyBetDetailsTO fancyBetDetails, ResultSet rs) throws SQLException {
        fancyBetDetails.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_ID));
        fancyBetDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        fancyBetDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        fancyBetDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
//                fancyBetDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        fancyBetDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
    }

    private EventViewDetailsWrapperTO extractMatchScoreDetailsFromRs(ResultSet rs) throws SQLException {
        EventViewDetailsWrapperTO eventViewDetailsWrapper = new EventViewDetailsWrapperTO();
        List<LiveScoreDetailsTO> liveScoreDetailsList = new ArrayList<>();
        while (rs.next()) {
            this.populateEventViewDetailsWrapper(eventViewDetailsWrapper, rs);
            LiveScoreDetailsTO liveScoreDetails = this.populateLiveScoreDetails(rs);
            if (!liveScoreDetails.isEmpty()) {
                liveScoreDetailsList.add(liveScoreDetails);
            }
        }
        if (liveScoreDetailsList.size() > 0) {
            eventViewDetailsWrapper.setLiveScoreDetailsList(liveScoreDetailsList);
        }
        return eventViewDetailsWrapper;
    }

    private LiveScoreDetailsTO populateLiveScoreDetails(ResultSet rs) throws SQLException {
        LiveScoreDetailsTO liveScoreDetails = new LiveScoreDetailsTO();
        liveScoreDetails.setHomeTeamScore(rs.getString(ColumnLabelConstants.SCORE_HOME));
        liveScoreDetails.setAwayTeamScore(rs.getString(ColumnLabelConstants.SCORE_AWAY));
        liveScoreDetails.setScoreTitle(rs.getString(ColumnLabelConstants.EVENT_VIEW_SCORE_TITLE));
        return liveScoreDetails;
    }

    private void populateEventViewDetailsWrapper(EventViewDetailsWrapperTO eventViewDetailsWrapper, ResultSet rs) throws SQLException {
        eventViewDetailsWrapper.setCountryId(rs.getInt(ColumnLabelConstants.COUNTRY_ID));
        eventViewDetailsWrapper.setCountryName(rs.getString(ColumnLabelConstants.COUNTRY_NAME));
        eventViewDetailsWrapper.setLeagueId(rs.getInt(ColumnLabelConstants.LEAGUE_ID));
        eventViewDetailsWrapper.setLeagueName(rs.getString(ColumnLabelConstants.LEAGUE_NAME));
        eventViewDetailsWrapper.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
        eventViewDetailsWrapper.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
        eventViewDetailsWrapper.setHomeRunRate(rs.getString(ColumnLabelConstants.HOME_RUN_RATE));
        eventViewDetailsWrapper.setAwayRunRate(rs.getString(ColumnLabelConstants.AWAY_RUN_RATE));
        eventViewDetailsWrapper.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
        eventViewDetailsWrapper.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        eventViewDetailsWrapper.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        eventViewDetailsWrapper.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        eventViewDetailsWrapper.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        eventViewDetailsWrapper.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        eventViewDetailsWrapper.setTimePeriod(rs.getString(ColumnLabelConstants.TIME_PERIOD));
        eventViewDetailsWrapper.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
    }

    private void populateOddDetails(
            ResultSet rs, EventViewOutcomeDetailsTO outcomeDetails, String odds, String size) throws SQLException {
        OddDetailsTO oddDetails = new OddDetailsTO();
        /*Double oddValue=rs.getDouble(odds);
        if( oddValue !=0.0 && oddValue !=null){
            oddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
            oddDetails.setOdds(rs.getDouble(odds));
            oddDetails.setSize(rs.getDouble(size));
            oddDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_ID));
        }*/
        oddDetails.setOdds(rs.getDouble(odds));
        oddDetails.setSize(rs.getDouble(size));
        oddDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_ID));
        oddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        if (odds.equals(ColumnLabelConstants.BACK_ODDS)) {
            if (oddDetails.getOdds() != 0) {
                outcomeDetails.getBackOddList().add(oddDetails);
            }
        } else {
            if (oddDetails.getOdds() != 0) {
                outcomeDetails.getLayOddList().add(oddDetails);
            }
        }
    }

    private void populateOutcomeDetails(ResultSet rs, EventViewOutcomeDetailsTO outcomeDetails) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        this.populateOddDetails(rs, outcomeDetails, ColumnLabelConstants.BACK_ODDS,
                ColumnLabelConstants.BACK_ODDS_SIZE);
        this.populateOddDetails(rs, outcomeDetails, ColumnLabelConstants.LAY_ODDS,
                ColumnLabelConstants.LAY_ODDS_SIZE);
    }

    private void populateMatchOddDetails(ResultSet rs, MatchOddDetailsTO matchOddDetails) throws SQLException {
        matchOddDetails.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        matchOddDetails.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
        matchOddDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        matchOddDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        matchOddDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        matchOddDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        matchOddDetails.setHomeTeam(rs.getString(ColumnLabelConstants.HOME_TEAM));
        matchOddDetails.setAwayTeam(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        matchOddDetails.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
        matchOddDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
    }


}