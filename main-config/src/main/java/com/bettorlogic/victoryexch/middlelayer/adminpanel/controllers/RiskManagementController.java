package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.service.RiskManagementService;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + AdminPanelConstants.RISK_MANAGEMENT)
public class RiskManagementController {

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private RiskManagementService riskManagementService;

    @Autowired
    private SportsBookService sportsBookService;

    @RequestMapping(value = SportsBookConstants.GET_PLAYERS, method = RequestMethod.GET)
    public SportsBookOutput getActivityListDetails(String loginToken) {
        try {
            if (StringUtils.isEmpty(loginToken)) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            PlayersWrapperTO playersWrapper = riskManagementService.getPlayersInfo(loginToken);
            return outputGenerator.getSuccessResponse(playersWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_PLAYERS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_MATCH_ODDS, method = RequestMethod.GET)
    public SportsBookOutput getMatchOdds(String loginToken) {
        try {
            Boolean tokenValid = sportsBookService.validateUserToken(loginToken);
            if (StringUtils.isEmpty(loginToken) || !tokenValid) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            MatchOddsWrapperTO matchOddsWrapper = riskManagementService.retrieveMatchOdds(loginToken);
            return outputGenerator.getSuccessResponse(matchOddsWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_MATCH_ODDS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_FANCY_BETS, method = RequestMethod.GET)
    public SportsBookOutput getFancyBets(String loginToken) {
        try {
            if (StringUtils.isEmpty(loginToken)) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            FancyBetsWrapperTO fancyBetsWrapper = riskManagementService.retrieveFancy(loginToken);
            return outputGenerator.getSuccessResponse(fancyBetsWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_BETS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_OTHER_MARKETS, method = RequestMethod.GET)
    public SportsBookOutput getOtherMarkets(String loginToken) {
        try {
            if (StringUtils.isEmpty(loginToken)) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            OtherMarketsWrapperTO otherMarketsWrapper = riskManagementService.retrieveOtherMarkets(loginToken);
            return outputGenerator.getSuccessResponse(otherMarketsWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_OTHER_MARKETS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_MATCH_OODS_LIST, method = RequestMethod.GET)
    public SportsBookOutput getMatchOddsDetails(Integer eventId, Integer sportId, Integer userId) {
        try {
            MatchOddsDropdownWrapperTO matchOddsWrapper = riskManagementService.retrieveMatchOddsList(eventId, sportId, userId);
            return outputGenerator.getSuccessResponse(matchOddsWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_MATCH_ODDS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_FANCY_LIST, method = RequestMethod.GET)
    public SportsBookOutput getFancyDetails(Integer eventId, String marketName, String outCome) {
        try {
            FancyDropDownWrapperTO fancyDropDownWrapper = riskManagementService.retrieveFancyDetails(eventId, marketName, outCome);
            return outputGenerator.getSuccessResponse(fancyDropDownWrapper);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_LIST);
        }
    }

}

