package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto.*;

import java.util.List;

public interface BankingService {

    List<BankingDetailsOutput> getBankingDetails(String userLoginToken);

    BankingLogsOutputTO getBankingLogs(Integer userId);

    BankingTansactionsOutputTO updateBankingDetails(String userLoginToken, List<BankingDetailsInput> bankingInputTOList);

    Boolean updateExposureLimit(BankingUserExposureInputTO bankingUserExposureInputTO);

    Integer getRoleId(String userLoginToken);

    Boolean getValidUserId(Integer UserId);

    Boolean getValidateUserToken(String userLoginToken, Integer UserId);

    List<BankingDetailsOutput> getAdminBankingDetails(String userLoginToken);

    List<AdminDepositDetailsTO> getDepositPoints(AdminDepositInputTO adminDepositInputTO);

    String addDeposit(AdminDepositPointsTO adminDepositPointsTO);

}
