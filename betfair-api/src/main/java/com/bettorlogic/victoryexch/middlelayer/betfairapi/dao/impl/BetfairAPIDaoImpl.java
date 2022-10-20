package com.bettorlogic.victoryexch.middlelayer.betfairapi.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.betfairapi.dao.BetfairAPIDao;
import com.bettorlogic.victoryexch.middlelayer.betfairapi.dto.*;
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
public class BetfairAPIDaoImpl implements BetfairAPIDao {
    private static final String PROC_GET_MARKET_ODDS = "select * from  betfairodds.get_market_odds(?) order by selection_id";
    private static final String PROC_GET_MARKET_ODDS_EX = "select available_to_back_price, available_to_back_size, " +
            "available_to_lay_price, available_to_lay_size from  betfairodds.get_market_odds(?) where selection_id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OddsMyPojo getOdds(String marketId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_MARKET_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, marketId);
            return cs;
        }, (CallableStatement cs) -> this.odds(cs.executeQuery(), marketId));
    }

    private OddsEx getOddsEx(String marketId, Integer selectionId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_MARKET_ODDS_EX,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, marketId);
            cs.setInt(2, selectionId);
            return cs;
        }, (CallableStatement cs) -> this.populateOddsEx(cs.executeQuery()));
    }

    private OddsEx populateOddsEx(ResultSet rs) throws SQLException {
        List<AvailableToBack> availableToBackList = new ArrayList<>();
        List<AvailableToLay> availableToLayList = new ArrayList<>();
        OddsEx oddsEx = new OddsEx();
        while (rs.next()) {
            AvailableToBack availableToBack = new AvailableToBack();
            availableToBack.setPrice(rs.getDouble(ColumnLabelConstants.AVAILABLE_TO_BACK_PRICE));
            availableToBack.setSize(rs.getDouble(ColumnLabelConstants.AVAILABLE_TO_BACK_SIZE));
            availableToBackList.add(availableToBack);

            AvailableToLay availableToLay = new AvailableToLay();
            availableToLay.setPrice(rs.getDouble(ColumnLabelConstants.AVAILABLE_TO_LAY_PRICE));
            availableToLay.setSize(rs.getDouble(ColumnLabelConstants.AVAILABLE_TO_LAY_SIZE));
            availableToLayList.add(availableToLay);
        }
        oddsEx.setAvailableToBack(availableToBackList);
        oddsEx.setAvailableToLay(availableToLayList);
        return oddsEx;
    }

    private OddsMyPojo odds(ResultSet rs, String marketId) throws SQLException {
        OddsMyPojo oddsMyPojo = new OddsMyPojo();
        List<OddsRunners> oddsRunnersList = new ArrayList<>();
        int lastSelectionId = 0;
        OddsRunners oddsRunners = null;


        while (rs.next()) {
            oddsMyPojo.setMarketId(rs.getString(ColumnLabelConstants.MARKET_ID));
            oddsMyPojo.setIsMarketDataDelayed(rs.getBoolean(ColumnLabelConstants.IS_MARKETDATA_DELAYED));
            oddsMyPojo.setStatus(rs.getString(ColumnLabelConstants.STATUS));
            oddsMyPojo.setBetDelay(rs.getInt(ColumnLabelConstants.BET_DELAY));
            oddsMyPojo.setBspReconciled(rs.getBoolean(ColumnLabelConstants.BSP_RECONCILED));
            oddsMyPojo.setComplete(rs.getBoolean(ColumnLabelConstants.COMPLETE));
            oddsMyPojo.setInplay(rs.getBoolean(ColumnLabelConstants.INPLAY));
            oddsMyPojo.setNumberOfWinners(rs.getInt(ColumnLabelConstants.NUMBER_OF_WINNERS));
            oddsMyPojo.setNumberOfRunners(rs.getInt(ColumnLabelConstants.NUMBER_OF_RUNNERS));
            oddsMyPojo.setLastMatchTime(rs.getString(ColumnLabelConstants.LAST_MATCH_TIME));
            oddsMyPojo.setTotalMatched(rs.getDouble(ColumnLabelConstants.BETFAIR_TOTAL_MATCHED));
            oddsMyPojo.setTotalAvailable(rs.getDouble(ColumnLabelConstants.BETFAIR_TOTAL_MATCHED));
            oddsMyPojo.setCrossMatching(rs.getBoolean(ColumnLabelConstants.CROSS_MATCHING));
            oddsMyPojo.setRunnersVoidable(rs.getBoolean(ColumnLabelConstants.RUNNERS_VOIDABLE));
            oddsMyPojo.setVersion(rs.getLong(ColumnLabelConstants.VERSION));
            oddsMyPojo.setIsMarketDataDelayed(rs.getBoolean(ColumnLabelConstants.IS_MARKETDATA_DELAYED));
            int selectionId = rs.getInt(ColumnLabelConstants.SELECTION_ID);
            if (selectionId == lastSelectionId) {
                assert oddsRunners != null;
                oddsRunners.setSelectionId(rs.getInt(ColumnLabelConstants.SELECTION_ID));
                oddsRunners.setHandicap(rs.getInt(ColumnLabelConstants.HANDICAP));
                oddsRunners.setStatus(rs.getString(ColumnLabelConstants.STATUS));
            } else {
                lastSelectionId = rs.getInt(ColumnLabelConstants.SELECTION_ID);
                rs.previous();
                oddsRunners = new OddsRunners();
                oddsRunnersList.add(oddsRunners);
            }

        }
        while (rs.next()) {

        }
        oddsRunnersList.forEach(oddsRunners1 -> oddsRunners1.setEx(this.getOddsEx(marketId, oddsRunners1.getSelectionId())));
        oddsMyPojo.setRunners(oddsRunnersList);
              return oddsMyPojo;
    }

}
