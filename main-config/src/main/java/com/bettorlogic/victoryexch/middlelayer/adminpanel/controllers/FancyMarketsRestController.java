package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service.FancyMarketService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service.impl.FancyMarketServiceImpl;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL)
public class FancyMarketsRestController {

    private static final Logger LOGGER = LogManager.getLogger(FancyMarketsRestController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private FancyMarketServiceImpl fancyMarketServiceImpl;

    @Autowired
    private FancyMarketService fancyMarketService;


    @RequestMapping(method = POST, value = AdminPanelConstants.GET_FANCY_MARKETS)
    public SportsBookOutput getFancyMarket(@Valid @RequestBody FancyMarketInputTO input) {
        try {
            FancyMarketListTO fancyMarketTO = fancyMarketServiceImpl.getFancyMarket(input);
            return outputGenerator.getSuccessResponse(fancyMarketTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_FANCY_MARKETS);
        }
    }

    @RequestMapping(method = POST, value = AdminPanelConstants.GET_MATCH_ODDS_MARKETS)
    public SportsBookOutput getMatchOddsMarket(@Valid @RequestBody FancyMarketInputTO input) {
        try {
            List<AdminMatchOddsDetailsTO> matchOddsList = fancyMarketServiceImpl.getAdminMatchOdds(input);
            return outputGenerator.getSuccessResponse(matchOddsList);

        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_FANCY_MARKETS);
        }
    }

    @RequestMapping(method = POST, value = AdminPanelConstants.INSERT_FANCY)
    public SportsBookOutput addFancyMarket(@RequestBody List<FancyMarketsTO> fancyMarketsTO) {
        try {
            fancyMarketService.addFancyMarket(fancyMarketsTO);
            return outputGenerator.getSuccessResponse(AdminPanelConstants.SUCCESS_MESSAGE);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.INSERT_FANCY);
        }
    }

    @RequestMapping(method = POST, value = AdminPanelConstants.INSERT_ADMIN_MATCHODDS)
    public SportsBookOutput addAdminMatchOdds(@RequestBody List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS) {
        try {
            fancyMarketService.addAdminOdds(adminMatchOddsDetailsTOS);
            return outputGenerator.getSuccessResponse(AdminPanelConstants.MESSAGE);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.INSERT_ADMIN_MATCHODDS);
        }
    }


    @RequestMapping(method = GET, value = AdminPanelConstants.GET_FANCY_SPORTS)
    public SportsBookOutput getSportsList() {
        try {
            FancySportsListTO fancySportsListTO = fancyMarketService.getSportList();
            return outputGenerator.getSuccessResponse(fancySportsListTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_FANCY_SPORTS);
        }
    }

    @RequestMapping(method = GET, value = SportsBookConstants.GET_FANCY_LEAGUES)
    public SportsBookOutput getLeaguesList(Integer sportId) {
        try {
            FancyLeaguesTO fancyLeaguesTO = fancyMarketService.getLeaguesList(sportId);
            return outputGenerator.getSuccessResponse(fancyLeaguesTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_LEAGUES);
        }
    }

    @RequestMapping(method = GET, value = SportsBookConstants.GET_FANCY_EVENTS)
    public SportsBookOutput getEventsList(Integer leagueId) {
        try {
            FancyEventsTO fancyEventsTO = fancyMarketService.getEventsList(leagueId);
            return outputGenerator.getSuccessResponse(fancyEventsTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_EVENTS);
        }
    }

    @RequestMapping(method = POST, value = AdminPanelConstants.ACTIVE_STATUS)
    public SportsBookOutput isActivate(@RequestBody FancyMarketsSuspendDetailsTO suspendDetails) {
        try {
            String response = fancyMarketService.updateStatus(suspendDetails);
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.ACTIVE_STATUS);
        }
    }

    @RequestMapping(method = POST, value = AdminPanelConstants.SUSPEND_STATUS)
    public SportsBookOutput suspendMarkets(@RequestBody FancyMarketsSuspendDetailsTO suspendDetails) {
        try {
            String response = fancyMarketService.updateSuspention(suspendDetails);
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.SUSPEND_STATUS);
        }
    }

}