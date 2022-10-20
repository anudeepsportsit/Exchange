package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.service;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.CountryDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.HighlightsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LeagueEventDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.LiveEventWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto.PopularLeaguesWrapperTO;

public interface SportsPageService {

    HighlightsWrapperTO getHighlightsDetails(Integer sportId, Integer leagueId, String userToken, Integer minutes);

    LeagueEventDetailsWrapperTO getLeagueWiseEvents(Integer leagueId, Integer timeInterval, String userToken);

    CountryDetailsWrapperTO getSportWiseLeagues(Integer sportId, Integer timeInterval);

    PopularLeaguesWrapperTO getLeaguesDetails(Integer sportId);

    LiveEventWrapperTO getLiveEventDetails(Integer sportId, String userToken);
}
