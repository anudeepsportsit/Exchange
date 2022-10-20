package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListLoginDetailsTO;

import java.util.List;

public interface BetListDao {
    BetListDetailsWrapperTO extractBetList(BetListLoginDetailsTO loginDetails);

    BetListDetailsWrapperTO extractBetList(BetListLoginDetailsTO loginDetails,Integer[] userIds);

    List<Integer> getUsers(Integer res);

    BetListDetailsWrapperTO extractBetListAllSports(BetListLoginDetailsTO loginDetails, String users);

    BetListDetailsWrapperTO extractBetListAllSport(BetListLoginDetailsTO loginDetails, Integer[] users);

    BetListDetailsWrapperTO extractBetListAllSportSettled(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListAllSportcancelled(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListAllSportVoided(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListSport(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListSportSettled(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListSportcancelled(BetListLoginDetailsTO loginDetails, Integer[] myArray);

    BetListDetailsWrapperTO extractBetListSportVoided(BetListLoginDetailsTO loginDetails, Integer[] myArray);
}