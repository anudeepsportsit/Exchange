package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.service;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto.MultiMarketsWrapperTO;

public interface MultiMarketsService {
    MultiMarketsWrapperTO getMarkets(String userToken);
}
