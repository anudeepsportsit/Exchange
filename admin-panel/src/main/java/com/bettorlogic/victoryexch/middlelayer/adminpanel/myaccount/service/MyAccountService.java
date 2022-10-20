package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.TotalProfitLossWrapperTO;
import org.postgresql.util.PSQLException;

import java.util.List;

public interface MyAccountService {
    UserActivityWrapperTO getActivityLogs(String userToken);

    /*
        This service is used to get user deposit and withdraw details
        which the user has done transactions from banking section
        of admin panel.
        @returns List<AccountStatementTO>
        @input String userToken
     */
    List<AccountStatementTO> getAccountStatement(String userToken,Integer userId) throws PSQLException;

    /*
        This  service is used to get all the betting history details of the selected player
        during the master login
        @returns List<BettingHistoryDetailsOutput>
        @input String userToken
     */
    List<BettingHistoryDetailsOutput> getBettingHistoryDetails(BettingHistoryDetailsInput bettingHistoryDetails) throws Exception;

    /*
       This method is used to receive the input json from front-end and provide the
       betting  profit-loss details of the player between the given dates
       of admin panel.
       @returns SportsBookOutput
       @input BettingHistoryDetailsInput
    */
    TotalProfitLossWrapperTO getBettingProfitLossDetails(BettingHistoryDetailsInput bettingHistoryDetails) throws Exception;

    /**
     * This service is used get the account summary of the sub-ordinate when the admin\super\master
     * navigates from the downline-list tab.
     *
     * @param userToken
     * @param userId
     * @return AccountSummaryTO
     * @throws PSQLException
     */
    AccountSummaryTO getAccountSummaryDetails(String userToken, Integer userId) throws PSQLException;

    String updatePasswordDetails(UpdatePasswordDetailsTO updatePassword);

    String updateExposureDetails(UpdateExposureTO exposureAmount);

    String updateCommissionDetails(UpdateExposureTO commissionAmount);
}