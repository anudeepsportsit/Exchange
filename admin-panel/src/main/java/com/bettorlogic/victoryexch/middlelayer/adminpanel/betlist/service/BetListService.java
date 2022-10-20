package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListLoginDetailsTO;

import java.util.List;

public interface BetListService {

    BetListDetailsWrapperTO getBetList(BetListLoginDetailsTO loginDetails);

    BetListDetailsWrapperTO getBetList(BetListLoginDetailsTO loginDetails,List<Integer> userIds);

    List<Integer> getUserIds(Integer res);

    BetListDetailsWrapperTO getBetListAllSports(BetListLoginDetailsTO loginDetails, List<Integer> users);

    BetListDetailsWrapperTO getBetListSport(BetListLoginDetailsTO loginDetails, List<Integer> users);
}
