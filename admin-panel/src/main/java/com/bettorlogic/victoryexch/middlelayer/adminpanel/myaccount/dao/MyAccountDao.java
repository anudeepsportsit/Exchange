package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.AccountStatementTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.AccountSummaryTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.UpdateExposureTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.UserActivityWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.TotalProfitLossWrapperTO;
import org.postgresql.util.PSQLException;

import java.util.List;

public interface MyAccountDao {
    UserActivityWrapperTO getLogs(String userToken);

    /*
        This method is used to retrieve user deposit and withdraw transaction details from
        the stored procedure "get_account_statement_details"
        @returns List<AccountStatementTO>
        @input String userToken
     */
    List<AccountStatementTO> getAccountStatement(String userToken,Integer userId) throws PSQLException;

    /*
                This method is used to retrieve betting history details from database
                using the stored procedure "get_betting_history_details"
                @returns List<BettingHistoryDetailsOutput>
                @input String userToken
             */
    List<BettingHistoryDetailsOutput> getBettingHistoryDetails(BettingHistoryDetailsInput bettingHistoryDetails);

    /*
        @returns List<BettingHistoryDetailsOutput>
        @input String userToken
     */
    TotalProfitLossWrapperTO getBettingProfitLossDetails(BettingHistoryDetailsInput bettingHistoryDetails) throws PSQLException;


    /**
     * @param userToken
     * @param userId
     * @return
     * @throws PSQLException
     */
    AccountSummaryTO getAccountSummaryDetails(String userToken, Integer userId);

    String getUserPassword(String loginToken);

    void updatePassword(String encode, Integer userId);

    void updateExposure(UpdateExposureTO exposureAmount);

    void updateCommission(UpdateExposureTO commissionAmount);

}