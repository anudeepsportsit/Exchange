package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dao.BetSettlementsDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.AgentBetSettlement;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementOutputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BetSettlementsDaoImpl implements BetSettlementsDao {

    private static final String PROC_GET_ALL_BET_SETTLEMENTS = "select * from get_all_bet_settlements()";
    private static final String PROC_UPDATE_BET_SETTLEMENTS = "select * from update_bet_settlements(?,?,?)";
    private static final String PROC_GET_LOSS_BET_SETTLEMENTS_ID = "select id from bet_settlements where event_id = ? and market_name = ? and not outcome_name = ?";
    private static final String QUERY_GET_BETSETTLEMENT_ID = "select id from bet_settlements where event_id = ? and market_name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BetSettlementOutputTO> getAllBetSettlements() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_ALL_BET_SETTLEMENTS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> extractBetSettlements(cs.executeQuery()));
    }

    private List<BetSettlementOutputTO> extractBetSettlements(ResultSet rs) throws SQLException {
        List<BetSettlementOutputTO> betSettlementList = new ArrayList<>();
        BetSettlementOutputTO betSettlement;
        while (rs.next()) {
            betSettlement = new BetSettlementOutputTO();
            betSettlement.setBetSettlementId(rs.getInt(AdminPanelColumnLabelConstants.BET_SETTLEMENT_ID));
            betSettlement.setEventId(rs.getInt(AdminPanelColumnLabelConstants.BET_EVENT_ID));
            betSettlement.setEventName(rs.getString(AdminPanelColumnLabelConstants.BET_EVENT_NAME));
            betSettlement.setMarketName(rs.getString(AdminPanelColumnLabelConstants.BET_MARKET_NAME));
            betSettlement.setSubmarketName(rs.getString(AdminPanelColumnLabelConstants.BET_SUBMARKET_NAME));
            betSettlement.setOutcomeName(rs.getString(AdminPanelColumnLabelConstants.BET_OUTCOME_NAME));
            betSettlementList.add(betSettlement);
        }
        return betSettlementList;
    }

    @Override
    public String updateBetSettlements(BetSettlementDetailsTO betSettlementInputTO) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_BET_SETTLEMENTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setArray(1, connection.createArrayOf("INTEGER", betSettlementInputTO.getBetSettlementIds()));
            cs.setString(2, betSettlementInputTO.getUserLoginToken());
            cs.setString(3, betSettlementInputTO.getBetSettlementBulkData());
            return cs;
        }, (CallableStatement cs) -> this.extractBetSettlementMessage(cs.executeQuery()));
    }

    @Override
    public List<Integer> getSettlementId(AgentBetSettlement agentBetSettlement) {

        String sql = "select id from bet_settlements where event_id = ? and market_name = ? and outcome_name like '%"+agentBetSettlement.getOutcomeName()+"%'" ;

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentBetSettlement.getEventId());
            cs.setString(2, agentBetSettlement.getMarketName());
            return cs;
        }, (CallableStatement cs) -> this.extractBetSettlementId(cs.executeQuery()));
    }

    @Override
    public List<Integer> getlossSettlementId(AgentBetSettlement agentBetSettlement) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_LOSS_BET_SETTLEMENTS_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentBetSettlement.getEventId());
            cs.setString(2, agentBetSettlement.getMarketName());
            cs.setString(3, agentBetSettlement.getOutcomeName());
            return cs;
        }, (CallableStatement cs) -> this.extractBetSettlementId(cs.executeQuery()));
    }

    @Override
    public void deleteMarkets(AgentBetSettlement agentBetSettlement) {
        try {
            jdbcTemplate.update("delete from odds where eventid=? and providerid=?", agentBetSettlement.getEventId(), agentBetSettlement.getProviderId());
            jdbcTemplate.update("delete from fancymarkets where fancymarketname = ?", agentBetSettlement.getMarketName());
        }catch (Exception e){
            System.out.println("Markets not deleted in odds table");
        }
    }

    @Override
    public List<Integer> getLapsedSettlementId(AgentBetSettlement agentBetSettlement) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(QUERY_GET_BETSETTLEMENT_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentBetSettlement.getEventId());
            cs.setString(2, agentBetSettlement.getMarketName());
            return cs;
        }, (CallableStatement cs) -> this.extractBetSettlementId(cs.executeQuery()));
    }

    private String extractBetSettlementMessage(ResultSet rs) throws SQLException {
        String message = null;
        while (rs.next()) {
            message = rs.getString(AdminPanelColumnLabelConstants.BET_RETURN_MESSAGE);
        }
        return message;
    }

    private List<Integer> extractBetSettlementId(ResultSet rs) throws SQLException {
        List<Integer> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getInt("id"));
        }
        return list;
    }

}
