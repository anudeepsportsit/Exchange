package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dao;

import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.PlaceInstructionReport;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.CancelOpenBetsOutput;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.OpenBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.OpenBetsEventWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.UpdateOpenBetsOutput;

import java.util.List;

public interface OpenBetsDao {
    List<OpenBetsEventWrapperTO> getOpenBetDetails(String userToken);

    UpdateOpenBetsOutput updateOpenBetDetails(String userToken, OpenBetDetailsTO betDetails, double stake, PlaceInstructionReport placeInstructionReport);

    CancelOpenBetsOutput cancelOpenBets(String userToken, Integer betId);

    void updateOpenBet(PlaceInstructionReport instructionReport, String odds, Integer betId, String returns);

    void cancelBets(Integer betId, String currentUtc, String betfairBetId);

    String  updateUserBalance(String currentUtc);

    Integer getIsMatched(Integer betId);

    int getUserId(String userToken);

    Double getBalance(OpenBetDetailsTO openBetDetails, int userId);

    List<OpenBetsEventWrapperTO> getAgentOpenBetDetails(String marketName);

    Double getUserExposureLimit(int userId);
}