package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;


import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.ErrorMessageDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.Timezones;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service.DownlineListService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service.impl.DownlineListServiceImpl;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dao.MyAccountDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + DownLineListConstants.DOWN_LINE_LIST)
public class DownlineListController {
    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private DownlineListService downlineListService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SportsBookService sportsBookService;

    @Autowired
    private MyAccountDao myAccountDao;

    @Autowired
    private UserIdTokenValidationService userIdTokenValidationService;

    @Autowired
    private DownlineListServiceImpl downlineListServiceImpl;

    @RequestMapping(value = DownLineListConstants.DOWN_LINE_LIST_URL, method = RequestMethod.GET)
    public SportsBookOutput getDownlineListDetails(String userToken, Integer userId) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                UserIdTokenValidationsTO userIdTokenValidations = userIdTokenValidationService.validateUser(userToken, userId);
                boolean isValidUser = userIdTokenValidations.isValidManagerToken() &&
                        (userId == null || userIdTokenValidations.isValidManagerId());
                if (isValidUser) {
                    DownlineListDetailsWrapperTO downlineListDetails = downlineListService.getDownLineListDetails(userToken, userId);
                    return outputGenerator.getSuccessResponse(downlineListDetails);
                } else {
                    ErrorMessageDetailsTO errorMessageDetails = new ErrorMessageDetailsTO();
                    errorMessageDetails.setErrorMessage(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    return outputGenerator.getSuccessResponse(errorMessageDetails);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.GET_DOWNLINE_LIST_DETAILS_SERVICE_NAME);
        }
    }


    @RequestMapping(value = DownLineListConstants.UPDATE_CREDIT_LIMIT, method = RequestMethod.POST)
    public SportsBookOutput updatecredit(@RequestBody CreditRequestTO creditRequestTO) {
        try {
            String currentPassword = myAccountDao.getUserPassword(creditRequestTO.getLoginToken());
            boolean isPasswordMatch = passwordEncoder.matches(creditRequestTO.getPassword(), currentPassword);
            if(isPasswordMatch){
                    String result = downlineListService.updateCreditLimit(creditRequestTO);
                    return outputGenerator.getSuccessResponse(result);
            } else {
                throw new Exception(ExceptionConstants.INVALID_PASSWORD);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.UPDATE_CREDIT_LIMIT);
        }
    }



    @RequestMapping(value = DownLineListConstants.ADD_USER, method = RequestMethod.POST)
    public SportsBookOutput createUser(@RequestBody @NotNull @Valid UserDetailsTO userDetails) {
        try {
            Map<String, Object> result = new HashMap<>();
            if (StringUtils.isEmpty(userDetails.getUserToken()) || StringUtils.containsWhitespace(userDetails.getUserToken())) {
                result.put(DownLineListConstants.IS_VALID_TOKEN, true);
            }else{
                result = downlineListService.addUser(userDetails);
            }
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.ADD_USER);
        }
    }

    @RequestMapping(value = DownLineListConstants.CHANGE_STATUS, method = RequestMethod.POST)
    public SportsBookOutput updateStatus(@RequestBody @NotNull @Valid ChangeStatusTO changeStatusTO) {
        try {
            boolean isValidPassword;
            boolean isCreated = false;
            Map<String, Object> result = new HashMap<>();
            String dbUserPassword = downlineListService.getDbPassword(changeStatusTO.getUserToken());
            UserBetDetailsTO userBetDetailsTO = sportsBookService.checkValidation(changeStatusTO.getUserToken());
            isValidPassword = passwordEncoder.matches(changeStatusTO.getUserPassword(), dbUserPassword);

            if(changeStatusTO.getStatusCode() == 1){
                boolean isAdmin = downlineListService.getstatusChangedBy(changeStatusTO.getUserId());
                if(isAdmin && userBetDetailsTO.getRoleId() == 1){
                    result = downlineListService.changeStatus(changeStatusTO,userBetDetailsTO);
                    return outputGenerator.getSuccessResponse(result);
                }else if(isAdmin && userBetDetailsTO.getRoleId() != 1){
                    result.put(DownLineListConstants.USER_UPDATED_KEY, false);
                    return outputGenerator.getSuccessResponse(result);
                }
            }else if(changeStatusTO.getStatusCode() == 3 || changeStatusTO.getStatusCode() == 2){
                String user = downlineListService.getUserName(changeStatusTO.getUserId());
                if(user.equalsIgnoreCase("master@default.com") || user.equalsIgnoreCase("super@default.com")){
                    result.put(DownLineListConstants.USER_UPDATED_KEY, false);
                    return outputGenerator.getSuccessResponse(result);
                }
            }

            if (isValidPassword) {
                result = downlineListService.changeStatus(changeStatusTO,userBetDetailsTO);
            } else {
                result.put(DownLineListConstants.IS_VALID_PASSWORD, false);
            }
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.CHANGE_STATUS);
        }
    }

    @RequestMapping(value = DownLineListConstants.BET_LIST_LIVE_URL, method = RequestMethod.GET)
    public SportsBookOutput getBetLiveList(String userToken) {
        try {
            List<BetListLiveTO> betListLiveTO = downlineListService.getBetListLive(userToken);
            return outputGenerator.getSuccessResponse(betListLiveTO);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.BET_LIST_LIVE_URL);
        }


    }

    @RequestMapping(value = DownLineListConstants.BET_LIST_LIVE_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getBetLiveListDetails(@RequestBody @NotNull @Valid BetListLiveDetailsInputTO betListLiveDetailsInput) {
        try {
            BetListLiveWrapperTO betListLiveDetailsTO = downlineListService.getBetListLiveDetails(betListLiveDetailsInput);
            return outputGenerator.getSuccessResponse(betListLiveDetailsTO);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, DownLineListConstants.BET_LIST_LIVE_DETAILS);
        }
    }






}