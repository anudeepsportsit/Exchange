package com.bettorlogic.victoryexch.middlelayer.common.utils;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.OutcomeDetailsTO;

import java.util.stream.Collectors;

public class OutcomeSorter {
    public static OutcomeDetailsTO sortAndGetBackLayOddsWithHighestSize(EventViewOutcomeDetailsTO eventViewOutcomeDetails) {
        OutcomeDetailsTO outcomeDetails = new OutcomeDetailsTO();
        eventViewOutcomeDetails.getBackOddList().sort(new OddDetailsSizeComparator());
        eventViewOutcomeDetails.getLayOddList().sort(new OddDetailsSizeComparator());
        outcomeDetails.setOutcomeId(eventViewOutcomeDetails.getOutcomeId());
        outcomeDetails.setOutcomeName(eventViewOutcomeDetails.getOutcomeName());
        outcomeDetails.setClOutcomeId(eventViewOutcomeDetails.getClOutcomeId());
        if (eventViewOutcomeDetails.getBackOddList().size() >= 1) {
            OddDetailsTO backOdds = eventViewOutcomeDetails.getBackOddList().get(0);
            if (backOdds != null) {
                outcomeDetails.setBackOdds(backOdds.getOdds());
                outcomeDetails.setBackOddsSize(backOdds.getSize());
                outcomeDetails.setMarketSuspended(backOdds.getMarketSuspended());
            } else {
                outcomeDetails.setBackOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                outcomeDetails.setBackOddsSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            }
        }
        if (eventViewOutcomeDetails.getLayOddList().size() >= 1) {
            OddDetailsTO layOdds = eventViewOutcomeDetails.getLayOddList().get(0);
            if (layOdds != null) {
                outcomeDetails.setLayOdds(layOdds.getOdds());
                outcomeDetails.setLayOddsSize(layOdds.getSize());
                outcomeDetails.setMarketSuspended(layOdds.getMarketSuspended());
            } else {
                outcomeDetails.setLayOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                outcomeDetails.setLayOddsSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            }
        }
        return outcomeDetails;
    }

    public static void sortAndGetBackLayOddsForEventViewPopularAndGoalMarkets(EventViewOutcomeDetailsTO eventViewOutcomeDetails) {
        eventViewOutcomeDetails.setLayOddList(eventViewOutcomeDetails.getLayOddList()
                .parallelStream().filter(oddDetails -> oddDetails.getOdds() != 0)
                .collect(Collectors.toList()));
        eventViewOutcomeDetails.getBackOddList().sort(new OddDetailsOddsComparator().reversed());
        eventViewOutcomeDetails.getLayOddList().sort(new OddDetailsOddsComparator());
        if (eventViewOutcomeDetails.getBackOddList().size() < 1) {
            OddDetailsTO oddDetails = new OddDetailsTO();
            oddDetails.setOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            oddDetails.setSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            eventViewOutcomeDetails.getBackOddList().add(oddDetails);
        }
        if (eventViewOutcomeDetails.getLayOddList().size() < 1) {
            OddDetailsTO oddDetails = new OddDetailsTO();
            oddDetails.setOutcomeId(SportsBookConstants.DEFAULT_ODDS_AND_SIZE.intValue());
            oddDetails.setOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            oddDetails.setSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
            eventViewOutcomeDetails.getLayOddList().add(oddDetails);
        }
        if (eventViewOutcomeDetails.getBackOddList().size() >= 1) {
            eventViewOutcomeDetails.setBackOddList(eventViewOutcomeDetails.getBackOddList().subList(0, 1));
        }
        if (eventViewOutcomeDetails.getLayOddList().size() >= 1) {
            eventViewOutcomeDetails.setLayOddList(eventViewOutcomeDetails.getLayOddList().subList(0, 1));
        }
    }

    public static void sortBackLayOddsAccordingToSize(EventViewOutcomeDetailsTO eventViewOutcomeDetails) {
        OutcomeDetailsTO outcomeDetails = OutcomeSorter.sortAndGetBackLayOddsWithHighestSize(eventViewOutcomeDetails);
        eventViewOutcomeDetails.setMarketSuspended(outcomeDetails.getMarketSuspended());
    }
}