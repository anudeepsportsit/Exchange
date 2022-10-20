package com.bettorlogic.victoryexch.middlelayer.playerpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service.HomePageService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto.MultiMarketsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.service.MultiMarketsService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.utils.MultiMarketsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class MultiMarketsRestController {


    private final MultiMarketsService multiMarketsService;
    private final SportsBookOutputGenerator outputGenerator;
    @Autowired
    private HomePageService homePageService;

    @Autowired
    private SportsBookService sportsBookService;


    @Autowired
    public MultiMarketsRestController(SportsBookOutputGenerator outputGenerator, MultiMarketsService multiMarketsService) {
        this.outputGenerator = outputGenerator;
        this.multiMarketsService = multiMarketsService;
    }


    @RequestMapping(value = MultiMarketsConstants.GET_MULTI_MARKETS, method = RequestMethod.GET)
    public SportsBookOutput getMultiMarkets(String userToken) {
        try {
            Boolean tokenValid = sportsBookService.validateUserToken(userToken);
            if (tokenValid) {
                MultiMarketsWrapperTO multiMarketsWrapperTO = multiMarketsService.getMarkets(userToken);
                return outputGenerator.getSuccessResponse(multiMarketsWrapperTO);
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, MultiMarketsConstants.GET_MULTI_MARKETS);
        }
    }


}


