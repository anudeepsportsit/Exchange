package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dao;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto.MultiMarketsWrapperTO;

public interface MultiMarketsDao {
    MultiMarketsWrapperTO getMultiMarkets(String userToken);
}
