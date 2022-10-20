package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dao.RiskManagementDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.service.RiskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskManagementServiceImpl implements RiskManagementService {

    @Autowired
    private RiskManagementDao riskManagementDao;

    @Override
    public PlayersWrapperTO getPlayersInfo(String loginToken) {
        PlayersWrapperTO playersWrapper = new PlayersWrapperTO();
        playersWrapper.setMatchedList(riskManagementDao.getMatchedPlayersInfo(loginToken));
        playersWrapper.setExposureList(riskManagementDao.getExposurePlayersInfo(loginToken));
        return playersWrapper;
    }

    @Override
    public MatchOddsWrapperTO retrieveMatchOdds(String loginToken) {
        return riskManagementDao.getMatchOdds(loginToken);
    }

    @Override
    public FancyBetsWrapperTO retrieveFancy(String loginToken) {
        return riskManagementDao.getFancyBets(loginToken);
    }

    @Override
    public OtherMarketsWrapperTO retrieveOtherMarkets(String loginToken) {
        return riskManagementDao.getTiedGoals(loginToken);
    }

    @Override
    public MatchOddsDropdownWrapperTO retrieveMatchOddsList(Integer eventId, Integer sportId, Integer userId) {
        return riskManagementDao.getMatchOddsList(eventId, sportId, userId);
    }

    @Override
    public FancyDropDownWrapperTO retrieveFancyDetails(Integer eventId, String marketName, String outCome) {
        if (outCome.equalsIgnoreCase("Yes") || outCome.equalsIgnoreCase("No")) {
            return riskManagementDao.getFancyDetails(eventId, marketName);
        } else {
            return riskManagementDao.getFancyDetailsBack(eventId, marketName);
        }
    }
}
