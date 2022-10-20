package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.service.impl;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dao.MultiMarketsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto.MultiMarketsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.service.MultiMarketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiMarketsServiceImpl implements MultiMarketsService {

    @Autowired
    private MultiMarketsDao multiMarketsDao;

    @Override
    public MultiMarketsWrapperTO getMarkets(String userToken) {
        return multiMarketsDao.getMultiMarkets(userToken);
    }
}