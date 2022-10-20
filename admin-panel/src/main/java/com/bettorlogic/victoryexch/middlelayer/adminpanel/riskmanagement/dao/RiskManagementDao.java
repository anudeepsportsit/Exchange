package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto.*;

import java.util.List;

public interface RiskManagementDao {

    List<PlayersDetailsTO> getMatchedPlayersInfo(String loginToken);

    List<PlayersDetailsTO> getExposurePlayersInfo(String loginToken);

    MatchOddsWrapperTO getMatchOdds(String loginToken);

    FancyBetsWrapperTO getFancyBets(String loginToken);

    OtherMarketsWrapperTO getTiedGoals(String loginToken);

    MatchOddsDropdownWrapperTO getMatchOddsList(Integer eventId, Integer sportId, Integer userId);

    FancyDropDownWrapperTO getFancyDetails(Integer eventId, String marketName);

    FancyDropDownWrapperTO getFancyDetailsBack(Integer eventId, String marketName);
}
