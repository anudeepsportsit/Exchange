package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dao.BetListDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.service.BetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetListServiceImpl implements BetListService {

    @Autowired
    private BetListDao betListDao;

    @Override
    public BetListDetailsWrapperTO getBetList(BetListLoginDetailsTO loginDetails) {
        return betListDao.extractBetList(loginDetails);
    }

    @Override
    public BetListDetailsWrapperTO getBetList(BetListLoginDetailsTO loginDetails,List<Integer> userIds){
        Integer[] userIdsArray = new Integer[userIds.size()];
        userIds.toArray(userIdsArray);
        return betListDao.extractBetList(loginDetails,userIdsArray);
    }
    @Override
    public List<Integer> getUserIds(Integer res) {
        return betListDao.getUsers(res);
    }

    @Override
    public BetListDetailsWrapperTO getBetListAllSports(BetListLoginDetailsTO loginDetails, List<Integer> users) {
        Integer[] myArray = new Integer[users.size()];
        users.toArray(myArray);
        if (loginDetails.getBetStatus() == 5){
            loginDetails.setBetStatus(1);
        }if(loginDetails.getBetStatus() == 6){
            loginDetails.setBetStatus(0);
        }if(loginDetails.getBetStatus() == 9){
            loginDetails.setBetStatus(1);
            return betListDao.extractBetListAllSportSettled(loginDetails,myArray);
        }if(loginDetails.getBetStatus() == 8) { // cancelled
            loginDetails.setBetStatus(0);
            return betListDao.extractBetListAllSportcancelled(loginDetails,myArray);
        }if(loginDetails.getBetStatus() == 4) { // voided
            loginDetails.setBetStatus(1);
            return betListDao.extractBetListAllSportVoided(loginDetails,myArray);
        }
        return betListDao.extractBetListAllSport(loginDetails,myArray);
    }

    @Override
    public BetListDetailsWrapperTO getBetListSport(BetListLoginDetailsTO loginDetails, List<Integer> users) {
        Integer[] myArray = new Integer[users.size()];
        users.toArray(myArray);
        if (loginDetails.getBetStatus() == 5){
            loginDetails.setBetStatus(1);
        }if(loginDetails.getBetStatus() == 6){
            loginDetails.setBetStatus(0);
        }if(loginDetails.getBetStatus() == 9){
            loginDetails.setBetStatus(1);
            return betListDao.extractBetListSportSettled(loginDetails,myArray);
        }if(loginDetails.getBetStatus() == 8) { // cancelled
            loginDetails.setBetStatus(0);
            return betListDao.extractBetListSportcancelled(loginDetails,myArray);
        }if(loginDetails.getBetStatus() == 4) { // voided
            loginDetails.setBetStatus(1);
            return betListDao.extractBetListSportVoided(loginDetails,myArray);
        }
        return betListDao.extractBetListSport(loginDetails,myArray);
    }
}