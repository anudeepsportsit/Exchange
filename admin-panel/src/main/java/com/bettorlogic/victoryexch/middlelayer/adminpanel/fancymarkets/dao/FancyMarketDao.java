package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.*;

import java.util.List;

public interface FancyMarketDao {
    FancyMarketListTO getFancyMarket(FancyMarketInputTO input);

    FancySportsListTO getSports();

    FancyLeaguesTO getLeagues(Integer sportId);

    FancyEventsTO getEvents(Integer leagueId);

    void addMarkets(FancyMarketsTO fancyMarketsTO);

    void addFootballMatchOdds(AdminMatchOddsTO adminMatchOddsTO);

    void addOtherMatchOdds(AdminMatchOddsTO adminMatchOddsTO);

    List<AdminMatchOddsDetailsTO> getMatchOddsFootball(FancyMarketInputTO input);

    List<AdminMatchOddsDetailsTO> getMatchOddsOther(FancyMarketInputTO input);

    String updateFancyStatus(FancyMarketsSuspendDetailsTO suspendDetails);

    String updateFancySuspention(FancyMarketsSuspendDetailsTO suspendDetails);
}