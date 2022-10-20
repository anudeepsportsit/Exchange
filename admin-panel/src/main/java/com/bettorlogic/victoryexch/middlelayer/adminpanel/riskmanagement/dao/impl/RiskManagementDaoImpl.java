package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dao.RiskManagementDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RiskManagementDaoImpl implements RiskManagementDao {

    private static final String PROC_GET_TOP_MATCHED_PLAYERS = "select * from get_top_matched_amount_users(?)";
    private static final String PROC_GET_TOP_EXPOSURE_PLAYERS = "select * from get_top_exposure_users(?)";
    private static final String PROC_GET_MATCH_ODDS = "select * from get_risk_management_match_odds(?)";
    private static final String PROC_GET_FANCY_BETS = "select * from get_risk_management_fancy_bets(?)";
    private static final String PROC_GET_OTHER_MARKETS = "select * from get_risk_management_other_markets(?)";
    private static final String PROC_PROCESS_GET_MATCH_ODDS_LIST = "select * from get_risk_management_dropdown_matchodds(?,?,?) order by event_id,market_name,odd_dictionary,back_odds_size desc,lay_odds_size desc";
    private static final String PROC_PROCESS_GET_FANCY_LIST = "select * from get_risk_management_dropdown_fancy_bets(?,?)";
    private static final String PROC_PROCESS_GET_FANCY_BACK_LIST = "select * from get_risk_management_dropdown_fancy_bets_backlay(?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<PlayersDetailsTO> getMatchedDetails(ResultSet executeQuery) throws SQLException {
        List<PlayersDetailsTO> playersDetailsList = new ArrayList<>();
        while (executeQuery.next()) {
            PlayersDetailsTO playersDetails = new PlayersDetailsTO();
            playersDetails.setUserName(executeQuery.getString(ColumnLabelConstants.USER_NAME));
            playersDetails.setUserId(executeQuery.getInt(ColumnLabelConstants.USER_ID));
            playersDetails.setUserAmount(executeQuery.getString(ColumnLabelConstants.USER_MATCHED_AMOUNT));
            playersDetails.setUserExposure(executeQuery.getString(ColumnLabelConstants.USER_EXPOSURE));
            playersDetailsList.add(playersDetails);
        }
        return playersDetailsList;
    }

    @Override
    public List<PlayersDetailsTO> getExposurePlayersInfo(String loginToken) {
        String query = PROC_GET_TOP_EXPOSURE_PLAYERS;
        return jdbcTemplate.execute(connection -> executeCallableStatement(loginToken, query, connection), (CallableStatement cs) -> getExposureDetails(cs.executeQuery()));
    }

    private CallableStatement executeCallableStatement(String loginToken, String query, Connection connection) throws SQLException {
        CallableStatement cs = connection.prepareCall(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        cs.setString(1, loginToken);
        return cs;
    }

    @Override
    public MatchOddsWrapperTO getMatchOdds(String loginToken) {
        String query = PROC_GET_MATCH_ODDS;
        return jdbcTemplate.execute(connection -> executeCallableStatement(loginToken, query, connection), (CallableStatement cs) -> getMatchOddsDetails(cs.executeQuery()));
    }

    @Override
    public FancyBetsWrapperTO getFancyBets(String loginToken) {
        String query = PROC_GET_FANCY_BETS;
        return jdbcTemplate.execute(connection -> executeCallableStatement(loginToken, query, connection), (CallableStatement cs) -> getFancyDetails(cs.executeQuery()));
    }

    @Override
    public OtherMarketsWrapperTO getTiedGoals(String loginToken) {
        String query = PROC_GET_OTHER_MARKETS;
        return jdbcTemplate.execute(connection -> executeCallableStatement(loginToken, query, connection), (CallableStatement cs) -> getOtherDetails(cs.executeQuery()));
    }

    @Override
    public List<PlayersDetailsTO> getMatchedPlayersInfo(String loginToken) {
        String query = PROC_GET_TOP_MATCHED_PLAYERS;
        return jdbcTemplate.execute(connection -> executeCallableStatement(loginToken, query, connection), (CallableStatement cs) -> getMatchedDetails(cs.executeQuery()));
    }


    @Override
    public MatchOddsDropdownWrapperTO getMatchOddsList(Integer eventId, Integer sportId, Integer userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_MATCH_ODDS_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setInt(2, eventId);
            cs.setInt(3, userId);
            return cs;
        }, (CallableStatement cs) -> getMatchOddsListHierarchy(cs.executeQuery()));
    }

    @Override
    public FancyDropDownWrapperTO getFancyDetails(Integer eventId, String marketName) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_FANCY_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, marketName);
            cs.setInt(2, eventId);
            return cs;
        }, (CallableStatement cs) -> getFancyListHierarchy(cs.executeQuery()));
    }

    @Override
    public FancyDropDownWrapperTO getFancyDetailsBack(Integer eventId, String marketName) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_GET_FANCY_BACK_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, marketName);
            cs.setInt(2, eventId);
            return cs;
        }, (CallableStatement cs) -> getFancyBackListHierarchy(cs.executeQuery()));
    }

    private FancyDropDownWrapperTO getFancyBackListHierarchy(ResultSet executeQuery) throws SQLException {
        FancyDropDownWrapperTO fancyDropDownWrapper = new FancyDropDownWrapperTO();
        List<FancyDownDownOutcomesTO> fancyDownDownOutcomes = new ArrayList<>();
        while (executeQuery.next()) {
            fancyDropDownWrapper.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            fancyDropDownWrapper.setMarketName(executeQuery.getString(ColumnLabelConstants.FANCY_MARKET_NAME));
            fancyDropDownWrapper.setMinStake(executeQuery.getDouble(ColumnLabelConstants.MIN_STAKE));
            fancyDropDownWrapper.setMaxStake(executeQuery.getDouble(ColumnLabelConstants.MAX_STAKE));
            FancyDownDownOutcomesTO fancyDownDownOutcome = new FancyDownDownOutcomesTO();

            fancyDownDownOutcome.setId(executeQuery.getInt(ColumnLabelConstants.ODDS_ID));
            fancyDownDownOutcome.setName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            fancyDownDownOutcome.setOdds(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS));
            fancyDownDownOutcome.setSize(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS_SIZE));
            fancyDownDownOutcomes.add(fancyDownDownOutcome);

            executeQuery.next();
            fancyDownDownOutcome = new FancyDownDownOutcomesTO();
            fancyDownDownOutcome.setId(executeQuery.getInt(ColumnLabelConstants.ODDS_ID));
            fancyDownDownOutcome.setName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            fancyDownDownOutcome.setOdds(executeQuery.getDouble(ColumnLabelConstants.LAY_ODDS));
            fancyDownDownOutcome.setSize(executeQuery.getDouble(ColumnLabelConstants.LAY_ODDS_SIZE));
            fancyDownDownOutcomes.add(fancyDownDownOutcome);
            fancyDropDownWrapper.setOutcomes(fancyDownDownOutcomes);
        }
        return fancyDropDownWrapper;
    }

    private FancyDropDownWrapperTO getFancyListHierarchy(ResultSet executeQuery) throws SQLException {
        FancyDropDownWrapperTO fancyDropDownWrapper = new FancyDropDownWrapperTO();
        List<FancyDownDownOutcomesTO> fancyDownDownOutcomes = new ArrayList<>();
        while (executeQuery.next()) {
            fancyDropDownWrapper.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            fancyDropDownWrapper.setMarketName(executeQuery.getString(ColumnLabelConstants.FANCY_MARKET_NAME));
            fancyDropDownWrapper.setMinStake(executeQuery.getDouble(ColumnLabelConstants.MIN_STAKE));
            fancyDropDownWrapper.setMaxStake(executeQuery.getDouble(ColumnLabelConstants.MAX_STAKE));
            FancyDownDownOutcomesTO fancyDownDownOutcome = new FancyDownDownOutcomesTO();

            fancyDownDownOutcome.setId(executeQuery.getInt(ColumnLabelConstants.ODDS_ID));
            fancyDownDownOutcome.setName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            fancyDownDownOutcome.setOdds(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS));
            fancyDownDownOutcome.setSize(executeQuery.getDouble(ColumnLabelConstants.OUTCOME_SIZE));
            fancyDownDownOutcomes.add(fancyDownDownOutcome);

            executeQuery.next();
            fancyDownDownOutcome = new FancyDownDownOutcomesTO();
            fancyDownDownOutcome.setId(executeQuery.getInt(ColumnLabelConstants.ODDS_ID));
            fancyDownDownOutcome.setName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            fancyDownDownOutcome.setOdds(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS));
            fancyDownDownOutcome.setSize(executeQuery.getDouble(ColumnLabelConstants.OUTCOME_SIZE));
            fancyDownDownOutcomes.add(fancyDownDownOutcome);
            fancyDropDownWrapper.setOutcomes(fancyDownDownOutcomes);
        }
        return fancyDropDownWrapper;
    }

    private MatchOddsDropdownWrapperTO getMatchOddsListHierarchy(ResultSet executeQuery) throws SQLException {
        MatchOddsDropdownWrapperTO matchOddsDropdownWrapper = new MatchOddsDropdownWrapperTO();
        List<OddsDetailsTO> backOddsDetails = null;
        List<OddsDetailsTO> layOddsDetails = null;
        List<OutcomesDetailsTO> outcomesDetails = new ArrayList<>();
        OddsDetailsTO oddsDetails = null;
        OutcomesDetailsTO outcomesDetail = null;
        while (executeQuery.next()) {
            matchOddsDropdownWrapper.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            matchOddsDropdownWrapper.setEventName(executeQuery.getString(ColumnLabelConstants.EVENT_NAME));
            matchOddsDropdownWrapper.setStakeAmount(executeQuery.getString(ColumnLabelConstants.STAKE_AMOUNT));
            matchOddsDropdownWrapper.setMatchedAmount(executeQuery.getString(ColumnLabelConstants.USER_BALANCE));
            matchOddsDropdownWrapper.setSportId(executeQuery.getInt(ColumnLabelConstants.SPORT_ID));
            if (matchOddsDropdownWrapper.getSportId() == 1) {
                outcomesDetail = new OutcomesDetailsTO();
                outcomesDetail.setId(executeQuery.getInt(ColumnLabelConstants.HOME_TEAM_ID));
                outcomesDetail.setName(executeQuery.getString(ColumnLabelConstants.HOME_TEAM));
                populateOutcomesList(executeQuery, outcomesDetails, outcomesDetail);
                outcomesDetail = new OutcomesDetailsTO();
                outcomesDetail.setId(2);
                outcomesDetail.setName(ColumnLabelConstants.DRAW);
                populateOutcomesList(executeQuery, outcomesDetails, outcomesDetail);
                outcomesDetail = new OutcomesDetailsTO();
                outcomesDetail.setId(executeQuery.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
                outcomesDetail.setName(executeQuery.getString(ColumnLabelConstants.AWAY_TEAM));
                populateOutcomesList(executeQuery, outcomesDetails, outcomesDetail);
            } else {
                outcomesDetail = new OutcomesDetailsTO();
                outcomesDetail.setId(executeQuery.getInt(ColumnLabelConstants.HOME_TEAM_ID));
                outcomesDetail.setName(executeQuery.getString(ColumnLabelConstants.HOME_TEAM));
                populateOutcomesList(executeQuery, outcomesDetails, outcomesDetail);
                outcomesDetail = new OutcomesDetailsTO();
                outcomesDetail.setId(executeQuery.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
                outcomesDetail.setName(executeQuery.getString(ColumnLabelConstants.AWAY_TEAM));
                populateOutcomesList(executeQuery, outcomesDetails, outcomesDetail);

            }
        }
        matchOddsDropdownWrapper.setOutcomes(outcomesDetails);
        return matchOddsDropdownWrapper;
    }

    private void populateOutcomesList(ResultSet executeQuery, List<OutcomesDetailsTO> outcomesDetails, OutcomesDetailsTO outcomesDetail) throws SQLException {
        List<OddsDetailsTO> backOddsDetails = new ArrayList<>();
        List<OddsDetailsTO> layOddsDetails = new ArrayList<>();
        populateOddsList(executeQuery, backOddsDetails, layOddsDetails);
        populateOddsList(executeQuery, backOddsDetails, layOddsDetails);
        populateOddsList(executeQuery, backOddsDetails, layOddsDetails);
        outcomesDetail.setBackOdds(backOddsDetails);
        outcomesDetail.setLayOdds(layOddsDetails);
        outcomesDetails.add(outcomesDetail);
    }

    private void populateOddsList(ResultSet executeQuery, List<OddsDetailsTO> backOddsDetails, List<OddsDetailsTO> layOddsDetails) throws SQLException {
        OddsDetailsTO oddsDetails;
        oddsDetails = new OddsDetailsTO();
        oddsDetails.setOdds(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS));
        oddsDetails.setSize(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS_SIZE));
        backOddsDetails.add(oddsDetails);
        oddsDetails = new OddsDetailsTO();
        oddsDetails.setOdds(executeQuery.getDouble(ColumnLabelConstants.LAY_ODDS));
        oddsDetails.setSize(executeQuery.getDouble(ColumnLabelConstants.LAY_ODDS_SIZE));
        layOddsDetails.add(oddsDetails);
        executeQuery.next();
    }

    private OtherMarketsWrapperTO getOtherDetails(ResultSet executeQuery) throws SQLException {
        OtherMarketsWrapperTO otherMarketsWrapper = new OtherMarketsWrapperTO();
        List<MatchOddsBetsDetailsTO> matchOddsBetsDetails = new ArrayList<>();
        List<MatchOddsDetailsTO> matchOddsDetails = new ArrayList<>();
        MatchOddsDetailsTO matchOddsDetail = new MatchOddsDetailsTO();
        while (executeQuery.next()) {
            Integer sportId = executeQuery.getInt(ColumnLabelConstants.SPORT_ID);
            if (matchOddsDetail.getSportId() != sportId && matchOddsDetail.getSportId() != null) {
                matchOddsDetails.add(matchOddsDetail);
                matchOddsDetail = new MatchOddsDetailsTO();
                matchOddsBetsDetails = new ArrayList<>();
            }
            matchOddsDetail.setSportId(executeQuery.getInt(ColumnLabelConstants.SPORT_ID));
            matchOddsDetail.setSportName(executeQuery.getString(ColumnLabelConstants.SPORT_NAME));
            MatchOddsBetsDetailsTO matchOddsBetsDetailsTO = new MatchOddsBetsDetailsTO();
            matchOddsBetsDetailsTO.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            matchOddsBetsDetailsTO.setEventName(executeQuery.getString(ColumnLabelConstants.EVENT_NAME));
            matchOddsBetsDetailsTO.setMarketDate(executeQuery.getString(ColumnLabelConstants.BET_PLACED).substring(0, 10));
            matchOddsBetsDetailsTO.setMarketName(executeQuery.getString(ColumnLabelConstants.MARKET_NAME));
            matchOddsBetsDetailsTO.setOdds(executeQuery.getString(ColumnLabelConstants.ODDS));
            matchOddsBetsDetailsTO.setOutcomeName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            matchOddsBetsDetailsTO.setUserId(executeQuery.getInt(ColumnLabelConstants.USER_ID));
            matchOddsBetsDetailsTO.setUserName(executeQuery.getString(ColumnLabelConstants.USER_NAME));
            matchOddsBetsDetailsTO.setStakeAmount(executeQuery.getString(ColumnLabelConstants.STAKE_AMOUNT));
            matchOddsBetsDetails.add(matchOddsBetsDetailsTO);
            matchOddsDetail.setBets(matchOddsBetsDetails);
        }
        matchOddsDetails.add(matchOddsDetail);
        otherMarketsWrapper.setOtherMarkets(matchOddsDetails);
        return otherMarketsWrapper;
    }

    private FancyBetsWrapperTO getFancyDetails(ResultSet executeQuery) throws SQLException {
        FancyBetsWrapperTO fancyBetsWrapper = new FancyBetsWrapperTO();
        List<MatchOddsBetsDetailsTO> matchOddsBetsDetails = new ArrayList<>();
        List<MatchOddsDetailsTO> matchOddsDetails = new ArrayList<>();
        MatchOddsDetailsTO matchOddsDetail = new MatchOddsDetailsTO();
        while (executeQuery.next()) {
            Integer sportId = executeQuery.getInt(ColumnLabelConstants.SPORT_ID);
            if (matchOddsDetail.getSportId() != sportId && matchOddsDetail.getSportId() != null) {
                matchOddsDetails.add(matchOddsDetail);
                matchOddsDetail = new MatchOddsDetailsTO();
                matchOddsBetsDetails = new ArrayList<>();
            }
            matchOddsDetail.setSportId(executeQuery.getInt(ColumnLabelConstants.SPORT_ID));
            matchOddsDetail.setSportName(executeQuery.getString(ColumnLabelConstants.SPORT_NAME));
            MatchOddsBetsDetailsTO matchOddsBetsDetailsTO = new MatchOddsBetsDetailsTO();
            matchOddsBetsDetailsTO.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            matchOddsBetsDetailsTO.setEventName(executeQuery.getString(ColumnLabelConstants.EVENT_NAME));
            matchOddsBetsDetailsTO.setMarketDate(executeQuery.getString(ColumnLabelConstants.BET_PLACED).substring(0, 10));
            matchOddsBetsDetailsTO.setMarketName(executeQuery.getString(ColumnLabelConstants.MARKET_NAME));
            matchOddsBetsDetailsTO.setOdds(executeQuery.getString(ColumnLabelConstants.ODDS));
            matchOddsBetsDetailsTO.setOutcomeName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            matchOddsBetsDetailsTO.setUserId(executeQuery.getInt(ColumnLabelConstants.USER_ID));
            matchOddsBetsDetailsTO.setUserName(executeQuery.getString(ColumnLabelConstants.USER_NAME));
            matchOddsBetsDetailsTO.setStakeAmount(executeQuery.getString(ColumnLabelConstants.STAKE_AMOUNT));
            matchOddsBetsDetails.add(matchOddsBetsDetailsTO);
            matchOddsDetail.setBets(matchOddsBetsDetails);
        }
        matchOddsDetails.add(matchOddsDetail);
        fancyBetsWrapper.setFancyBets(matchOddsDetails);
        return fancyBetsWrapper;
    }


    private MatchOddsWrapperTO getMatchOddsDetails(ResultSet executeQuery) throws SQLException {
        MatchOddsWrapperTO matchOddsWrapper = new MatchOddsWrapperTO();
        List<MatchOddsBetsDetailsTO> matchOddsBetsDetails = new ArrayList<>();
        List<MatchOddsDetailsTO> matchOddsDetails = new ArrayList<>();
        MatchOddsDetailsTO matchOddsDetail = new MatchOddsDetailsTO();
        while (executeQuery.next()) {
            Integer sportId = executeQuery.getInt(ColumnLabelConstants.SPORT_ID);
            if (matchOddsDetail.getSportId() != sportId && matchOddsDetail.getSportId() != null) {
                matchOddsDetails.add(matchOddsDetail);
                matchOddsDetail = new MatchOddsDetailsTO();
                matchOddsBetsDetails = new ArrayList<>();
            }
            matchOddsDetail.setSportId(executeQuery.getInt(ColumnLabelConstants.SPORT_ID));
            matchOddsDetail.setSportName(executeQuery.getString(ColumnLabelConstants.SPORT_NAME));
            MatchOddsBetsDetailsTO matchOddsBetsDetailsTO = new MatchOddsBetsDetailsTO();
            matchOddsBetsDetailsTO.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
            matchOddsBetsDetailsTO.setEventName(executeQuery.getString(ColumnLabelConstants.EVENT_NAME));
            matchOddsBetsDetailsTO.setMarketDate(executeQuery.getString(ColumnLabelConstants.BET_PLACED).substring(0, 10));
            matchOddsBetsDetailsTO.setMarketName(executeQuery.getString(ColumnLabelConstants.MARKET_NAME));
            matchOddsBetsDetailsTO.setOdds(executeQuery.getString(ColumnLabelConstants.ODDS));
            matchOddsBetsDetailsTO.setOutcomeName(executeQuery.getString(ColumnLabelConstants.OUTCOME_NAME));
            matchOddsBetsDetailsTO.setSelectionName(executeQuery.getString(ColumnLabelConstants.SELECTION_NAME));
            matchOddsBetsDetailsTO.setUserId(executeQuery.getInt(ColumnLabelConstants.USER_ID));
            matchOddsBetsDetailsTO.setUserName(executeQuery.getString(ColumnLabelConstants.USER_NAME));
            matchOddsBetsDetailsTO.setStakeAmount(executeQuery.getString(ColumnLabelConstants.STAKE_AMOUNT));
            String oddType = executeQuery.getString(ColumnLabelConstants.BET_ODD_TYPE);
            if (oddType.equalsIgnoreCase("b")) {
                matchOddsBetsDetailsTO.setOddType("Back");
            } else {
                matchOddsBetsDetailsTO.setOddType("Lay");
            }
            matchOddsBetsDetails.add(matchOddsBetsDetailsTO);
            matchOddsDetail.setBets(matchOddsBetsDetails);
        }
        matchOddsDetails.add(matchOddsDetail);
        matchOddsWrapper.setMatchOdds(matchOddsDetails);
        return matchOddsWrapper;
    }

    private List<PlayersDetailsTO> getExposureDetails(ResultSet executeQuery) throws SQLException {
        List<PlayersDetailsTO> playersDetailsList = new ArrayList<>();
        while (executeQuery.next()) {
            PlayersDetailsTO playersDetails = new PlayersDetailsTO();
            playersDetails.setUserName(executeQuery.getString(ColumnLabelConstants.USER_NAME));
            playersDetails.setUserId(executeQuery.getInt(ColumnLabelConstants.USER_ID));
            playersDetails.setUserAmount(executeQuery.getString(ColumnLabelConstants.USER_MATCHED_AMOUNT));
            playersDetails.setUserExposure(executeQuery.getString(ColumnLabelConstants.USER_EXPOSURE));
            playersDetailsList.add(playersDetails);
        }
        return playersDetailsList;
    }
}
