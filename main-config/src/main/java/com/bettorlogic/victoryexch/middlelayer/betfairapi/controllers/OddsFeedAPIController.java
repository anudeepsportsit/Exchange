package com.bettorlogic.victoryexch.middlelayer.betfairapi.controllers;


import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.ErrorMessageDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.RdExchangeFactory;
import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dto.OddsMyPojo;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class OddsFeedAPIController {
    private static final Logger LOGGER = LogManager.getLogger(OddsFeedAPIController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private RdExchangeFactory rdExchangeFactory;


    @RequestMapping(value = SportsBookConstants.GET_MARKET_ODDS, method = RequestMethod.GET)
    public SportsBookOutput getMarketOdds(@RequestParam @NotNull @Valid String marketId) {
        try {
            LOGGER.info("getMarketodds Started");
            OddsMyPojo oddsTypes = rdExchangeFactory.getMarketOdds(marketId);
            LOGGER.info("getMarketodds ended");
            if (oddsTypes != null) {
                return outputGenerator.getSuccessResponse(oddsTypes);
            } else {
                ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                errorMessageDetails.setErrorMessage("Invalid Session Key or market Id or App key");
                return outputGenerator.getSuccessResponse(errorMessageDetails);
            }

        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_MARKET_ODDS);
        }
    }
}
