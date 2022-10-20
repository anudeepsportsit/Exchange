package com.bettorlogic.victoryexch.middlelayer.common.dao;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.FancyMarketDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.MatchOddDetailsTO;

import java.util.List;

public interface EventViewDetailsDao {

    FancyMarketDetailsWrapperTO getFancyBetDetails(Integer eventId, String userToken, Integer providerId);

    MatchOddDetailsTO getMatchOddDetails(Integer eventId, String userToken, Integer providerId);

    List<MatchOddDetailsTO> getGoalMarketDetails(Integer eventId, String userToken, Integer providerId);

    List<MatchOddDetailsTO> getPopularMarketDetails(Integer eventId, String userToken, Integer providerId);

    EventViewDetailsWrapperTO getMatchScoreDetails(Integer eventId, String userToken);

}