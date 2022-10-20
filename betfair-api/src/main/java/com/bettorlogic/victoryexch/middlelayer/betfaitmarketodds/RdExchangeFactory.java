package com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds;

import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dao.APICaller;
import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dao.RdExchangeFeedRepository;
import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dto.OddsMyPojo;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RdExchangeFactory {

    private static final Logger LOGGER = LogManager.getLogger(RdExchangeFactory.class);
    @Autowired
    private APICaller apiCaller;
    @Autowired
    private RdExchangeFeedRepository rdExchangeFeedRepository;

    public OddsMyPojo getMarketOdds(String marketId) throws Exception {
        String authenticationKey = rdExchangeFeedRepository.getBetFairKey();
        String LIVE_ODDS_REQUEST_JSON = "{\"marketIds\":[" + marketId + "],\"priceProjection\":" +
                "{\"priceData\":[\"SP_AVAILABLE\",\"EX_BEST_OFFERS\",\"SP_TRADED\"," +

                "\"EX_TRADED\"],\"virtualise\":\"true\"}}, \"id\": 1}]";
        LOGGER.info("getMarketOdds Started");
        String response = apiCaller.processAPICallForFeed(SportsBookConstants.LIVE_MARKETBOOK_URL, LIVE_ODDS_REQUEST_JSON, SportsBookConstants.APP_KEY, authenticationKey);
        LOGGER.info("getMarketOdds Ended");
        List<OddsMyPojo> oddsTypesList = new ArrayList<>();
        if (response != null) {
            oddsTypesList = new ObjectMapper().readValue(response, new TypeReference<List<OddsMyPojo>>() {
            });
        }
        return oddsTypesList.size() >= 1 ? oddsTypesList.get(0) : null;
    }


}