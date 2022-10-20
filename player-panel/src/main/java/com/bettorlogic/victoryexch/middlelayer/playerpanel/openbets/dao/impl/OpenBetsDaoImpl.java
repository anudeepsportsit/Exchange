package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.CancelInstructionTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.PlaceInstructionReport;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.ExecutionReportStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.service.BetPlacement;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dao.OpenBetsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OpenBetsDaoImpl implements OpenBetsDao {

    private static final String PROC_GET_OPEN_BET_DETAILS = "select * from get_open_bet_details(?) order by provider_id,event_id,is_matched desc,bet_placed";
    private static final String PROC_CANCEL_OPEN_BETS = "select * from cancel_open_bets(?,?)";
    private static final String PROC_UPDATE_OPEN_BET_DETAILS = "select * from update_open_bet_details(?,?,?,?,?,?,?,?,?)";
    private static final String PROC_GET_MATKET_ID = "select clmarketid,betfair_betid from bet where id=?";
    private static final String QUERY_GET_SESSION_TOKEN = "select name from betfairodds.betfair_key";
    private static final String UPDATE_BET_DETAILS = "update bet set betfair_betid=?,betfair_placed_date=?,ismatched=?,odds=?,returns=? where id=?";
    private static final String CANCEL_BET = "update bet set betstatus='VOID',betstatusid=4,usercancel='No',bet_settlement_id=?,betsettled=? where id=?";
    private static final String PROC_UPDATE_USER_BALANCE = "select * from update_betfair_bet_settlement_userbal_transactions(?)";
    private static final String RS_UPDATE_BALANCE = "update_betfair_bet_settlement_userbal_transactions";

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenBetsDaoImpl.class);
    private static final String PROC_GET_USER_ID = "select id from bmeusers where user_login_token='";
    private static final String QUERY_IS_MATCHED = "select ismatched from bet where id='";
    private static final String GET_BALANCE = "select * from validate_balance_availability(?,?,?,?,?,?)";
    private static final String PROC_GET_AGENT_OPEN_BET_DETAILS = "select * from get_agent_open_bet_details(?) order by provider_id,is_matched desc,event_id,bet_placed";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OpenBetsEventWrapperTO> getOpenBetDetails(String userToken) {
        LOGGER.info("Fetch data for 'getOpenBetDetails' from the stored procedure: " + PROC_GET_OPEN_BET_DETAILS);
        List<OpenBetsEventWrapperTO> openBetDetailsList =
                jdbcTemplate.execute(con -> {
                            CallableStatement cs = con.prepareCall(PROC_GET_OPEN_BET_DETAILS,
                                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            cs.setString(1, userToken);
                            return cs;
                        }
                        , (CallableStatement cs) -> this.populateOpenBetDetails(cs.executeQuery()));
        LOGGER.info("Data for 'getOpenBetDetails' has been fetched successfully: " + openBetDetailsList);
        return openBetDetailsList;
    }

    @Override
    public UpdateOpenBetsOutput updateOpenBetDetails(String userToken, OpenBetDetailsTO betDetails, double stake, PlaceInstructionReport placeInstructionReport) {
        LOGGER.info("Fetch data for 'updateOpenBetDetails' from the stored procedure: " + PROC_UPDATE_OPEN_BET_DETAILS);
        UpdateOpenBetsOutput updateOpenBetsOutput = jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_UPDATE_OPEN_BET_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setDouble(2, new Double(betDetails.getStake()));
            cs.setDouble(3, new Double(betDetails.getOdds()));
            cs.setDouble(4, new Double(betDetails.getReturns()));
            cs.setDouble(5, stake);
            cs.setInt(6, betDetails.getBetId());
            cs.setLong(7, new Long(placeInstructionReport.getBetId()));
            cs.setString(8, String.valueOf(placeInstructionReport.getPlacedDate()));
            cs.setInt(9, placeInstructionReport.getOrderStatus() == OrderStatus.EXECUTABLE ? 0 : 1);
            return cs;
        }, (CallableStatement cs) -> this.populateUpdateBetDetailsOutput(cs.executeQuery()));
        LOGGER.info("The given bets have been updated successfully");
        return updateOpenBetsOutput;
    }

    @Override
    public CancelOpenBetsOutput cancelOpenBets(String userToken, Integer betId) {
        String report = jdbcTemplate.execute(con -> {
                    CallableStatement cs = con.prepareCall(PROC_GET_MATKET_ID,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, betId);
                    return cs;
                }
                , (CallableStatement cs) -> this.cancelBetsApi(cs.executeQuery()));

        LOGGER.info("Fetch data for 'cancelOpenBets' from the stored procedure: " + PROC_CANCEL_OPEN_BETS);
        CancelOpenBetsOutput openBetDetails =
                jdbcTemplate.execute(con -> {
                            CallableStatement cs = con.prepareCall(PROC_CANCEL_OPEN_BETS,
                                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            cs.setString(1, userToken);
                            cs.setInt(2, betId);
                            return cs;
                        }
                        , (CallableStatement cs) -> this.populateCancelBetDetails(cs.executeQuery()));
        LOGGER.info("Data for 'cancelOpenBets' has been fetched successfully: " + openBetDetails);
        return openBetDetails;
    }

    @Override
    public void updateOpenBet(PlaceInstructionReport instructionReport, String odds, Integer betId, String returns) {
        jdbcTemplate.update(UPDATE_BET_DETAILS, new BigDecimal(instructionReport.getBetId()),String.valueOf(instructionReport.getPlacedDate()),
                instructionReport.getOrderStatus() == OrderStatus.EXECUTABLE ? 0 : 1,  Double.valueOf(odds),Double.valueOf(returns),betId);
    }

    @Override
    public void cancelBets(Integer betId, String currentUtc, String betfairBetId) {
        jdbcTemplate.update(CANCEL_BET, Long.parseLong(betfairBetId), Timestamp.valueOf(currentUtc),betId);
    }

    @Override
    public String updateUserBalance(String currentUtc){
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_USER_BALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setTimestamp(1, Timestamp.valueOf(currentUtc));
            return cs;
        }, (CallableStatement cs) -> {
            String  betSlipId = null;
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                betSlipId = rs.getString(RS_UPDATE_BALANCE);
            }
            return betSlipId;
        });
    }

    @Override
    public Integer getIsMatched(Integer betId) {
        return jdbcTemplate.queryForObject(QUERY_IS_MATCHED +  betId + "'", Integer.class);
    }

    @Override
    public int getUserId(String userToken) {
        return jdbcTemplate.queryForObject(PROC_GET_USER_ID +  userToken + "'", Integer.class);
    }

    @Override
    public List<OpenBetsEventWrapperTO> getAgentOpenBetDetails(String marketName) {
        LOGGER.info("Fetch data for 'getOpenBetDetails' from the stored procedure: " + PROC_GET_OPEN_BET_DETAILS);
        List<OpenBetsEventWrapperTO> openBetDetailsList =
                jdbcTemplate.execute(con -> {
                            CallableStatement cs = con.prepareCall(PROC_GET_AGENT_OPEN_BET_DETAILS,
                                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            cs.setString(1, marketName);
                            return cs;
                        }
                        , (CallableStatement cs) -> this.populateOpenBetDetails(cs.executeQuery()));
        LOGGER.info("Data for 'getOpenBetDetails' has been fetched successfully: " + openBetDetailsList);
        return openBetDetailsList;
    }

    @Override
    public Double getUserExposureLimit(int userId) {
        try {
            String SQL = "select exposurelimit from usersettings where bmeuserid=";
            return jdbcTemplate.queryForObject(SQL + userId, Double.class);
        }catch (Exception e){
            return 1.0;
        }
    }

    @Override
    public Double getBalance(OpenBetDetailsTO openBetDetails, int userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_BALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            cs.setString(2, openBetDetails.getOddType());
            cs.setBigDecimal(3, new BigDecimal(openBetDetails.getStake()));
            cs.setBigDecimal(4, new BigDecimal(openBetDetails.getReturns()));
            cs.setInt(5, openBetDetails.getEventId());
            cs.setInt(6, openBetDetails.getOutcomeId());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            Double id = null;
            while (rs.next()) {
                id = rs.getDouble("validate_balance_availability");
            }
            return id;
        });
    }


    private String cancelBetsApi(ResultSet rs) throws SQLException {
        List<CancelInstructionTO> instructionList = new ArrayList<>();
        BetPlacement placement = new BetPlacement();
        ExecutionReportStatus reportStatus = null;
        while (rs.next()) {
            CancelInstructionTO cancelInstructionTO = new CancelInstructionTO();
            String marketId = rs.getString(ColumnLabelConstants.CLMARKET_ID);
            cancelInstructionTO.setBetId(String.valueOf(rs.getLong(ColumnLabelConstants.BET_FAIR_ID)));
            instructionList.add(cancelInstructionTO);
            reportStatus = placement.cancelBets(marketId, instructionList, jdbcTemplate.queryForObject(QUERY_GET_SESSION_TOKEN, String.class));
        }
        return reportStatus.toString();
    }

    private CancelOpenBetsOutput populateCancelBetDetails(ResultSet rs) throws SQLException {
        CancelOpenBetsOutput cancelOpenBetsOutput = new CancelOpenBetsOutput();
        while (rs.next()) {

            cancelOpenBetsOutput.setBetCancelled(rs.getBoolean(ColumnLabelConstants.IS_BET_CANCELLED));
            if (!cancelOpenBetsOutput.isBetCancelled()) {
                cancelOpenBetsOutput.setInOpenStatus(rs.getBoolean(ColumnLabelConstants.IS_IN_OPEN_STATUS));
                cancelOpenBetsOutput.setPlayerHasBet(rs.getBoolean(ColumnLabelConstants.PLAYER_HAS_BET));
                cancelOpenBetsOutput.setUnmatchedBet(rs.getBoolean(ColumnLabelConstants.IS_UNMATCHED_BET));
                cancelOpenBetsOutput.setValidPlayer(rs.getBoolean(ColumnLabelConstants.IS_VALID_PLAYER));
            }
        }
        return cancelOpenBetsOutput;
    }

    private UpdateOpenBetsOutput populateUpdateBetDetailsOutput(ResultSet rs) throws SQLException {
        UpdateOpenBetsOutput updateOpenBetsOutput = new UpdateOpenBetsOutput();
        while (rs.next()) {
            updateOpenBetsOutput.setBetUpdated(rs.getBoolean(ColumnLabelConstants.IS_BET_UPDATED));
            updateOpenBetsOutput.setValidPlayer(rs.getBoolean(ColumnLabelConstants.IS_VALID_PLAYER));
            updateOpenBetsOutput.setPlayerHasBet(rs.getBoolean(ColumnLabelConstants.PLAYER_HAS_BET));
            updateOpenBetsOutput.setHasSufficientBalance(rs.getBoolean(ColumnLabelConstants.HAS_SUFFICIENT_BALANCE));
            updateOpenBetsOutput.setMarketSuspended(rs.getBoolean(ColumnLabelConstants.MARKET_SUSPENDED));
            updateOpenBetsOutput.setHasExceededExposureLimit(rs.getBoolean(ColumnLabelConstants.HAS_EXCEEDED_EXPOSURE));
            updateOpenBetsOutput.setOpenBet(rs.getBoolean(ColumnLabelConstants.IS_OPEN_BET));
        }

        return updateOpenBetsOutput;
    }

    private List<OpenBetsEventWrapperTO> populateOpenBetDetails(ResultSet rs) throws SQLException {
        List<OpenBetsEventWrapperTO> openBetsWrapperList = new ArrayList<>();
        int lastEventId = 0;
        int lastMatched = 5;
        int provider = 5;
        OpenBetsEventWrapperTO openBetsEventWrapper = new OpenBetsEventWrapperTO();
        BetDetailsSubWrapperTO betMatchedDetailsSubWrapper = new BetDetailsSubWrapperTO();
        BetDetailsSubWrapperTO betUnMatchedDetailsSubWrapper = new BetDetailsSubWrapperTO();
        while (rs.next()) {
            int eventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
            int providerId = rs.getInt(ColumnLabelConstants.PROVIDER_ID);
            if (eventId == lastEventId && provider == providerId) {
                assert openBetsEventWrapper != null;
                openBetsEventWrapper.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
                if(providerId == 3) {
                    openBetsEventWrapper.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME).concat("-- Fancy"));
                }else{
                    openBetsEventWrapper.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
                }
                openBetsEventWrapper.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
                openBetsEventWrapper.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
                    lastMatched = rs.getInt(ColumnLabelConstants.IS_MATCHED);
                    if (lastMatched == SportsBookConstants.MATCHED) {
                        betMatchedDetailsSubWrapper.getBetDetailsList().add(this.extractOpenBetDetails(rs));
                        openBetsEventWrapper.setMatchedBets(betMatchedDetailsSubWrapper);
                    } else if (lastMatched == SportsBookConstants.UN_MATCHED) {
                        betUnMatchedDetailsSubWrapper.getBetDetailsList().add(this.extractOpenBetDetails(rs));
                        openBetsEventWrapper.setUnMatchedBets(betUnMatchedDetailsSubWrapper);
                    }
            } else {
                lastEventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                provider = rs.getInt(ColumnLabelConstants.PROVIDER_ID);
                rs.previous();
                openBetsEventWrapper = new OpenBetsEventWrapperTO();
                openBetsWrapperList.add(openBetsEventWrapper);
                betMatchedDetailsSubWrapper = new BetDetailsSubWrapperTO();
                betUnMatchedDetailsSubWrapper = new BetDetailsSubWrapperTO();
            }
        }
        return openBetsWrapperList;
    }

    private OpenBetDetailsTO extractOpenBetDetails(ResultSet rs) throws SQLException {
        OpenBetDetailsTO openBetDetails = new OpenBetDetailsTO();
        openBetDetails.setBetId(rs.getInt(ColumnLabelConstants.BET_ID));
        openBetDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        openBetDetails.setOddType(rs.getString(ColumnLabelConstants.ODD_TYPE));
        openBetDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        openBetDetails.setBetPlacedTime(rs.getString(ColumnLabelConstants.BET_PLACED));
        openBetDetails.setProfitLiability(rs.getString(ColumnLabelConstants.PROFIT_LIABILITY));
        openBetDetails.setIsFancy(rs.getInt(ColumnLabelConstants.IS_FANCY_FLAG));
        openBetDetails.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
        openBetDetails.setClientMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
        openBetDetails.setSelectionId(rs.getString(ColumnLabelConstants.SELECTION_ID));
        openBetDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        openBetDetails.setOddsManualChangeFlag(rs.getInt(ColumnLabelConstants.IS_MANUAL));
        openBetDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        openBetDetails.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
        openBetDetails.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
        openBetDetails.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_ID));
        openBetDetails.setBetfairBetId(String.valueOf(rs.getBigDecimal(ColumnLabelConstants.BET_FAIR_ID)));
        openBetDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        openBetDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        openBetDetails.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAYTEAM_ID));
        openBetDetails.setAwayTeam(rs.getString(ColumnLabelConstants.AWAYTEAM));
        openBetDetails.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOMETEAM_ID));
        openBetDetails.setHomeTeam(rs.getString(ColumnLabelConstants.HOMETEAM));
        openBetDetails.setSubMarket(rs.getString(ColumnLabelConstants.SUBMARKETNAME));
        this.extractOpenBetDetails(rs, openBetDetails);
        return openBetDetails;
    }

    private void extractOpenBetDetails(ResultSet rs, OpenBetDetailsTO openBetDetails) throws SQLException {
        openBetDetails.setOdds(rs.getBigDecimal(ColumnLabelConstants.ODDS) != null
                ? rs.getBigDecimal(ColumnLabelConstants.ODDS).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setStake(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null
                ? rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setExposure(rs.getBigDecimal(ColumnLabelConstants.EXPOSURE_VALUE) != null
                ? rs.getBigDecimal(ColumnLabelConstants.EXPOSURE_VALUE).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setReturns(rs.getBigDecimal(ColumnLabelConstants.RETURNS_VALUE) != null
                ? rs.getBigDecimal(ColumnLabelConstants.RETURNS_VALUE).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setMinStake(rs.getBigDecimal(ColumnLabelConstants.MIN_STAKE) != null
                ? rs.getBigDecimal(ColumnLabelConstants.MIN_STAKE).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setMaxStake(rs.getBigDecimal(ColumnLabelConstants.MAX_STAKE) != null
                ? rs.getBigDecimal(ColumnLabelConstants.MAX_STAKE).toString() : BigDecimal.ZERO.toString());
        openBetDetails.setOriginalOdds(rs.getBigDecimal(ColumnLabelConstants.ORIGINAL_ODDS) != null
                ? rs.getBigDecimal(ColumnLabelConstants.ORIGINAL_ODDS).toString() : BigDecimal.ZERO.toString());
    }

}