package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto.*;

import java.util.List;

public interface BankingDao {

    List<BankingDetailsOutput> getBankingDetails(String userLoginToken);

    List<BankingDetailsOutput> getAdminBankingDetails(String userLoginToken);

    List<BankingDetailsTO> getBankingLogs(Integer userId);

    String getUserName(Integer userId);

    BankingTansactionsOutputTO updateBankingDetails(String userLoginToken, String bulkDataInsert);

    Boolean updateExposureLimit(BankingUserExposureInputTO bankingUserExposureInputTO);

    Integer getRoleId(String userLoginToken);

    Boolean getValidUserId(Integer UserId);

    Boolean getValidateUserToken(String userLoginToken, Integer UserId);

    List<AdminDepositDetailsTO> getAdminDeposits(AdminDepositInputTO adminDepositInputTO);

    String addAdminDeposit(AdminDepositPointsTO adminDepositPointsTO);

}
