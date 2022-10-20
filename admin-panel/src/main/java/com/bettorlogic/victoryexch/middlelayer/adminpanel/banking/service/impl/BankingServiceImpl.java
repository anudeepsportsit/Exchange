package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dao.BankingDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BankingServiceImpl implements BankingService {
    @Autowired
    private BankingDao bankingDao;

    @Override
    public List<BankingDetailsOutput> getBankingDetails(String userLoginToken) {
        List<BankingDetailsOutput> bankingOutputTO = bankingDao.getBankingDetails(userLoginToken);
        Comparator<BankingDetailsOutput> compareById = (BankingDetailsOutput o1, BankingDetailsOutput o2) -> o1.getUserId().compareTo(o2.getUserId());
        Collections.sort(bankingOutputTO, compareById);
        return bankingOutputTO;
    }

    @Override
    public BankingLogsOutputTO getBankingLogs(Integer userId) {
        BankingLogsOutputTO bankingLogsOutputTO = new BankingLogsOutputTO();
        bankingLogsOutputTO.setUserName(bankingDao.getUserName(userId));
        bankingLogsOutputTO.setBankingLogs(bankingDao.getBankingLogs(userId));
        return bankingLogsOutputTO;
    }

    @Override
    public BankingTansactionsOutputTO updateBankingDetails(String userLoginToken, List<BankingDetailsInput> bankingInputTOList) {
        StringBuilder bulkData = new StringBuilder();

        bankingInputTOList.stream().forEach(bankingDetailsInput ->
                bulkData.append(parseBulkData(bankingDetailsInput)));
        String bulkDataInsert = bulkData.substring(0, bulkData.length() - 1);

        return bankingDao.updateBankingDetails(userLoginToken, bulkDataInsert);
    }

    @Override
    public Boolean updateExposureLimit(BankingUserExposureInputTO bankingUserExposureInputTO) {
        return bankingDao.updateExposureLimit(bankingUserExposureInputTO);
    }

    @Override
    public Integer getRoleId(String userLoginToken) {
        return bankingDao.getRoleId(userLoginToken);
    }

    @Override
    public Boolean getValidUserId(Integer userId) {
        return bankingDao.getValidUserId(userId);
    }

    @Override
    public Boolean getValidateUserToken(String userLoginToken, Integer userId) {
        return bankingDao.getValidateUserToken(userLoginToken, userId);
    }

    @Override
    public List<BankingDetailsOutput> getAdminBankingDetails(String userLoginToken) {
        List<BankingDetailsOutput> bankingOutputTO = bankingDao.getAdminBankingDetails(userLoginToken);
        Comparator<BankingDetailsOutput> compareById = (BankingDetailsOutput o1, BankingDetailsOutput o2) -> o1.getUserId().compareTo(o2.getUserId());
        Collections.sort(bankingOutputTO, compareById);
        return bankingOutputTO;
    }

    @Override
    public List<AdminDepositDetailsTO> getDepositPoints(AdminDepositInputTO adminDepositInputTO) {
        return bankingDao.getAdminDeposits(adminDepositInputTO);
    }

    @Override
    public String addDeposit(AdminDepositPointsTO adminDepositPointsTO) {
        return bankingDao.addAdminDeposit(adminDepositPointsTO);
    }

    private String parseBulkData(BankingDetailsInput banking) {
        return "(" + banking.getUserId() + "," + banking.getAmount() + ",'" + banking.getTransactionType() + "','" + banking.getRemark() + "'),";
    }
}
