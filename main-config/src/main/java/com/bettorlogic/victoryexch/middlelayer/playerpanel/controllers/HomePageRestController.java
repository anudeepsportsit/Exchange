package com.bettorlogic.victoryexch.middlelayer.playerpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.HighlightsSportTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto.*;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service.HomePageService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class HomePageRestController {

    private static final Logger LOGGER = LogManager.getLogger(HomePageRestController.class);
    @Autowired
    private SportsBookOutputGenerator outputGenerator;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private SportsBookService sportsBookService;
    @Autowired
    private UserIdTokenValidationService userIdTokenValidationService;

    @Cacheable(HomePageConstants.CACHE_BANNERS)
    @RequestMapping(value = HomePageConstants.BANNERS, method = RequestMethod.GET)
    public SportsBookOutput getBanners(Integer pageId, String userToken) {
        try {
            if (pageId == null || pageId == 0) {
                throw new Exception(ExceptionConstants.PAGE_ID_NULL);
            }
            LOGGER.info("Rest API for Banners started");
            BannersTO banners = homePageService.retriveBanners(pageId, userToken);
            LOGGER.info("Rest API for Banners successfully completed");
            return outputGenerator.getSuccessResponse(banners);

        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.BANNERS);
        }
    }

    @RequestMapping(value = HomePageConstants.GET_HIGHLIGHTS_SPORTS, method = RequestMethod.GET)
    public SportsBookOutput getHighlightsSports(Integer pageId) {
        try {
            if (pageId == null || pageId == 0) {
                throw new Exception(ExceptionConstants.PAGE_ID_NULL);
            }
            if (pageId == 1) {
                HighlightsSportsDetailsTO highlightsSports = homePageService.getHighlightsSports();
                return outputGenerator.getSuccessResponse(highlightsSports);
            } else {
                HighlightsSportsDetailsTO highlightsSports = homePageService.getInplaySports();
                return outputGenerator.getSuccessResponse(highlightsSports);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_HIGHLIGHTS_SPORTS);
        }
    }

    @RequestMapping(value = HomePageConstants.LEFT_MENU, method = RequestMethod.GET)
    public SportsBookOutput getLeftMenuSports() {
        try {
            SportTO sportTO = homePageService.retriveLeftMenuSports();
            return outputGenerator.getSuccessResponse(sportTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.LEFT_MENU);
        }
    }

    @RequestMapping(value = HomePageConstants.NAVIGATION_BAR, method = RequestMethod.POST)
    public SportsBookOutput getNavigationBar(Integer sportId) {
        try {
            if (sportId != null) {
                SubMenuDetailsWrapperTO subMenuTO = homePageService.retrieveNavigationBar(sportId);
                return outputGenerator.getSuccessResponse(subMenuTO);
            } else {
                throw new Exception(ExceptionConstants.SPORT_ID_NULL);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_NAVIGATION_BAR);
        }
    }

    @RequestMapping(value = HomePageConstants.HEADER, method = RequestMethod.GET)
    public SportsBookOutput getHeaders() {
        try {
            HeadersTO headersTO = homePageService.retriveHeaders();
            return outputGenerator.getSuccessResponse(headersTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.HEADER);
        }
    }


    @RequestMapping(value = HomePageConstants.GET_UPCOMING_OPTIONS, method = RequestMethod.GET)
    public SportsBookOutput getUpcomingOptions(Integer timeInterval) {
        try {
            if (timeInterval != null) {
                Map<String, Object> upcomingOptions = homePageService.getUpcomingOptions(timeInterval);
                return outputGenerator.getSuccessResponse(upcomingOptions);
            } else {
                throw new Exception(ExceptionConstants.TIME_INTERVAL_NULL);
            }


        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_UPCOMING_OPTIONS);
        }
    }

    @RequestMapping(value = HomePageConstants.GET_UPCOMING_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getUpcomingEvents(Integer sportId, Integer optionId, Integer timeInterval, String userToken) {
        try {
            if (StringUtils.isEmpty(userToken) || StringUtils.containsWhitespace(userToken)) {
                userToken = null;
            }
            if (sportId != null && sportId > 0) {
                if (optionId != null && (optionId >= 1 && optionId <= 8)) {
                    if (timeInterval != null) {
                        SportWrapperTO sportDetails = homePageService.getUpcomingEvents(sportId, optionId, timeInterval, userToken);
                        return outputGenerator.getSuccessResponse(sportDetails);
                    } else {
                        throw new Exception(ExceptionConstants.TIME_INTERVAL_NULL);
                    }
                } else {
                    throw new Exception(ExceptionConstants.INVALID_OPTION_ID);
                }
            } else {
                throw new Exception(ExceptionConstants.SPORT_ID_NULL);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_UPCOMING_EVENTS);
        }
    }

    @RequestMapping(value = HomePageConstants.GET_HIGHLIGHTS_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getHighlights(String sportId, String pageId, String userToken) {
        try {
            int sport;
            int page;
            try {
                sport = Integer.parseInt(sportId);
                page = Integer.parseInt(pageId);
            } catch (Exception e) {
                throw new Exception(ExceptionConstants.INVALID_REQUEST);
            }
            int status = sportsBookService.getId(userToken);
            if (status == 0) {
                userToken = null;
            }
            if (page == 1) {
                HighlightsSportTO highlightsSportTO = homePageService.getHighlightsDetails(sport, userToken);
                return outputGenerator.getSuccessResponse(highlightsSportTO);
            }
            if (page == 2) {
                HighlightsSportTO highlightsSportTO = homePageService.getInplayDetails(sport, userToken);
                return outputGenerator.getSuccessResponse(highlightsSportTO);
            } else {
                throw new Exception(ExceptionConstants.INVALID_REQUEST);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_HIGHLIGHTS_EVENTS);
        }
    }


    @RequestMapping(value = HomePageConstants.GET_ADMIN_INPLAY, method = RequestMethod.GET)
    public SportsBookOutput getadminInplay(String sportId, String userToken) {
        try {
            int sport;
            try {
                sport = Integer.parseInt(sportId);
            } catch (Exception e) {
                throw new Exception(ExceptionConstants.INVALID_REQUEST);
            }
            int status = sportsBookService.getId(userToken);
            if (status == 0) {
                throw new Exception(ExceptionConstants.WRONG_TOKEN);
            }
            HighlightsSportTO highlightsSportTO = homePageService.getHighlightsDetails(sport, userToken);
            return outputGenerator.getSuccessResponse(highlightsSportTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_ADMIN_INPLAY);
        }
    }


    @Cacheable(HomePageConstants.CACHE_USER_MENU)
    @RequestMapping(value = HomePageConstants.GET_USER_MENU, method = RequestMethod.GET)
    public SportsBookOutput getUserMenu() {
        try {
            UserMenuDetailsWrapperTO userMenuDetailsWrapperTO = homePageService.getMenuDetails();
            return outputGenerator.getSuccessResponse(userMenuDetailsWrapperTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_USER_MENU);
        }
    }

    @RequestMapping(value = HomePageConstants.GET_SEARCH_RESULTS, method = RequestMethod.POST)
    public SportsBookOutput searchEvents(
            @RequestBody @Valid @NotNull SearchEventDeatilsTO searchEventDeatilsTO) {
        try {
            SearchDetailsTO searchDetailsTO = homePageService.getSearchResults(searchEventDeatilsTO.getName());
            return outputGenerator.getSuccessResponse(searchDetailsTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_SEARCH_RESULTS);
        }
    }

    @RequestMapping(value = HomePageConstants.GET_PROFIT_LOSS, method = RequestMethod.POST)
    public SportsBookOutput getProfitLoss(@RequestBody @NotNull @Valid ProfitLossInputDetailsTO profitLossInputDetails) {
        try {
            Boolean tokenValid = sportsBookService.validateUserToken(profitLossInputDetails.getUserToken());
            if (tokenValid) {
                ProfitLossWrapperTO profitLossWrapperTO = homePageService.getResults(profitLossInputDetails);
                return outputGenerator.getSuccessResponse(profitLossWrapperTO);
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_PROFIT_LOSS);
        }
    }


    @RequestMapping(value = HomePageConstants.GET_TIMEZONES, method = RequestMethod.GET)
    public SportsBookOutput getTimeZones() {
        try {
            System.out.println("Method called");
            List<Timezones> timezonesTo = homePageService.getTimezones();
            return outputGenerator.getSuccessResponse(timezonesTo);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, HomePageConstants.GET_TIMEZONES);
        }

    }
}