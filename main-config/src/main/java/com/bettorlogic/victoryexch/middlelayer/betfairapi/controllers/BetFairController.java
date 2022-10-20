package com.bettorlogic.victoryexch.middlelayer.betfairapi.controllers;

import com.bettorlogic.victoryexch.middlelayer.betfairapi.service.impl.BetfairAPIService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class BetFairController {

    private static final Logger LOGGER = LogManager.getLogger(BetFairController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private BetfairAPIService betfairAPIService;


    /*@RequestMapping(value = SportsBookConstants.GET_MARKET_ODDS1, method = RequestMethod.GET)
    public SportsBookOutput getMarketOdds(@RequestParam @NotNull @Valid String marketId) {
        try {
          OddsMyPojo oddsMyPojo = betfairAPIService.getMarketOdds(marketId);
            return outputGenerator.getSuccessResponse(oddsMyPojo);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_MARKET_ODDS1);
        }
    }*/
}
