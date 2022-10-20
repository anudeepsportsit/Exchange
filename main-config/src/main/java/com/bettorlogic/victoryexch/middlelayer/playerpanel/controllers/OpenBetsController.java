package com.bettorlogic.victoryexch.middlelayer.playerpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentValidations;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.ErrorMessageDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.*;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.service.OpenBetsService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.utils.OpenBetsConstants;
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
import java.util.List;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + SportsBookConstants.OPEN_BETS)
public class OpenBetsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenBetsController.class);
    @Autowired
    private UserIdTokenValidationService userIdTokenValidationService;
    @Autowired
    private SportsBookOutputGenerator outputGenerator;
    @Autowired
    private OpenBetsService openBetsService;
    @Autowired
    private SportsBookService sportsBookService;

    @RequestMapping(value = OpenBetsConstants.GET_OPEN_BET_DETAILS_URL, method = RequestMethod.GET)
    public SportsBookOutput getOpenBetDetails(String userToken) {
        try {
            LOGGER.info("REST API for 'getOpenBetDetails' started; Input: userToken -> " + userToken);
            if (!StringUtils.isEmpty(userToken)) {
                UserIdTokenValidationsTO userIdTokenValidation = userIdTokenValidationService.validateUser(userToken, null);
                if (userIdTokenValidation.isValidPlayerToken()) {
                    List<OpenBetsEventWrapperTO> openBetsEventWrapperList = openBetsService.getOpenBetDetails(userToken);
                    LOGGER.info("REST API for 'getOpenBetDetails' completed successfully");
                    return outputGenerator.getSuccessResponse(openBetsEventWrapperList);
                } else {
                    ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                    errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    return outputGenerator.getSuccessResponse(errorMessageDetails);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, OpenBetsConstants.GET_OPEN_BET_DETAILS_SERVICE);
        }
    }

    @RequestMapping(value = OpenBetsConstants.GET_AGENT_OPEN_BET_DETAILS_URL, method = RequestMethod.GET)
    public SportsBookOutput getAgentOpenBetDetails(String token) {
        try {
            LOGGER.info("REST API for 'getOpenBetDetails' started; Input: userToken -> " + token);
            if (!StringUtils.isEmpty(token)) {
                AgentValidations agent = userIdTokenValidationService.validateAgent(token);
                if (agent != null) {
                    List<OpenBetsEventWrapperTO> openBetsEventWrapperList = openBetsService.getAgentOpenBetDetails(agent.getMarketName());
                    LOGGER.info("REST API for 'getOpenBetDetails' completed successfully");
                    return outputGenerator.getSuccessResponse(openBetsEventWrapperList);
                } else {
                    ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                    errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    return outputGenerator.getSuccessResponse(errorMessageDetails);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, OpenBetsConstants.GET_AGENT_OPEN_BET_DETAILS_URL);
        }
    }

    @RequestMapping(value = OpenBetsConstants.UPDATE_OPEN_BET_DETAILS_URL, method = RequestMethod.POST)
    public SportsBookOutput updateOpenBetDetails(@RequestBody @Valid @NotEmpty BetDetailsSubWrapperTO betDetailsSubWrapper) {
        try {
            LOGGER.info("Update open bet input -- "+betDetailsSubWrapper);
            int userId = openBetsService.getId(betDetailsSubWrapper.getUserToken());
            if (userId == 0) {
                LOGGER.warn(SportsBookConstants.BetSlipConstants.INVALID_USER);
                throw new Exception(SportsBookConstants.BetSlipConstants.INVALID_USER);
            }
            Double exposureLimit = openBetsService.getExposureLimit(userId);
            if(betDetailsSubWrapper.getBackBets().size() > 0) {
                Double exposure = Double.valueOf(betDetailsSubWrapper.getBackBets().get(0).getReturns());
                if(exposureLimit != null) {
                    if (exposure.compareTo(exposureLimit) == 1) {
                        throw new Exception(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE);
                    }
                }
            }else if(betDetailsSubWrapper.getLayBets().size() > 0){
                Double exposure = Double.valueOf(betDetailsSubWrapper.getLayBets().get(0).getReturns());
                if(exposureLimit != null) {
                    if (exposure.compareTo(exposureLimit) == 1) {
                        throw new Exception(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE);
                    }
                }
            }


            LOGGER.info("REST API for 'updateOpenBetDetails' started; Input: userToken -> " + betDetailsSubWrapper);
            List<UpdateOpenBetsOutput> openBetsEventWrapperList = openBetsService.updateOpenBetDetails(betDetailsSubWrapper,userId);
            LOGGER.info("REST API for 'updateOpenBetDetails' completed successfully");
            return outputGenerator.getSuccessResponse(openBetsEventWrapperList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, OpenBetsConstants.UPDATE_OPEN_BET_DETAILS_SERVICE);
        }
    }

    @RequestMapping(value = OpenBetsConstants.CANCEL_OPEN_BETS_URL, method = RequestMethod.POST)
    public SportsBookOutput cancelOpenBets(@RequestBody @Valid @NotEmpty CancelOpenBetsInput cancelOpenBetsInput) {
        try {
            LOGGER.info("REST API for 'cancelOpenBets' started; Input: userToken -> " + cancelOpenBetsInput.getUserToken(), cancelOpenBetsInput.getBetIdList());
            CancelOpenBetsOutput cancelOpenBetsOutput = openBetsService.cancelOpenBets(cancelOpenBetsInput.getUserToken(), cancelOpenBetsInput.getBetIdList());
            LOGGER.info("REST API for 'cancelOpenBets' completed successfully");
            return outputGenerator.getSuccessResponse(cancelOpenBetsOutput);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, OpenBetsConstants.CANCEL_OPEN_BETS_SERVICE);
        }
    }
}