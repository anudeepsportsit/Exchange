package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.*;

import java.util.List;

public interface FancyMarketService {

    FancyMarketListTO getFancyMarket(FancyMarketInputTO input);

    FancySportsListTO getSportList();

    FancyLeaguesTO getLeaguesList(Integer sportId);

    FancyEventsTO getEventsList(Integer leagueId);

    void addFancyMarket(List<FancyMarketsTO> fancyMarketsTO);

    void addAdminOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS);

    List<AdminMatchOddsDetailsTO> getAdminMatchOdds(FancyMarketInputTO input);

    String updateStatus(FancyMarketsSuspendDetailsTO suspendDetails);

    String updateSuspention(FancyMarketsSuspendDetailsTO suspendDetails);
}
