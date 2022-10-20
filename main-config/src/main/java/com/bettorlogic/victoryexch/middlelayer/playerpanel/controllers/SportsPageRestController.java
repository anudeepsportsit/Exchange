package com.bettorlogic.victoryexch.middlelayer.playerpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.CountryDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service.HomePageService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.HighlightsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LeagueEventDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LiveEventWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.PopularLeaguesWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.service.SportsPageService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.utils.SportsPageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class SportsPageRestController {

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private SportsPageService sportsPageService;

    @Autowired
    private SportsBookService sportsBookService;

    @Autowired
    private HomePageService homePageService;

    @RequestMapping(value = SportsPageConstants.GET_HIGHLIGHTS, method = RequestMethod.GET)
    public SportsBookOutput getHighlights(String sportId, String leagueId, String userToken, Integer minutes) {
        try {
            int sport;
            int league;
            try {
                sport = Integer.parseInt(sportId);
                league = Integer.parseInt(leagueId);
            } catch (Exception e) {
                throw new Exception(ExceptionConstants.INVALID_REQUEST);
            }
            int status = sportsBookService.getId(userToken);
            if (status == 0) {
                userToken = null;
            }
            if (league == 0) {
                throw new Exception(ExceptionConstants.LEAGUE_ID_NULL);
            }
            HighlightsWrapperTO highlightsTO = sportsPageService.getHighlightsDetails(sport, league, userToken, minutes);
            return outputGenerator.getSuccessResponse(highlightsTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsPageConstants.GET_HIGHLIGHTS);
        }
    }

    @RequestMapping(value = SportsPageConstants.GET_LIVE_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getLiveEvents(String sportId, String userToken) {
        try {
            int sport;
            try {
                sport = Integer.parseInt(sportId);
            } catch (Exception e) {
                throw new Exception(ExceptionConstants.INVALID_REQUEST);
            }
            if (sport == 0) {
                throw new Exception(ExceptionConstants.SPORT_ID_NULL);
            }
            int status = sportsBookService.getId(userToken);
            if (status == 0) {
                userToken = null;
            }
            LiveEventWrapperTO liveEventWrapperTO = sportsPageService.getLiveEventDetails(sport, userToken);
            return outputGenerator.getSuccessResponse(liveEventWrapperTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsPageConstants.GET_LIVE_EVENTS);
        }
    }


    @RequestMapping(value = SportsPageConstants.GET_POPULAR_LEAGUES, method = RequestMethod.GET)
    public SportsBookOutput getPopularLeagues(Integer sportId) {
        try {
            if (sportId == null || sportId == 0) {
                throw new Exception(ExceptionConstants.SPORT_ID_NULL);
            }
            PopularLeaguesWrapperTO popularLeaguesWrapperTO = sportsPageService.getLeaguesDetails(sportId);
            return outputGenerator.getSuccessResponse(popularLeaguesWrapperTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsPageConstants.GET_POPULAR_LEAGUES);
        }
    }

    @RequestMapping(value = SportsPageConstants.GET_LEAGUE_WISE_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getLeagueWiseEvents(Integer leagueId, Integer timeInterval, String userToken) {
        try {
            if (StringUtils.isEmpty(userToken) || StringUtils.containsWhitespace(userToken)) {
                userToken = null;
            }
            if (leagueId != null && leagueId != 0) {
                if (timeInterval != null) {
                    LeagueEventDetailsWrapperTO leagueWiseEvents = sportsPageService.getLeagueWiseEvents(leagueId, timeInterval, userToken);
                    return outputGenerator.getSuccessResponse(leagueWiseEvents);
                } else {
                    throw new Exception(ExceptionConstants.TIME_INTERVAL_NULL);
                }
            } else {
                throw new Exception(ExceptionConstants.LEAGUE_ID_NULL);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsPageConstants.GET_LEAGUE_WISE_EVENTS);
        }
    }

    @RequestMapping(value = SportsPageConstants.GET_SPORT_WISE_LEAGUES, method = RequestMethod.GET)
    public SportsBookOutput getSportWiseLeagues(Integer sportId, Integer timeInterval) {
        try {
            if (timeInterval == null) {
                timeInterval = 0;
            }
            if (sportId != null && sportId != 0) {
                CountryDetailsWrapperTO countrySportDetails = sportsPageService.getSportWiseLeagues(sportId, timeInterval);
                return outputGenerator.getSuccessResponse(countrySportDetails);
            } else {
                throw new Exception(ExceptionConstants.SPORT_ID_NULL);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsPageConstants.GET_SPORT_WISE_LEAGUES);
        }
    }

}