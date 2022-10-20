package com.bettorlogic.victoryexch.middlelayer.common.dao;

import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;

import java.util.List;

public interface BetSlipDetailsDao {
    BetDetailsFetchTO getBetSlipDetails(BetDetailsFetchTO betDetailsFetch);

    int[] saveBetDetails(List<BetDetailsTO> betDetails, Integer betSlipId);

    Integer saveBet(String userToken, BetDetailsTO betDetails, Integer betSlipId);

    Integer saveBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails);

    BetSlipValidationDetailsTO validateBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails,
                                                      BetDetailsTO betDetails);

    UserBalanceDetailsTO updateUserBalanceDetails(UserBalanceDetailsTO userBalanceDetails, Double userExposure);

    void updateBetSettlementDetails();

    String getSessionKey();

    Integer getBetsCount(int betSlipId);

    void removeBetSlip(int betSlipId);

    Integer getBetDelay(Integer marketId);

    Integer getBetDelayMatchOdds(Integer eventId);
}