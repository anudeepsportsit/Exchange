package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dao.BetListDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BetListDaoImpl implements BetListDao {

    private static final String PROC_GET_BET_LIST = "select distinct * from get_betlist(?,?,?,?,?,?)";
    private static final String PROC_GET_BETS_ALL_SPORTS = "select distinct * from get_betlist_all_matched(?,?,?,?,?)";
    private static final String PROC_GET_BETS_ALL_SPORTS_SETTLED = "select distinct * from get_betlist_settled(?,?,?,?,?)";
    private static final String PROC_GET_BETS_ALL_SPORTS_CANCELLED = "select distinct * from get_betlist_cancelled(?,?,?,?,?)";
    private static final String PROC_GET_BETS_ALL_SPORTS_VOIDED = "select distinct * from get_betlist_voided(?,?,?,?,?)";
    private static final String PROC_GET_BETS_SPORTS = "select distinct * from get_betlist_sportwise_matched(?,?,?,?,?,?)";
    private static final String PROC_GET_BETS_SPORTS_SETTLED = "select distinct * from get_betlist_sportwise_settled(?,?,?,?,?,?)";
    private static final String PROC_GET_BETS_SPORTS_CANCELLED = "select distinct * from get_betlist_sportwise_cancelled(?,?,?,?,?,?)";
    private static final String PROC_GET_BETS_SPORTS_VOIDED = "select distinct * from get_betlist_sportwise_voided(?,?,?,?,?,?)";

    private static final String PROC_GET_BET_LIST_DETAILS = "select distinct * from get_betlist_v1(?,?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BetListDetailsWrapperTO extractBetList(BetListLoginDetailsTO loginDetails) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BET_LIST,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setString(1, loginDetails.getUserToken());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setInt(3, loginDetails.getSportId());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setInt(5, loginDetails.getBetStatus());
                    cs.setString(6, loginDetails.getToDate());
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetList(BetListLoginDetailsTO loginDetails,Integer[] userIds) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BET_LIST_DETAILS,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setString(1, loginDetails.getUserToken());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setInt(3, loginDetails.getSportId());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setInt(5, loginDetails.getBetStatus());
                    cs.setString(6, loginDetails.getToDate());
                    cs.setArray(7,  connection.createArrayOf("INTEGER", userIds));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public List<Integer> getUsers(Integer res) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall("select user_id from user_manager_ids(?,4)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, res);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            List<Integer> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getInt("user_id"));
            }
            return result;
        });
    }

    @Override
    public BetListDetailsWrapperTO extractBetListAllSport(BetListLoginDetailsTO loginDetails, Integer[] users) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_ALL_SPORTS,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getBetStatus());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setString(3, loginDetails.getFromDate());
                    cs.setString(4, loginDetails.getToDate());
                    cs.setArray(5,  connection.createArrayOf("INTEGER", users));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListAllSportSettled(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_ALL_SPORTS_SETTLED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getBetStatus());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setString(3, loginDetails.getFromDate());
                    cs.setString(4, loginDetails.getToDate());
                    cs.setArray(5,  connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListAllSportcancelled(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_ALL_SPORTS_CANCELLED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getBetStatus());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setString(3, loginDetails.getFromDate());
                    cs.setString(4, loginDetails.getToDate());
                    cs.setArray(5,  connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListAllSportVoided(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_ALL_SPORTS_VOIDED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getBetStatus());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setString(3, loginDetails.getFromDate());
                    cs.setString(4, loginDetails.getToDate());
                    cs.setArray(5,  connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListSport(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_SPORTS,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getSportId());
                    cs.setInt(2, loginDetails.getBetStatus());
                    cs.setInt(3, loginDetails.getMinutes());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setString(5, loginDetails.getToDate());
                    cs.setArray(6, connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListSportSettled(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_SPORTS_SETTLED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getSportId());
                    cs.setInt(2, loginDetails.getBetStatus());
                    cs.setInt(3, loginDetails.getMinutes());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setString(5, loginDetails.getToDate());
                    cs.setArray(6, connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListSportcancelled(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_SPORTS_CANCELLED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getSportId());
                    cs.setInt(2, loginDetails.getBetStatus());
                    cs.setInt(3, loginDetails.getMinutes());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setString(5, loginDetails.getToDate());
                    cs.setArray(6, connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListSportVoided(BetListLoginDetailsTO loginDetails, Integer[] myArray) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_SPORTS_VOIDED,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getSportId());
                    cs.setInt(2, loginDetails.getBetStatus());
                    cs.setInt(3, loginDetails.getMinutes());
                    cs.setString(4, loginDetails.getFromDate());
                    cs.setString(5, loginDetails.getToDate());
                    cs.setArray(6, connection.createArrayOf("INTEGER", myArray));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    @Override
    public BetListDetailsWrapperTO extractBetListAllSports(BetListLoginDetailsTO loginDetails, String users) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_BETS_ALL_SPORTS,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, loginDetails.getBetStatus());
                    cs.setInt(2, loginDetails.getMinutes());
                    cs.setString(3, loginDetails.getFromDate());
                    cs.setString(4, loginDetails.getToDate());
                    cs.setArray(5, connection.createArrayOf("INTEGER", new String[]{users}));
                    return cs;
                },
                (CallableStatement cs) -> betListDetails(cs.executeQuery()));
    }

    private BetListDetailsWrapperTO betListDetails(ResultSet rs) throws SQLException {
        BetListDetailsWrapperTO betListDetailsWrapper = new BetListDetailsWrapperTO();
        List<BetListDetailsTO> betListDetails = new ArrayList<>();
        while (rs.next()) {
            BetListDetailsTO betList = new BetListDetailsTO();
            betList.setSuperId(rs.getInt(ColumnLabelConstants.SUPER_ID));
            betList.setSuperName(rs.getString(ColumnLabelConstants.SUPER_NAME));
            betList.setMasterId(rs.getInt(ColumnLabelConstants.MASTER_ID));
            betList.setMasterName(rs.getString(ColumnLabelConstants.MASTER_NAME));
            betList.setPlayerId(rs.getInt(ColumnLabelConstants.PLAYER_ID));
            betList.setPlayerName(rs.getString(ColumnLabelConstants.PLAYER_NAME));
            betList.setBetId(rs.getInt(ColumnLabelConstants.BET_ID));
            betList.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED));
            betList.setIpAddress(rs.getString(ColumnLabelConstants.IP_ADDRESS));
            betList.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
            betList.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            betList.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            betList.setSelection(rs.getString(ColumnLabelConstants.SELECTION));
            betList.setOddType(rs.getString(ColumnLabelConstants.ODD_TYPE));
            betList.setOddsRequest(rs.getDouble(ColumnLabelConstants.ODD_REQUEST));
            betList.setStake(rs.getDouble(ColumnLabelConstants.BETLIST_STAKE));
            betList.setLiability(rs.getDouble(ColumnLabelConstants.LIABILTY));
            betList.setProfitLoss(rs.getDouble(ColumnLabelConstants.PROFIT_LOSS));
            betList.setBetStatus(rs.getString(ColumnLabelConstants.BET_STATUS));
            betListDetails.add(betList);

        }
        betListDetailsWrapper.setBetListDetailsList(betListDetails);
        return betListDetailsWrapper;

    }

}