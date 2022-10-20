package com.bettorlogic.victoryexch.middlelayer.betfairapi.service;

import com.bettorlogic.victoryexch.middlelayer.betfairapi.dao.impl.BetfairAPIDaoImpl;
import com.bettorlogic.victoryexch.middlelayer.betfairapi.dto.OddsMyPojo;
import com.bettorlogic.victoryexch.middlelayer.betfairapi.service.impl.BetfairAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetfairService implements BetfairAPIService {

    @Autowired
    private BetfairAPIDaoImpl betfairAPIDao;

    @Override
    public OddsMyPojo getMarketOdds(String marketId) {

        return betfairAPIDao.getOdds(marketId);
    }
}
