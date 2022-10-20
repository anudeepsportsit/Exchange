package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.service.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.CountryDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.OutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.OddDetailsOddsComparator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dao.SportsPageDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.HighlightsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LeagueEventDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LiveEventWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.PopularLeaguesWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.service.SportsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class SportsPageServiceImpl implements SportsPageService {

    @Autowired
    private SportsPageDao sportsPageDao;

    @Override
    public HighlightsWrapperTO getHighlightsDetails(Integer sportId, Integer leagueId, String userToken, Integer minutes) {
        return sportsPageDao.getHighlights(sportId, leagueId, userToken, minutes);
    }

    @Override
    public LeagueEventDetailsWrapperTO getLeagueWiseEvents(Integer leagueId, Integer timeInterval, String userToken) {
        LeagueEventDetailsWrapperTO leagueEventDetailsWrapper = sportsPageDao.getLeagueWiseEvents(leagueId, timeInterval, userToken);
        leagueEventDetailsWrapper.getLeagueDetailsList().forEach(highlightsDetails ->
                highlightsDetails.getEventDetailsList().forEach(eventDetails ->
                        eventDetails.getMarketsList().forEach(marketDetails ->
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
                                            marketDetails.setMarketSuspendedFlag(marketDetails.getOutcomesList().get(0).getMarketSuspended());
                                        }
                                ))));
        return leagueEventDetailsWrapper;
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
        outcomeDetails.setOutcomeId(eventViewOutcomeDetails.getOutcomeId());
        outcomeDetails.setOutcomeName(eventViewOutcomeDetails.getOutcomeName());
        outcomeDetails.setClOutcomeId(eventViewOutcomeDetails.getClOutcomeId());
        return outcomeDetails;
    }

    @Override
    public CountryDetailsWrapperTO getSportWiseLeagues(Integer sportId, Integer timeInterval) {
        return sportsPageDao.getSportWiseLeagues(sportId, timeInterval);
    }

    @Override
    public PopularLeaguesWrapperTO getLeaguesDetails(Integer sportId) {
        return sportsPageDao.getPopularLeagues(sportId);
    }

    @Override
    public LiveEventWrapperTO getLiveEventDetails(Integer sportId, String userToken) {
        return sportsPageDao.getEventDetails(sportId, userToken);
    }
}