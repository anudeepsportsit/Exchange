package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto.*;

public interface RiskManagementService {

    PlayersWrapperTO getPlayersInfo(String loginToken);

    MatchOddsWrapperTO retrieveMatchOdds(String loginToken);

    FancyBetsWrapperTO retrieveFancy(String loginToken);

    OtherMarketsWrapperTO retrieveOtherMarkets(String loginToken);

    MatchOddsDropdownWrapperTO retrieveMatchOddsList(Integer eventId, Integer sportId, Integer userId);

    FancyDropDownWrapperTO retrieveFancyDetails(Integer eventId, String marketName, String outCome);
}
