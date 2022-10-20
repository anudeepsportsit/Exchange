package com.bettorlogic.victoryexch.middlelayer.betfairapi.service.impl;

import com.bettorlogic.victoryexch.middlelayer.betfairapi.dto.OddsMyPojo;

public interface BetfairAPIService {

    OddsMyPojo getMarketOdds(String marketId);


}
