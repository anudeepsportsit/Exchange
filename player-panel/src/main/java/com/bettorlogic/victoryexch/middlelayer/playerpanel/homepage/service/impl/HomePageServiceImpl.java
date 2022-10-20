package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.EventDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.HighlightsSportTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.OutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.OddDetailsOddsComparator;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dao.HomePageDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto.*;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service.HomePageService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.OutcomeSorterComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomePageDao homePageDao;

    @Override
    public BannersTO retriveBanners(Integer pageId, String userToken) {
        return homePageDao.getBannersDao(pageId, userToken);
    }

    @Override
    public SportTO retriveLeftMenuSports() {
        return homePageDao.getSportsDao();
    }

    @Override
    public SportWrapperTO getUpcomingEvents(Integer sportId, Integer optionId, Integer timeInterval, String userToken) {
        SportWrapperTO sportWrapper = homePageDao.getUpcomingEvents(sportId, optionId, timeInterval, userToken);
        this.sortBackAndLayOdds(sportWrapper);
        sportWrapper.getSportsList().forEach(sportHierarchyDetails -> {
            this.addEmptyDrawOutcome(sportHierarchyDetails.getEventsList(), sportHierarchyDetails.getSportId());
        });
        return sportWrapper;
    }

    private void addEmptyDrawOutcome(List<EventDetailsTO> eventDetailsList, Integer sportId) {
        eventDetailsList.forEach(eventDetails ->
                eventDetails.getMarketsList().forEach(marketDetails -> {
                            boolean noDrawOutcome = marketDetails.getOutcomesList().size() < 3;
                            if (noDrawOutcome && sportId.equals(SportsBookConstants.SportId.CRICKET)) {
                                OutcomeDetailsTO outcomeDetails = new OutcomeDetailsTO();
                                outcomeDetails.setBackOdds(BigDecimal.ZERO.doubleValue());
                                outcomeDetails.setLayOdds(BigDecimal.ZERO.doubleValue());
                                outcomeDetails.setOutcomeId(BigDecimal.ZERO.intValue());
                                outcomeDetails.setOutcomeOrder(2);
                                outcomeDetails.setOutcomeName(SportsBookConstants.OutcomeNames.X);
                                marketDetails.getOutcomesList().add(outcomeDetails);
                            }
                    if(sportId == 4) {
                        Collections.swap(marketDetails.getOutcomesList(), 1, 2);
                    }
                    else{marketDetails.getOutcomesList().sort(new OutcomeSorterComparator());}
                        }
                ));
    }

    private void sortBackAndLayOdds(SportWrapperTO sportWrapper) {
        sportWrapper.getSportsList().forEach(sportHierarchyDetails ->
                sportHierarchyDetails.getEventsList().forEach(eventDetails ->
                        eventDetails.getMarketsList().forEach(marketDetails -> {
                                    marketDetails.getEventViewOutcomeDetailsList().forEach(eventViewOutcomeDetails -> {
                                        eventViewOutcomeDetails.setBackOddList(eventViewOutcomeDetails.getBackOddList()
                                                .parallelStream().filter(oddDetails -> oddDetails.getOdds() != 0).collect(Collectors.toList()));
                                        eventViewOutcomeDetails.setLayOddList(eventViewOutcomeDetails.getLayOddList()
                                                .parallelStream().filter(oddDetails -> oddDetails.getOdds() != 0).collect(Collectors.toList()));
                                        eventViewOutcomeDetails.getBackOddList().sort(new OddDetailsOddsComparator().reversed());
                                        eventViewOutcomeDetails.getLayOddList().sort(new OddDetailsOddsComparator());
                                        if (eventViewOutcomeDetails.getBackOddList().size() >= 1) {
                                            eventViewOutcomeDetails.setBackOddList(eventViewOutcomeDetails.getBackOddList().subList(0, 1));
                                        }
                                        if (eventViewOutcomeDetails.getLayOddList().size() >= 1) {
                                            eventViewOutcomeDetails.setLayOddList(eventViewOutcomeDetails.getLayOddList().subList(0, 1));
                                        }
                                        marketDetails.getOutcomesList().add(this.setOutcomeDetails(eventViewOutcomeDetails));
                                        if (marketDetails.getOutcomesList().size() >= 1) {
                                            marketDetails.setMarketSuspendedFlag(marketDetails.getOutcomesList().get(0).getMarketSuspended());
                                        }

                                    });
                                }
                        )));
    }

    private OutcomeDetailsTO setOutcomeDetails(EventViewOutcomeDetailsTO eventViewOutcomeDetails) {
        OutcomeDetailsTO outcomeDetails = new OutcomeDetailsTO();
        if (eventViewOutcomeDetails.getBackOddList().size() >= 1) {
            outcomeDetails.setBackOdds(eventViewOutcomeDetails.getBackOddList().get(0).getOdds());
            outcomeDetails.setBackOddsSize(eventViewOutcomeDetails.getBackOddList().get(0).getSize());
        }
        if (eventViewOutcomeDetails.getLayOddList().size() >= 1) {
            outcomeDetails.setLayOdds(eventViewOutcomeDetails.getLayOddList().get(0).getOdds());
            outcomeDetails.setLayOddsSize(eventViewOutcomeDetails.getLayOddList().get(0).getSize());
        }
        outcomeDetails.setMarketSuspended(eventViewOutcomeDetails.getMarketSuspended());
        outcomeDetails.setOutcomeId(eventViewOutcomeDetails.getOutcomeId());
        outcomeDetails.setOutcomeName(eventViewOutcomeDetails.getOutcomeName());
        outcomeDetails.setClOutcomeId(eventViewOutcomeDetails.getClOutcomeId());
        outcomeDetails.setOutcomeOrder(eventViewOutcomeDetails.getOutcomeOrder());
        return outcomeDetails;
    }

    @Override
    public HeadersTO retriveHeaders() {
        return homePageDao.getHeadersDao();
    }

    @Override
    public Map<String, Object> getUpcomingOptions(Integer timeInterval) {
        Map<String, Object> optionsMap = new HashMap<>();
        List<UpcomingOptionsTO> upcomingOptionsList = homePageDao.getUpcomingOptions();
        List<SportDetailsTO> sportDetailsList = homePageDao.getUpcomingSportButtonDetails(timeInterval);
        optionsMap.put(HomePageConstants.OPTIONS, upcomingOptionsList);
        optionsMap.put(HomePageConstants.BUTTONS, sportDetailsList);
        return optionsMap;
    }

    @Override
    public SearchDetailsTO getSearchResults(String name) {
        return homePageDao.getResults(name);
    }

    @Override
    public SubMenuDetailsWrapperTO retrieveNavigationBar(Integer sportId) {
        return homePageDao.retrieveNavigationBar(sportId);
    }

    @Override
    public HighlightsSportTO getHighlightsDetails(int sportId, String userToken) {
        return homePageDao.retriveHighlights(sportId, userToken);
    }

    @Override
    public HighlightsSportTO getAdminInplayDetails(int sportId, String userToken) {
        return homePageDao.retriveAdminInplay(sportId, userToken);
    }

    @Override
    public HighlightsSportTO getInplayDetails(int sportId, String userToken) {
        return homePageDao.retriveInplay(sportId, userToken);
    }

    @Override
    public UserMenuDetailsWrapperTO getMenuDetails() {
        return homePageDao.getMenuDetails();
    }

    @Override
    public HighlightsSportsDetailsTO getHighlightsSports() {
        return homePageDao.retriveHighlightsSports();
    }

    @Override
    public HighlightsSportsDetailsTO getInplaySports() {
        return homePageDao.retriveInplaySports();
    }

    @Override
    public ProfitLossWrapperTO getResults(ProfitLossInputDetailsTO profitLossInputDetails) {
        return homePageDao.retriveResults(profitLossInputDetails);
    }

    @Override
    public List<Timezones> getTimezones() {
        return homePageDao.getTimezones();
    }
}