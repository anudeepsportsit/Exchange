package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.ErrorMessageDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.TotalProfitLossWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.service.MyAccountService;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + AdminPanelConstants.MY_ACCOUNT)
public class MyAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountController.class);
    @Autowired
    private SportsBookOutputGenerator outputGenerator;
    @Autowired
    private MyAccountService myAccountService;

    @Autowired
    private SportsBookService sportsBookService;

    @Autowired
    private UserIdTokenValidationService validationService;

    /**
     * This service is for retrieve all the activity logs for the logged in user
     * input is user login token
     * output -- returns list of activity logs
     */


    @RequestMapping(value = SportsBookConstants.GET_ACTIVITY_LOG, method = RequestMethod.GET)
    public SportsBookOutput getActivityListDetails(String userToken) {
        try {
            if (StringUtils.isEmpty(userToken) || StringUtils.containsWhitespace(userToken)) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            int status = sportsBookService.getId(userToken);
            if (status == 0) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            } else {
                UserActivityWrapperTO userActivityWrapperTO = myAccountService.getActivityLogs(userToken);
                return outputGenerator.getSuccessResponse(userActivityWrapperTO);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_ACTIVITY_LOG);
        }
    }

    /*
       @returns List<AccountStatementTO>
       @input String userToken
    */
    @RequestMapping(value = SportsBookConstants.GET_ACCOUNT_STATEMENT, method = RequestMethod.GET)
    public SportsBookOutput getAccountStatementDetails(String userToken,Integer userId) {
        try {
            if (!StringUtils.isEmpty(userToken) && !StringUtils.containsWhitespace(userToken)) {
                boolean isValidManager = validationService.validateUser(userToken, null).isValidManagerToken();
                if (isValidManager) {
                    List<AccountStatementTO> accountStatement = myAccountService.getAccountStatement(userToken,userId);
                    return outputGenerator.getSuccessResponse(accountStatement);
                } else {
                    ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                    errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    return outputGenerator.getSuccessResponse(errorMessageDetails);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_ACCOUNT_STATEMENT_SERVICE_NAME);
        }
    }

    /**
     * @param userToken
     * @param userId
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.GET_ACCOUNT_SUMMARY, method = RequestMethod.GET)
    public SportsBookOutput getAccountSummaryDetails(String userToken, Integer userId) {
        try {
            LOGGER.info("REST API for 'getAccountSummaryDetails' started; Input -> userToken:" + userToken + " userId: " + userId);
            if (!StringUtils.isEmpty(userToken) && userId != null) {
                AccountSummaryTO accountSummary;
                UserIdTokenValidationsTO userIdTokenValidations = validationService.validateUser(userToken, userId);
                boolean isValidUser = userIdTokenValidations.isValidManagerToken() &&
                        (userIdTokenValidations.isValidManagerId() || userIdTokenValidations.isValidPlayerId());
                if (isValidUser) {
                    accountSummary = myAccountService.getAccountSummaryDetails(userToken, userId);
                    LOGGER.info("REST API for 'getAccountSummaryDetails' completed successfully");
                    return outputGenerator.getSuccessResponse(accountSummary);
                } else {
                    ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                    errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    LOGGER.info("REST API for 'getAccountSummaryDetails' completed successfully");
                    return outputGenerator.getSuccessResponse(errorMessageDetails);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_ACCOUNT_SUMMARY_SERVICE_NAME);
        }
    }

    /*
       @returns SportsBookOutput
       @input BettingHistoryDetailsInput
    */
    @RequestMapping(value = SportsBookConstants.GET_BETTING_HISTORY_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getBettingHistoryDetails(@RequestBody @Valid @NotEmpty BettingHistoryDetailsInput bettingHistoryDetails) {
        try {
            LOGGER.debug("REST API for 'getBettingHistoryDetails' started; Input: " + bettingHistoryDetails);
            UserIdTokenValidationsTO userIdTokenValidations
                    = validationService.validateUser(bettingHistoryDetails.getUserToken(), bettingHistoryDetails.getPlayerUserId());
            if (userIdTokenValidations.isValidManagerToken() && userIdTokenValidations.isValidPlayerId()) {
                List<BettingHistoryDetailsOutput> bettingHistoryDetailsOutput = myAccountService.getBettingHistoryDetails(bettingHistoryDetails);
                LOGGER.debug("REST API for 'getBettingHistoryDetails' completed successfully ");
                return outputGenerator.getSuccessResponse(bettingHistoryDetailsOutput);
            } else {
                ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                return outputGenerator.getSuccessResponse(errorMessageDetails);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_BETTING_HISTORY_DETAILS_SERVICE_NAME);
        }
    }

    /*
       @returns SportsBookOutput
       @input BettingHistoryDetailsInput
    */
    @RequestMapping(value = SportsBookConstants.GET_BETTING_PROFIT_LOSS_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getBettingProfitLossDetails(@RequestBody @Valid @NotEmpty BettingHistoryDetailsInput bettingHistoryDetails) {
        try {
            LOGGER.debug("REST API for 'getBettingProfitLossDetails' started; Input: " + bettingHistoryDetails);
            UserIdTokenValidationsTO userIdTokenValidations
                    = validationService.validateUser(bettingHistoryDetails.getUserToken(), bettingHistoryDetails.getPlayerUserId());
            if (userIdTokenValidations.isValidManagerToken() && userIdTokenValidations.isValidPlayerId()) {
                TotalProfitLossWrapperTO bettingHistoryDetailsOutput = myAccountService.getBettingProfitLossDetails(bettingHistoryDetails);
                LOGGER.debug("REST API for 'getBettingProfitLossDetails' completed successfully ");
                return outputGenerator.getSuccessResponse(bettingHistoryDetailsOutput);
            } else {
                ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                return outputGenerator.getSuccessResponse(errorMessageDetails);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_BETTING_HISTORY_DETAILS_SERVICE_NAME);
        }
    }

    @RequestMapping(value = SportsBookConstants.UPDATE_PASSWORD, method = RequestMethod.POST)
    public SportsBookOutput updateAdminPassword(@RequestBody @Valid @NotNull UpdatePasswordDetailsTO updatePassword) {
        try {
            String response = null;
            if (!StringUtils.isEmpty(updatePassword.getLoginToken())) {
                LOGGER.info("userToken -" + updatePassword.getLoginToken());
                Boolean isValid = sportsBookService.validateUserToken(updatePassword.getLoginToken());
                if (isValid) {
                    response = myAccountService.updatePasswordDetails(updatePassword);
                } else {
                    response = SportsBookConstants.PASSWORD_NOT_MATCHED;
                }
            } else {
                return outputGenerator.getSuccessResponse(SportsBookConstants.USER_TOKEN_NOT_NULL);
            }
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_PASSWORD);
        }
    }

    @RequestMapping(value = SportsBookConstants.UPDATE_EXPOSURE, method = RequestMethod.POST)
    public SportsBookOutput updateExposure(@RequestBody @Valid @NotNull UpdateExposureTO exposureAmount) {
        try {
            String response = myAccountService.updateExposureDetails(exposureAmount);
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_EXPOSURE);
        }
    }

    @RequestMapping(value = SportsBookConstants.UPDATE_COMMISSION, method = RequestMethod.POST)
    public SportsBookOutput updateCommission(@RequestBody @Valid @NotNull UpdateExposureTO commissionAmount) {
        try {
            String response = myAccountService.updateCommissionDetails(commissionAmount);
            return outputGenerator.getSuccessResponse(response);
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_COMMISSION);
        }
    }
}
