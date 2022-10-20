package com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.service.impl;

import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dao.RdExchangeFeedRepository;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;

@Repository
public class RdExchangeFeedRepositoryImpl implements RdExchangeFeedRepository {
    private static final String GET_BET_FAIR_KEY = "select name from betfairodds.betfair_key";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getBetFairKey() {

        return jdbcTemplate.execute(connection -> connection.prepareCall(GET_BET_FAIR_KEY,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String result = null;
            while (rs.next()) {
                result = rs.getString(SportsBookConstants.NAME);
            }
            return result;
        });
    }
}

