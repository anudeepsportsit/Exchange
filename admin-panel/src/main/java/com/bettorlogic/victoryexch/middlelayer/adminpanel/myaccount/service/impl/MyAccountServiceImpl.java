package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dao.MyAccountDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.TotalProfitLossWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.service.MyAccountService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.PasswordEncoder;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class MyAccountServiceImpl implements MyAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountServiceImpl.class);
    @Autowired
    private MyAccountDao myAccountDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserActivityWrapperTO getActivityLogs(String userToken) {
        return myAccountDao.getLogs(userToken);
    }

    @Override
    public List<AccountStatementTO> getAccountStatement(String userToken,Integer userId) throws PSQLException {
        return myAccountDao.getAccountStatement(userToken, userId);
    }

    @Override
    public List<BettingHistoryDetailsOutput> getBettingHistoryDetails(BettingHistoryDetailsInput bettingHistoryDetails) throws Exception {
        LOGGER.info("Service for 'getBettingHistoryDetails' started");
        if (this.isValidFormat(bettingHistoryDetails.getFromDate()) &&
                this.isValidFormat(bettingHistoryDetails.getToDate())) {
            List<BettingHistoryDetailsOutput> bettingHistoryDetailsOutputList = myAccountDao.getBettingHistoryDetails(bettingHistoryDetails);
            LOGGER.info("Service for 'getBettingHistoryDetails' completed successfully");
            return bettingHistoryDetailsOutputList;
        } else {
            throw new Exception(ExceptionConstants.INVALID_DATE);
        }
    }

    @Override
    public TotalProfitLossWrapperTO getBettingProfitLossDetails(BettingHistoryDetailsInput bettingHistoryDetails) throws Exception {
        LOGGER.info("Service for 'getBettingProfitLossDetails' started");
        if (this.isValidFormat(bettingHistoryDetails.getFromDate()) &&
                this.isValidFormat(bettingHistoryDetails.getToDate())) {
            TotalProfitLossWrapperTO bettingProfitLossOutputList = myAccountDao.getBettingProfitLossDetails(bettingHistoryDetails);
            LOGGER.info("Service for 'getBettingProfitLossDetails' completed successfully");
            return bettingProfitLossOutputList;
        } else {
            throw new Exception(ExceptionConstants.INVALID_DATE);
        }
    }

    private boolean isValidFormat(String value) throws Exception {
        LocalDateTime ldt;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(SportsBookConstants.DATE_FORMAT);
        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    throw new Exception(ExceptionConstants.INVALID_DATE);
                }
            }
        }

    }

    @Override
    public AccountSummaryTO getAccountSummaryDetails(String userToken, Integer userId) throws PSQLException {
        LOGGER.info("Service for 'getAccountSummaryDetails' started");
        AccountSummaryTO accountSummary = myAccountDao.getAccountSummaryDetails(userToken, userId);
        LOGGER.info("Service for 'getAccountSummaryDetails' completed successfully");
        return accountSummary;
    }

    @Override
    public String updatePasswordDetails(UpdatePasswordDetailsTO updatePassword) {
        String currentPassword = myAccountDao.getUserPassword(updatePassword.getLoginToken());
        boolean isPasswordMatch = passwordEncoder.matches((updatePassword.getUserPassword()), currentPassword);
        if (isPasswordMatch) {
            myAccountDao.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()), updatePassword.getUserId());
            return SportsBookConstants.UPDATE_SUCCESSFULLY;
        } else {
            return SportsBookConstants.NOT_MATCHED;
        }
    }

    @Override
    public String updateExposureDetails(UpdateExposureTO exposureAmount) {
        String currentPassword = myAccountDao.getUserPassword(exposureAmount.getLoginToken());
        boolean isPasswordMatch = passwordEncoder.matches((exposureAmount.getUserPassword()), currentPassword);
        if (isPasswordMatch) {
            myAccountDao.updateExposure(exposureAmount);
            return SportsBookConstants.UPDATE_SUCCESSFULLY;
        } else {
            return SportsBookConstants.NOT_MATCHED;
        }
    }

    @Override
    public String updateCommissionDetails(UpdateExposureTO commissionAmount) {
        String currentPassword = myAccountDao.getUserPassword(commissionAmount.getLoginToken());
        boolean isPasswordMatch = passwordEncoder.matches((commissionAmount.getUserPassword()), currentPassword);
        if (isPasswordMatch) {
            myAccountDao.updateCommission(commissionAmount);
            return SportsBookConstants.UPDATE_SUCCESSFULLY;
        } else {
            return SportsBookConstants.NOT_MATCHED;
        }
    }


}