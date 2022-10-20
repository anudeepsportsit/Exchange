package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.service.BankingService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.DownlineListDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service.DownlineListService;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + AdminPanelConstants.BANKING)
public class BankingRestController {

    private static final Logger LOGGER = LogManager.getLogger(BankingRestController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private BankingService bankingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DownlineListService downlineListService;

    @Autowired
    private DownlineListDao downlineListDao;

    @Autowired
    private SportsBookService sportsBookService;

    @RequestMapping(value = AdminPanelConstants.GET_BANKING_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getBankingDetails(String userLoginToken) {
        try {
            if (!userLoginToken.isEmpty()) {
                Integer roleId = bankingService.getRoleId(userLoginToken);
                Boolean validateToken = downlineListDao.getDbToken(userLoginToken);
                if (validateToken) {
                    List<BankingDetailsOutput> bankingOutputList;
                    if (roleId == 1) {
                        bankingOutputList = bankingService.getAdminBankingDetails(userLoginToken);
                    } else {
                        bankingOutputList = bankingService.getBankingDetails(userLoginToken);
                    }
                    return outputGenerator.getSuccessResponse(bankingOutputList);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_BANKING_DETAILS);
        }
    }

    @RequestMapping(value = AdminPanelConstants.GET_BANKING_LOGS, method = RequestMethod.GET)
    public SportsBookOutput getBankingLogs(Integer userId) {
        try {
            if (userId != null) {
                Boolean validateUserId = bankingService.getValidUserId(userId);
                if (validateUserId) {
                    BankingLogsOutputTO bankingLogsOutputTO = bankingService.getBankingLogs(userId);
                    return outputGenerator.getSuccessResponse(bankingLogsOutputTO);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_USER_ID);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_USER_ID);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_BANKING_LOGS);
        }
    }

    @RequestMapping(value = AdminPanelConstants.UPDATE_BANKING_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput updateBankingDetails(@RequestBody @Valid @NotNull BankingDetailsInputTO bankingTransactionsList) {
        try {
            Boolean tokenValid = sportsBookService.validateUserToken(bankingTransactionsList.getUserLoginToken());
            if (tokenValid) {
                boolean isValidPassword;
                String dbUserPassword = downlineListService.getDbPassword(bankingTransactionsList.getUserLoginToken());
                isValidPassword = passwordEncoder.matches(bankingTransactionsList.getUserPassword(), dbUserPassword);

                if (isValidPassword) {
                    BankingTansactionsOutputTO bankingTansactionsOutputDetails = bankingService.updateBankingDetails(bankingTransactionsList.getUserLoginToken(), bankingTransactionsList.getBankingDetailsList());
                    return outputGenerator.getSuccessResponse(bankingTansactionsOutputDetails);
                } else {
                    String notValid = AdminPanelConstants.INVALID;
                    return outputGenerator.getSuccessResponse(notValid);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.UPDATE_BANKING_DETAILS);
        }
    }

    @RequestMapping(value = AdminPanelConstants.UPDATE_EXPOSURE_LIMIT, method = RequestMethod.POST)
    public SportsBookOutput updateExposureLimit(@RequestBody @Valid @NotNull BankingUserExposureInputTO bankingUserExposureInputTO) {
        try {
            if (bankingUserExposureInputTO.getUserLoginToken() != null) {
                if (bankingUserExposureInputTO.getUserId() != null) {
                    Boolean validateToken = bankingService.getValidateUserToken(bankingUserExposureInputTO.getUserLoginToken(), bankingUserExposureInputTO.getUserId());
                    if (validateToken) {
                        Map<String, Boolean> result = new HashMap<>();
                        Boolean isUpdated = bankingService.updateExposureLimit(bankingUserExposureInputTO);
                        result.put(AdminPanelConstants.IS_UPDATED, isUpdated);
                        return outputGenerator.getSuccessResponse(result);
                    } else {
                        throw new Exception(ExceptionConstants.INVALID_USER);
                    }
                } else {
                    throw new Exception(ExceptionConstants.INVALID_USER_ID);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.UPDATE_EXPOSURE_LIMIT);
        }
    }

    @RequestMapping(value = AdminPanelConstants.ADMIN_DEPOST_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getAdminDeposits(@RequestBody @Valid @NotNull AdminDepositInputTO adminDepositInputTO) {
        try {
            List<AdminDepositDetailsTO> depositList = bankingService.getDepositPoints(adminDepositInputTO);
            return outputGenerator.getSuccessResponse(depositList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.ADMIN_DEPOST_DETAILS);
        }
    }


    @RequestMapping(value = AdminPanelConstants.ADD_ADMIN_POINTS, method = RequestMethod.POST)
    public SportsBookOutput getAdminPoints(@RequestBody @Valid @NotNull AdminDepositPointsTO adminDepositPointsTO) {
        try {
            String response = bankingService.addDeposit(adminDepositPointsTO);
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.ADD_ADMIN_POINTS);
        }
    }

}

