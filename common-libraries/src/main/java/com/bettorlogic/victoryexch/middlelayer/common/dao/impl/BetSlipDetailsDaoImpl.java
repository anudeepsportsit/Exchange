package com.bettorlogic.victoryexch.middlelayer.common.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dao.BetSlipDetailsDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class BetSlipDetailsDaoImpl implements BetSlipDetailsDao {

    private static final String GET_BET_SLIP_DETAILS = "select * from get_betslip_details(?, ?, ?, ?, ?)";
    private static final String QUERY_SAVE_BET_DETAILS =
            "insert into bet (eventid, odd_dictionary_id, odds, oddtype, stake, returns, betslipid, " +
                    "ismanual, betstatusid, betstatus, originalodds, hometeamprofitloss, awayteamprofitloss) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String PROC_SAVE_BET_SETTLEMENT_DETAILS = "select * from save_bet_settlement()";
    private static final String PROC_SAVE_BET_SLIP_DETAILS = "select * from save_betslip_details(?, ?, ?, ?, ?, ?)";
    private static final String PROC_VALIDATE_BET_SLIP_DETAILS =
            "select * from validate_betslip_details_v1(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String PROC_UPDATE_BET_DETAILS = "select * from update_bet_details()";
    private static final String PROC_UPDATE_USER_BALANCE_DETAILS = "select * from update_user_balance(?,?,?,?,?,?,?)";
    //private static final String PROC_SAVE_BET_DETAILS = "select * from SAVE_BET_DETAILS_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String PROC_SAVE_BET_DETAILS = "select * from SAVE_BET_DETAILS_V3(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String QUERY_GET_SESSION_TOKEN = "select name from betfairodds.betfair_key";
    private static final String QUERY_GET_BETS_COUNT = "select count(*) from bet where betslipid=?";
    private static final String QUERY_DELETE_BETSLIP = "delete from betslip where id=?";

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BetDetailsFetchTO getBetSlipDetails(BetDetailsFetchTO betDetailsFetch) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_BET_SLIP_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, betDetailsFetch.getEventId());
            cs.setInt(2, betDetailsFetch.getMarketId());
            cs.setInt(3, betDetailsFetch.getOutcomeId() != null ? betDetailsFetch.getOutcomeId() : 0);
            cs.setDouble(4, betDetailsFetch.getOdds());
            cs.setString(5, betDetailsFetch.getOddType().toUpperCase());
            return cs;
        }, (CallableStatement cs) -> extractBetSlipDetailsFromResultSet(cs.executeQuery()));
    }

    @Override
    public int[] saveBetDetails(List<BetDetailsTO> betDetailsList, Integer betSlipId) {
        int[] saveBets = jdbcTemplate.batchUpdate(QUERY_SAVE_BET_DETAILS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                BetDetailsTO betDetails = betDetailsList.get(i);
                preparedStatement.setInt(1, betDetails.getEventId());
                preparedStatement.setInt(2, betDetails.getOutcomeId() != null ? betDetails.getOutcomeId() : 0);
                preparedStatement.setBigDecimal(3, betDetails.getOdds());
                preparedStatement.setString(4, betDetails.getOddType());
                preparedStatement.setDouble(5, betDetails.getStakeAmount());
                preparedStatement.setDouble(6, betDetails.getReturns());
                preparedStatement.setInt(7, betSlipId);
                preparedStatement.setInt(8, betDetails.getOddsManualChangeFlag());
                preparedStatement.setInt(9, SportsBookConstants.BET_STATUS_PLACED_ID);
                preparedStatement.setString(10, SportsBookConstants.BetSlipConstants.BET_PLACED);
                preparedStatement.setBigDecimal(11, betDetails.getOriginalOdds());
                preparedStatement.setBigDecimal(12, betDetails.getHomeTeamProfitLoss());
                preparedStatement.setBigDecimal(13, betDetails.getAwayTeamProfitLoss());
            }

            @Override
            public int getBatchSize() {
                return betDetailsList.size();
            }
        });
        jdbcTemplate.execute(PROC_UPDATE_BET_DETAILS);
        jdbcTemplate.execute(PROC_SAVE_BET_SETTLEMENT_DETAILS);
        return saveBets;
    }

    @Override
    public Integer saveBet(String userToken, BetDetailsTO betDetails, Integer betSlipId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_BET_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, betDetails.getEventId());
            cs.setInt(2, betDetails.getOutcomeId() != null ? betDetails.getOutcomeId() : 0);
            cs.setBigDecimal(3, betDetails.getOdds());
            cs.setString(4, betDetails.getOddType());
            cs.setBigDecimal(5, BigDecimal.valueOf(betDetails.getStakeAmount()));
            cs.setBigDecimal(6, BigDecimal.valueOf(betDetails.getReturns()));
            if (betDetails.getOddType().equals(SportsBookConstants.BetSlipConstants.ODD_TYPE)) {
                cs.setBigDecimal(7, BigDecimal.valueOf(betDetails.getStakeAmount()));
            } else {
                cs.setBigDecimal(7, BigDecimal.valueOf(betDetails.getReturns() - betDetails.getStakeAmount()));
            }
            cs.setInt(8, betSlipId);
            cs.setString(9, String.valueOf(betDetails.getOddsManualChangeFlag()));
            cs.setInt(10, SportsBookConstants.BET_STATUS_PLACED_ID);
            cs.setString(11, SportsBookConstants.BetSlipConstants.BET_PLACED);
            cs.setLong(12, betDetails.getClientBetId() == null ? 0 : betDetails.getClientBetId());
            cs.setString(13, betDetails.getClientBetPlacedDate());
            cs.setBigDecimal(14, BigDecimal.valueOf(betDetails.getClientStakeAmount() == null ? 0 : betDetails.getClientStakeAmount()));
            cs.setBigDecimal(15, betDetails.getOriginalOdds());
            cs.setInt(16, betDetails.getIsMatched());
            cs.setInt(17, betDetails.getIsLive());
            cs.setString(18, betDetails.getClientMarketId() == null ? "0" : betDetails.getClientMarketId());
            cs.setString(19, betDetails.getSelectionId() == null ? "0" : betDetails.getSelectionId());
            cs.setInt(20, betDetails.getMarketId() == null ? 0 : betDetails.getMarketId());
            cs.setString(21, userToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            Integer result = 0;
            while (rs.next()) {
                result = rs.getInt(ColumnLabelConstants.SAVE_BET_DETAILS);
            }
            return result;
        });
    }

    @Override
    public Integer saveBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails) {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        String currentUtc = odt.format((DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_BET_SLIP_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, betSlipWrapperDetails.getUserToken());
            cs.setDouble(2, betSlipWrapperDetails.getTotalStake());
            cs.setDouble(3, betSlipWrapperDetails.getTotalReturns());
            cs.setInt(4, betSlipWrapperDetails.getOddsChangeAcceptanceFlag());
            cs.setInt(5, betSlipWrapperDetails.getBetDetailsList().size());
            cs.setTimestamp(6, Timestamp.valueOf(currentUtc));
            return cs;
        }, (CallableStatement cs) -> {
            Integer betSlipId = null;
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                betSlipId = rs.getInt(ColumnLabelConstants.BET_SLIP_ID);
            }
            return betSlipId;
        });
    }

    @Override
    public BetSlipValidationDetailsTO validateBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails,
                                                             BetDetailsTO betDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_VALIDATE_BET_SLIP_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, betSlipWrapperDetails.getUserToken());
            cs.setInt(2, betDetails.getEventId());
            cs.setInt(3, betDetails.getMarketId());
            cs.setInt(4, betDetails.getOutcomeId() != null ? betDetails.getOutcomeId() : 0);
            cs.setInt(5, betSlipWrapperDetails.getOddsChangeAcceptanceFlag());
            cs.setBigDecimal(6, betDetails.getOdds());
            cs.setString(7, betDetails.getOddType().toUpperCase());
            cs.setInt(8, betDetails.getOddsManualChangeFlag());
            cs.setDouble(9, betSlipWrapperDetails.getTotalStake());
            cs.setDouble(10, betSlipWrapperDetails.getTotalReturns());
            return cs;
        }, (CallableStatement cs) -> this.extractBetSlipValidationResult(cs.executeQuery()));
    }

    private BetDetailsFetchTO extractBetSlipDetailsFromResultSet(ResultSet rs) throws SQLException {
        BetDetailsFetchTO betDetailsFetch = new BetDetailsFetchTO();
        while (rs.next()) {
            betDetailsFetch.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
            betDetailsFetch.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
            betDetailsFetch.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
            betDetailsFetch.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            betDetailsFetch.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
            betDetailsFetch.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
            betDetailsFetch.setOdds(rs.getDouble(ColumnLabelConstants.ODDS));
            betDetailsFetch.setIsFancy(rs.getInt(ColumnLabelConstants.IS_FANCY_FLAG));
            betDetailsFetch.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
            betDetailsFetch.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
            betDetailsFetch.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
            betDetailsFetch.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
            betDetailsFetch.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
            betDetailsFetch.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
            betDetailsFetch.setClientMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
            betDetailsFetch.setSelectionId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
            betDetailsFetch.setSubMarket(rs.getString(ColumnLabelConstants.SUB_MARKET));
        }
        betDetailsFetch.setEventExists(betDetailsFetch.getEventId() != null);
        betDetailsFetch.setMarketExists(betDetailsFetch.getEventId() != null && betDetailsFetch.getMarketId() != null);
        return betDetailsFetch;
    }

    @Override
    public void updateBetSettlementDetails() {
        jdbcTemplate.execute(PROC_UPDATE_BET_DETAILS);
        jdbcTemplate.execute(PROC_SAVE_BET_SETTLEMENT_DETAILS);
    }

    @Override
    public String getSessionKey() {
        return jdbcTemplate.queryForObject(QUERY_GET_SESSION_TOKEN, String.class);
    }

    @Override
    public Integer getBetsCount(int betSlipId) {
        return jdbcTemplate.queryForObject(QUERY_GET_BETS_COUNT, Integer.class, betSlipId);
    }

    @Override
    public void removeBetSlip(int betSlipId) {
        jdbcTemplate.update(QUERY_DELETE_BETSLIP, betSlipId);
    }

    @Override
    public Integer getBetDelay(Integer marketId) {
        try {
            return jdbcTemplate.queryForObject("select distinct betdelay from fancymarkets where fancymarketid="+marketId+"", Integer.class);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public Integer getBetDelayMatchOdds(Integer eventId) {
        try {
            return jdbcTemplate.queryForObject("select betdelay from bmevents where eventid="+eventId+"", Integer.class);
        }catch (Exception e){
            return 0;
        }
    }


    @Override
    public UserBalanceDetailsTO updateUserBalanceDetails(UserBalanceDetailsTO userBalanceDetails, Double totalExposure) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_USER_BALANCE_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userBalanceDetails.getUserToken());
            cs.setDouble(2, userBalanceDetails.getTransactionAmount());
            cs.setString(3, userBalanceDetails.getFromTo());
            cs.setString(4, userBalanceDetails.getTransactionType());
            cs.setString(5, userBalanceDetails.getTransactionRemarks());
            cs.setInt(6, userBalanceDetails.getTransactionTypeId());
            cs.setDouble(7, new BigDecimal(totalExposure).setScale(2, RoundingMode.CEILING).doubleValue());
            return cs;
        }, (CallableStatement cs) -> this.extractResultOfUserBalanceUpdate(cs.executeQuery()));
    }

    private UserBalanceDetailsTO extractResultOfUserBalanceUpdate(ResultSet rs) throws SQLException {
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        while (rs.next()) {
            userBalanceDetails.setIsBetPlaced(rs.getBoolean(ColumnLabelConstants.STATUS));
            if (!userBalanceDetails.getIsBetPlaced()) {
                userBalanceDetails.setRemarks(rs.getString(ColumnLabelConstants.REMARKS));
            } else {
                userBalanceDetails.setRemainingBalance(rs.getDouble(ColumnLabelConstants.USER_BALANCE));
            }
        }
        return userBalanceDetails;
    }

    private BetSlipValidationDetailsTO extractBetSlipValidationResult(ResultSet resultSet) throws SQLException {
        BetSlipValidationDetailsTO betSlipValidationDetails = new BetSlipValidationDetailsTO();
        while (resultSet.next()) {
            betSlipValidationDetails.setValidBet(resultSet.getBoolean(ColumnLabelConstants.BET_SLIP_PLACED_FLAG));
            if (!betSlipValidationDetails.getValidBet()) {
                betSlipValidationDetails.setMarketSuspended(resultSet.getBoolean(ColumnLabelConstants.IS_MARKET_SUSPENDED_FLAG));
                betSlipValidationDetails.setOddsChangeFlag(resultSet.getBoolean(ColumnLabelConstants.OODS_CHANGED_FLAG));
                betSlipValidationDetails.setIsValidUser(resultSet.getBoolean(ColumnLabelConstants.IS_VALID_USER));
                betSlipValidationDetails.setIsPlayer(resultSet.getBoolean(ColumnLabelConstants.IS_PLAYER));
                betSlipValidationDetails.setIsActive(resultSet.getBoolean(ColumnLabelConstants.IS_ACTIVE));
                betSlipValidationDetails.setHasInsufficientBalance(resultSet.getBoolean(ColumnLabelConstants.HAS_SUFFICIENT_BALANCE));
                betSlipValidationDetails.setIsValidMarket(resultSet.getBoolean(ColumnLabelConstants.IS_VALID_MARKET));
                betSlipValidationDetails.setIsValidEvent(resultSet.getBoolean(ColumnLabelConstants.IS_VALID_EVENT));
                betSlipValidationDetails.setIsValidOutcome(resultSet.getBoolean(ColumnLabelConstants.IS_VALID_OUTCOME));
                betSlipValidationDetails.setHasExceededExposureLimit(resultSet.getBoolean(ColumnLabelConstants.HAS_EXCEEDED_EXPOSURE));
            }
        }
        return betSlipValidationDetails;
    }
}