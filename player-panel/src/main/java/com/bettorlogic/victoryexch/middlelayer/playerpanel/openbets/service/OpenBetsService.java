package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.service;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.BetDetailsSubWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.CancelOpenBetsOutput;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.OpenBetsEventWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.UpdateOpenBetsOutput;

import java.util.List;

public interface OpenBetsService {
    List<OpenBetsEventWrapperTO> getOpenBetDetails(String userToken);

    List<UpdateOpenBetsOutput> updateOpenBetDetails(BetDetailsSubWrapperTO betDetailsSubWrapper, int userId) throws Exception;

    CancelOpenBetsOutput cancelOpenBets(String userToken, List<Integer> betIdList) throws Exception;

    int getId(String userToken);

    List<OpenBetsEventWrapperTO> getAgentOpenBetDetails(String marketName);

    Double getExposureLimit(int userId);
}