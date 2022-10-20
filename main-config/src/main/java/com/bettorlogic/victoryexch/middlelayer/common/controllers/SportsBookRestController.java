package com.bettorlogic.victoryexch.middlelayer.common.controllers;


import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.FancyEventsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.FancyLeaguesTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service.FancyMarketService;
import com.bettorlogic.victoryexch.middlelayer.common.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.BalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportHierarchyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ClearedOrderSummaryReportTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.OrderDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.OrdersResponseTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.service.OpenBetsService;
import io.swagger.models.auth.In;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class SportsBookRestController {

    private static final Logger LOGGER = LogManager.getLogger(SportsBookRestController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private SportsBookService sportsBookService;

    @Autowired
    private FancyMarketService fancyMarketService;

    @Autowired
    private OpenBetsService openBetsService;

    @Autowired
    private UserIdTokenValidationService userIdTokenValidationService;

    private boolean isValidEmailId(String emailId) {
        if (emailId != null) {
            return emailId.trim().length() != 0;
        }
        return false;
    }

    @RequestMapping(value = SportsBookConstants.ADD_ADMIN_LEAGUES, method = RequestMethod.POST)
    public SportsBookOutput addAdminLeagues(@RequestBody @NotNull @Valid LeaguesTO leaguesDetails) {
        try {
            Map<String, Object> result = sportsBookService.addLeagues(leaguesDetails);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.ADD_ADMIN_LEAGUES);
        }
    }

    @RequestMapping(value = SportsBookConstants.SAVE_PIN_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput savePinDetails(@RequestBody @Valid @NotNull FavouriteEventDetailsTO favouriteTeamDetails) {
        try {
            FavouriteEventDetailsTO favouriteEventDetailsOutput = sportsBookService.saveFavouriteEventDetails(favouriteTeamDetails);
            return outputGenerator.getSuccessResponse(favouriteEventDetailsOutput);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.SAVE_PIN_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.SAVE_MAKRET_PROFIT_LOSS_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput saveProfitLossDetails(@RequestBody @Valid @NotNull MarketProfitLossWrapperTO marketProfitLossWrapper) {
        try {
            boolean isValidPlayer = userIdTokenValidationService.validateUser(marketProfitLossWrapper.getUserToken(), null).isValidPlayerToken();
            if (isValidPlayer) {
                boolean marketProfitLossDetails =
                        sportsBookService
                                .saveProfitLossDetails(marketProfitLossWrapper.getUserToken(), marketProfitLossWrapper.getMarketProfitLossList());
                SaveDetailsTO saveDetails = new SaveDetailsTO();
                saveDetails.setDetailsUpdated(marketProfitLossDetails);
                return outputGenerator.getSuccessResponse(saveDetails);
            } else {
                throw new Exception(ExceptionConstants.INVALID_USER);
            }
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.SAVE_PIN_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_MAKRET_PROFIT_LOSS_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getProfitLossDetails(@RequestParam @Valid @NotEmpty String userToken) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                boolean isValidPlayer = userIdTokenValidationService.validateUser(userToken, null).isValidPlayerToken();
                if (isValidPlayer) {
                    List<MarketProfitLossTO> marketProfitLossDetails = sportsBookService.getProfitLossDetails(userToken);
                    MarketProfitLossWrapperTO marketProfitLossWrapperOutput = new MarketProfitLossWrapperTO();
                    marketProfitLossWrapperOutput.setMarketProfitLossList(marketProfitLossDetails);
                    return outputGenerator.getSuccessResponse(marketProfitLossWrapperOutput);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_USER);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }

        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_MAKRET_PROFIT_LOSS_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_AGENT_USERS_PROFIT_LOSS_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getAgentUsersProfitLossDetails() {
        try {
            //if (!StringUtils.isEmpty(userToken)) {
              //  boolean isValidPlayer = userIdTokenValidationService.validateUser(userToken, null).isValidPlayerToken();
               // if (isValidPlayer) {
                    List<MarketProfitLossTO> marketProfitLossDetails =
                            sportsBookService
                                    .getAdminProfitLossDetails();
                    MarketProfitLossWrapperTO marketProfitLossWrapperOutput = new MarketProfitLossWrapperTO();
                    marketProfitLossWrapperOutput.setMarketProfitLossList(marketProfitLossDetails);
                    return outputGenerator.getSuccessResponse(marketProfitLossWrapperOutput);
              /*  } else {
                    throw new Exception(ExceptionConstants.INVALID_USER);
                }*/
            /*} else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }*/
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_AGENT_USERS_PROFIT_LOSS_DETAILS);
        }
    }




    @RequestMapping(value = SportsBookConstants.GET_FANCY_LEAGUES, method = RequestMethod.GET)
    public SportsBookOutput getLeaguesList(Integer sportId) {
        try {
            FancyLeaguesTO fancyLeaguesTO = fancyMarketService.getLeaguesList(sportId);
            return outputGenerator.getSuccessResponse(fancyLeaguesTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_LEAGUES);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_FANCY_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getEventsList(Integer leagueId) {
        try {
            FancyEventsTO fancyEventsTO = fancyMarketService.getEventsList(leagueId);
            return outputGenerator.getSuccessResponse(fancyEventsTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FANCY_EVENTS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_USER_BALANCE, method = RequestMethod.GET)
    public SportsBookOutput getUserBalance(String loginToken) {
        try {
            LOGGER.info("Token  - " + loginToken);
            if (!StringUtils.isEmpty(loginToken)) {
                Boolean res = sportsBookService.validateUserToken(loginToken);
                if (res) {
                    BalanceDetailsTO balanceDetailsTO = sportsBookService.getUserBalance(loginToken);
                    LOGGER.info("user details :" + balanceDetailsTO);
                    return outputGenerator.getSuccessResponse(balanceDetailsTO);
                } else {
                    LOGGER.warn("Token Not Valid or Expired");
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.GET_USER_BALANCE);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_EVENT_VIEW_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getEventViewDetails(Integer eventId, String userToken, Integer sportId) {
        try {
            if (sportId != null && sportId == 3) {
                SportHierarchyDetailsTO sportDetails = sportsBookService.getCommonEventDetails(sportId);
                return outputGenerator.getSuccessResponse(sportDetails);
            }
            if (StringUtils.isEmpty(userToken) || StringUtils.containsWhitespace(userToken)) {
                userToken = null;
            }
            if (eventId != null && eventId > 0) {
                EventViewDetailsWrapperTO eventViewDetailsWrapper = sportsBookService.getEventViewDetails(eventId, userToken);
                return outputGenerator.getSuccessResponse(eventViewDetailsWrapper);
            } else {
                throw new Exception(ExceptionConstants.INVALID_EVENT_ID_ENTERED);
            }
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_EVENT_VIEW_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.REMOVE_PIN_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput removePinDetails(@RequestBody @Valid @NotNull FavouriteEventDetailsTO favouriteTeamDetails) {
        try {
            FavouriteEventDetailsTO favouriteEventDetailsOutput = sportsBookService.deleteFavouriteEventDetails(favouriteTeamDetails);
            return outputGenerator.getSuccessResponse(favouriteEventDetailsOutput);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.REMOVE_PIN_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.EMAIL_ID_VALIDATION_CHECK, method = RequestMethod.GET)
    public SportsBookOutput checkForValidEmailId(@RequestParam @Valid @NotNull String emailId) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            if (this.isValidEmailId(emailId)) {
                boolean value = sportsBookService.userCredentialsValidation(emailId, null).isUniqueEmailId();
                if(value == true){
                    result.put(SportsBookConstants.USER_DUPLICATE_KEY, false);
                }else
                result.put(SportsBookConstants.USER_DUPLICATE_KEY, true);
            } else {
                throw new Exception(ExceptionConstants.INVALID_EMAIL_ID_ENTERED);
            }
            return outputGenerator.getSuccessResponse(result);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.EMAIL_ID_VALIDATION_CHECK);
        }
    }

    @RequestMapping(value = SportsBookConstants.USER_NAME_VALIDATION_CHECK, method = RequestMethod.GET)
    public SportsBookOutput checkForValidUserName(@RequestParam @Valid @NotNull String userName) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            if (this.isValidEmailId(userName)) {
                boolean value = sportsBookService.userCredentialsValidation(null, userName).isUniqueUserName();
                if(value == true){
                    result.put(SportsBookConstants.USER_DUPLICATE_KEY, false);
                }else
                    result.put(SportsBookConstants.USER_DUPLICATE_KEY, true);
            } else {
                throw new Exception(ExceptionConstants.INVALID_USER_NAME_ENTERED);
            }
            return outputGenerator.getSuccessResponse(result);
        } catch (
                Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.USER_NAME_VALIDATION_CHECK);
        }
    }

    private void validatePhoneNumber(UserRegistrationDetailsTO input) throws Exception {
        if (!StringUtils.isEmpty(input.getPhoneNumber()) && !input.getPhoneNumber().contains(SportsBookConstants.PLUS_SYMBOL)) {
            try {
                input.setPhoneNumber(Long.valueOf(input.getPhoneNumber()).toString());
                if (input.getPhoneNumber().length() < 8) {
                    throw new Exception(ExceptionConstants.INVALID_PHONE_NUMBER_ENTERED);
                }
            } catch (NumberFormatException ex) {
                throw new Exception(ExceptionConstants.INVALID_PHONE_NUMBER_ENTERED);
            }
        }
    }

    @RequestMapping(value = SportsBookConstants.USER_SIGN_UP, method = RequestMethod.POST)
    public SportsBookOutput processUserSignUp(@RequestBody @Valid @NotNull UserRegistrationDetailsTO input) {
        try {
            if(input.getUserName().length()<8){
                return outputGenerator.getSuccessResponse(ExceptionConstants.INVALID_USER_NAME_LENGTH);
            }else {
                Boolean isValidEmail = sportsBookService.isValidEmail(input.getEmailId());
                this.validatePhoneNumber(input);

                if (isValidEmail) {
                    UserRegistrationOutput result = sportsBookService.processUserRegistration(input);
                    sportsBookService.getUserId(input.getEmailId());
                    return outputGenerator.getSuccessResponse(result);
                } else {
                    return outputGenerator.getSuccessResponse(ExceptionConstants.INVALID_EMAIL_ID_ENTERED);
                }
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.USER_SIGN_UP);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_FOOTER_CONTROLS, method = RequestMethod.GET)
    public SportsBookOutput getFooterControls() {
        try {
            FooterControlsWrapperTO result = sportsBookService.getFooterControls();
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_FOOTER_CONTROLS);
        }
    }

    @RequestMapping(value = SportsBookConstants.USER_LOGIN, method = RequestMethod.POST)
    public SportsBookOutput getUserLogin(@RequestBody @Valid @NotNull UserLoginDetailsTO input) {

        Integer adminAgentRole=sportsBookService.getRoleId(input.getEmailId());
        try {
            LOGGER.info("input:" + input);

            //Integer statusId = sportsBookService.getStatus(input.getEmailId());

            //if(statusId == 1){

            if(adminAgentRole==6){
                Map<String, Object> result = sportsBookService.agentLoginValidation(input);
                String status = (String) result.get("status");
                if(status.equalsIgnoreCase("Suspended")){
                    LOGGER.warn("unauthorized");
                    throw new Exception(ExceptionConstants.SUSPEND);
                }
                Integer agentId = (Integer) result.get("agentId");
                sportsBookService.unSuspendMarkets(agentId);
                return  outputGenerator.getSuccessResponse(result);
            }
            int emailIdCount = sportsBookService.getEmailIdCount(input.getEmailId());
            if (emailIdCount != 0) {
                Boolean emailVerified = sportsBookService.authenticateUser(input.getEmailId());
                if (emailVerified) {
                    Map<String, Object> result = sportsBookService.userLoginValidation(input);
                    LOGGER.info("user details:" + result);
                    return outputGenerator.getSuccessResponse(result);
                } else {
                    LOGGER.warn("unauthorized");
                    throw new Exception(ExceptionConstants.UNAUTHORIZED);
                }
            } else {
                LOGGER.warn("Invalid email-id");
                throw new Exception(ExceptionConstants.INVALID_EMAILID);
            }
            /*}else{
                LOGGER.warn("user is suspended");
                return outputGenerator.getSuccessResponse(ExceptionConstants.SUSPEND);
            }*/
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.USER_LOGIN);
        }
    }



    @RequestMapping(value = SportsBookConstants.CONFIRM_ACCOUNT, method = RequestMethod.GET)
    public SportsBookOutput confirmAccount(String token) {
        try {
            if (!StringUtils.isEmpty(token)) {
                EmailVerificationDetailsTo emailVerificationDetailsTo = sportsBookService.getEmailVerifiedStatus(token);
                return outputGenerator.getSuccessResponse(emailVerificationDetailsTo);
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.CONFIRM_ACCOUNT);
        }
    }


    private boolean isValidateOddTypeInBetSlip(List<BetDetailsTO> betDetailsList) {
        Predicate<BetDetailsTO> oddTypePredicate = betDetails ->
                betDetails.getOddType().equalsIgnoreCase(SportsBookConstants.ODD_TYPE_BACK) ||
                        betDetails.getOddType().equalsIgnoreCase(SportsBookConstants.ODD_TYPE_LAY);
        return betDetailsList.parallelStream().allMatch(oddTypePredicate);
    }

    @RequestMapping(value = SportsBookConstants.GET_BET_SLIP_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getBetSlipDetails(@RequestBody @Valid @NotNull BetSlipFetchDetailsWrapperTO input) {
        try {
            if (validateBetSlipFetchDetails(input)) {
                BetSlipFetchDetailsWrapperTO betSlipFetchDetailsWrapper = sportsBookService.getBetSlipDetails(input);
                return outputGenerator.getSuccessResponse(betSlipFetchDetailsWrapper);
            } else {
                throw new Exception(ExceptionConstants.INVALID_ODD_TYPE);
            }

        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_BET_SLIP_DETAILS);
        }
    }

    private boolean validateBetSlipFetchDetails(BetSlipFetchDetailsWrapperTO input) {
        Predicate<BetDetailsFetchTO> backBetDetailsFetch =
                betDetailsFetch -> betDetailsFetch.getOddType() != null &&
                        betDetailsFetch.getOddType().equalsIgnoreCase(SportsBookConstants.ODD_TYPE_BACK);
        Predicate<BetDetailsFetchTO> layBetDetailsFetch =
                betDetailsFetch -> betDetailsFetch.getOddType() != null &&
                        betDetailsFetch.getOddType().equalsIgnoreCase(SportsBookConstants.ODD_TYPE_LAY);
        return input.getBackBetsList().parallelStream().allMatch(backBetDetailsFetch) &&
                input.getLayBetsList().parallelStream().allMatch(layBetDetailsFetch);
    }

    @RequestMapping(value = SportsBookConstants.SAVE_BET_SLIP_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput saveBetSlipDetails(@RequestBody @Valid @NotNull BetSlipDetailsWrapperTO input) {
        Date currentTime = new Date();
        try {
            LOGGER.info("betslip input:" + input);
            currentTime = new Date();
            LOGGER.info("BetSlip started and validating bet starts at ---- :" +currentTime);
            UserBetDetailsTO userBetDetailsTO = sportsBookService.checkValidation(input.getUserToken());
            currentTime = new Date();
            LOGGER.info("BetSlip Validation for user ID : "+userBetDetailsTO.getUserId()+ " ends at ---- :" +currentTime);

            Double exposureLimit = openBetsService.getExposureLimit(userBetDetailsTO.getUserId());

            BigDecimal userProfit = sportsBookService.getProfit(input.getBetDetailsList().get(0), userBetDetailsTO.getUserId());
            Double profit = userProfit.doubleValue();
            if(exposureLimit != null) {
                if (profit.compareTo(exposureLimit) == 1) {
                    throw new Exception(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE);
                }
            }

            if(userBetDetailsTO.getRoleId() != null) {
                if (userBetDetailsTO.getRoleId() != 4) {
                    LOGGER.warn(SportsBookConstants.BetSlipConstants.USER_NOT_PLAYER);
                    throw new Exception(SportsBookConstants.BetSlipConstants.USER_NOT_PLAYER);
                }
                if (userBetDetailsTO.getStatusId() != 1) {
                    LOGGER.warn(SportsBookConstants.BetSlipConstants.SUSPENDED_LOCKED_USER);
                    throw new Exception(SportsBookConstants.BetSlipConstants.SUSPENDED_LOCKED_USER);
                }
                UserBalanceDetailsTO userBalanceDetails = sportsBookService.saveBetSlipDetailsSuccessResponse1(input,userBetDetailsTO.getUserId(),userBetDetailsTO.getExposureLimit(),userBetDetailsTO.getSettledBalance());

                currentTime = new Date();
                LOGGER.info("BetSlip Service for user ID : "+userBetDetailsTO.getUserId()+ "ends at ---- :" +currentTime);
                return outputGenerator.getSuccessResponse(userBalanceDetails);
            }else{
                LOGGER.warn(SportsBookConstants.BetSlipConstants.INVALID_USER);
                throw new Exception(SportsBookConstants.BetSlipConstants.INVALID_USER);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.SAVE_BET_SLIP_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_EZUGI_TOKEN, method = RequestMethod.GET)
    public SportsBookOutput getEzugiToken(String userToken) {
               try {
                       LOGGER.info("input:" + userToken);

                                       Map<String, Object> result = sportsBookService.getEzugiToken(userToken);
                               LOGGER.info("Ezugi Token service called:" + result);
                               return outputGenerator.getSuccessResponse(result);

                            } catch (Exception ex) {
                       return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_EZUGI_TOKEN);
                   }
           }
    @RequestMapping(value = SportsBookConstants.GET_BET_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getBetStatusList(@RequestBody OrderDetailsTO orderDetailsTO) {
        try {
            List<OrdersResponseTO> ordersResponseTO = sportsBookService.getBetDetails(orderDetailsTO);
            return outputGenerator.getSuccessResponse(ordersResponseTO);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_BET_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_SETTLED_BET_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput getSettledBetList(@RequestBody OrderDetailsTO orderDetailsTO) {
        try {
            BetfairServerResponse<ClearedOrderSummaryReportTO> ordersResponseTO = sportsBookService.getSettledDetails(orderDetailsTO);
            if (ordersResponseTO.getResponse() != null) {
                return outputGenerator.getSuccessResponse(ordersResponseTO.getResponse().getClearedOrders());
            } else {
                return outputGenerator.getSuccessResponse(SportsBookConstants.INVALID_SESSION);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.GET_SETTLED_BET_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.USER_LOGOUT, method = RequestMethod.POST)
    public SportsBookOutput processLogout(@RequestBody @Valid @NotNull UserLogoutDetailsTO logoutDetails) {
        try {
            if(logoutDetails.getAgentId() != null){
                sportsBookService.suspendMarkets(logoutDetails.getAgentId(),logoutDetails.getLoginToken());
            }
            LOGGER.info("Token -" + logoutDetails.getLoginToken());
            if(logoutDetails.getAgentId() != null){
                sportsBookService.suspendMarkets(logoutDetails.getAgentId(), logoutDetails.getLoginToken() );
            }
            boolean userLogoutStatus = sportsBookService.userLogout(logoutDetails.getLoginToken());
            Map<String, Object> result = new HashMap<>();
            result.put(SportsBookConstants.USER_LOGOUT_STATUS, userLogoutStatus);
            LOGGER.info("User Logout status : " + result);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.USER_LOGOUT);
        }
    }

    @RequestMapping(value = SportsBookConstants.SUB_HEADER, method = RequestMethod.GET)
    public SportsBookOutput getSubHeaders() {
        try {
            SubHeaderDetailsWrapperTO subHeaderDetailsWrapper = sportsBookService.retrieveSubHeaders();
            return outputGenerator.getSuccessResponse(subHeaderDetailsWrapper);
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.SUB_HEADER);
        }
    }

    /**
     * Get One click coin Details
     *
     * @param userToken User Token
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.GET_ONE_CLICK_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getOneClickCoinDetails(String userToken) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                LOGGER.info("userToken -" + userToken);
                Boolean res = sportsBookService.validateUserToken(userToken);
                if (res) {
                    OneClickCoinWrapperTO oneClickCoinWrapperTO = sportsBookService.getOneClickCoin(userToken);
                    LOGGER.info("coin List" + oneClickCoinWrapperTO);
                    return outputGenerator.getSuccessResponse(oneClickCoinWrapperTO);
                } else {
                    LOGGER.warn("User Id does not exist or Expired");
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }
            } else {
                LOGGER.warn("Session Expired");
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }

        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.GET_ONE_CLICK_DETAILS);
        }
    }

    /**
     * Get Coin Settings
     *
     * @param userToken User Token
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.GET_USER_COIN, method = RequestMethod.GET)
    public SportsBookOutput getCoinSettings(String userToken) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                LOGGER.info("userToken -" + userToken);
                Boolean res = sportsBookService.validateUserToken(userToken);
                if (res) {
                    UserCoinDetailsTO userCoinDetailsTO = sportsBookService.getUserCoins(userToken);
                    LOGGER.info("coin List - " + userCoinDetailsTO);
                    return outputGenerator.getSuccessResponse(userCoinDetailsTO);
                } else {
                    LOGGER.warn("User Id does not exist or Expired");
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }

            } else {
                LOGGER.warn("Session Expired");
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }

        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.GET_USER_COIN);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_USER_PROFILE, method = RequestMethod.GET)
    public SportsBookOutput getUserProfile(String userToken) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                LOGGER.info("userToken -" + userToken);
                Boolean res = sportsBookService.validateUserToken(userToken);
                if (res) {
                    UserProfileDetailsWrapperTO userProfileDetails = sportsBookService.getUserProfile(userToken);
                    LOGGER.info("user profile details succesfully retrived " + userProfileDetails);
                    return outputGenerator.getSuccessResponse(userProfileDetails);
                } else {
                    LOGGER.warn("User Id does not exist or Expired");
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }
            } else {
                LOGGER.warn("Session Expired");
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.GET_USER_PROFILE);
        }
    }

    @RequestMapping(value = SportsBookConstants.SAVE_USER_TIMEZONE, method = RequestMethod.GET)
    public SportsBookOutput saveTimeZone(Integer timeZoneId,String userToken){
        try{
            boolean result = sportsBookService.saveTime(timeZoneId,userToken);

            return outputGenerator.getSuccessResponse(result);
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e, SportsBookConstants.SAVE_USER_TIMEZONE);
        }
    }


    @RequestMapping(value = SportsBookConstants.UPDATE_USER_PASSWORD, method = RequestMethod.POST)
    public SportsBookOutput updateUserPassword(@RequestBody @Valid @NotNull UserPasswordUpdateDetailsTO password) {
        try {
            if(password.getIsUser().equalsIgnoreCase("false")){
                Integer roleId = sportsBookService.getLoginUserRoleId(password.getUserToken());
                if(roleId == 0) {
                    throw new Exception(ExceptionConstants.WRONG_TOKEN);
                }else{
                    SportsBookConstants.PasswordMessages changePassword = sportsBookService.updatePasswordByAdmin(password,roleId);
                    LOGGER.info(changePassword);
                    PasswordDetailsTo passwordDetailsTo;
                    if (changePassword.equals(SportsBookConstants.PasswordMessages.PASSWORD_UPDATE)) {
                        passwordDetailsTo = new PasswordDetailsTo();
                        passwordDetailsTo.setUpdated(true);
                        passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_UPDATE);
                        return outputGenerator.getSuccessResponse(passwordDetailsTo);
                    }else if (changePassword.equals(SportsBookConstants.PasswordMessages.PASSWORD_MATCH)) {
                        passwordDetailsTo = new PasswordDetailsTo();
                        passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_MATCH);
                        passwordDetailsTo.setUpdated(false);
                        return outputGenerator.getSuccessResponse(passwordDetailsTo);
                    } else {
                        passwordDetailsTo = new PasswordDetailsTo();
                        passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_NOT_MATCHED);
                        passwordDetailsTo.setUpdated(false);
                        return outputGenerator.getSuccessResponse(passwordDetailsTo);
                    }
                }
            }else {
                if (!StringUtils.isEmpty(password.getUserToken())) {
                    LOGGER.info("userToken -" + password.getUserToken());
                    Integer roleId = sportsBookService.getLoginUserRoleId(password.getUserToken());
                    Boolean isValid = sportsBookService.validateUserToken(password.getUserToken());
                    if(roleId == 6){
                        sportsBookService.updateAgentPassword(password);
                        SportsBookConstants.PasswordMessages passwordMessages = sportsBookService.updateAgentPasswordDetails(password);
                        PasswordDetailsTo passwordDetailsTo;
                        if (passwordMessages.equals(SportsBookConstants.PasswordMessages.PASSWORD_UPDATE)) {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setUpdated(true);
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_UPDATE);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        } else if (passwordMessages.equals(SportsBookConstants.PasswordMessages.PASSWORD_MATCH)) {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_MATCH);
                            passwordDetailsTo.setUpdated(false);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        } else {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_NOT_MATCHED);
                            passwordDetailsTo.setUpdated(false);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        }
                    }
                    if (isValid) {
                        SportsBookConstants.PasswordMessages passwordMessages = sportsBookService.updateUserPasswordDetails(password);
                        LOGGER.info(passwordMessages);
                        PasswordDetailsTo passwordDetailsTo;
                        if (passwordMessages.equals(SportsBookConstants.PasswordMessages.PASSWORD_UPDATE)) {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setUpdated(true);
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_UPDATE);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        } else if (passwordMessages.equals(SportsBookConstants.PasswordMessages.PASSWORD_MATCH)) {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_MATCH);
                            passwordDetailsTo.setUpdated(false);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        } else {
                            passwordDetailsTo = new PasswordDetailsTo();
                            passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_NOT_MATCHED);
                            passwordDetailsTo.setUpdated(false);
                            return outputGenerator.getSuccessResponse(passwordDetailsTo);
                        }
                    } else {
                        LOGGER.warn("User Id does not exist or Expired");
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                } else {
                    LOGGER.warn("Session Expired");
                    throw new Exception(ExceptionConstants.SESSION_EXPIRED);
                }
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_USER_PASSWORD);
        }
    }

    /**
     * Update Coin Setting
     *
     * @param userDetails User Token
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.UPDATE_USER_COIN, method = RequestMethod.POST)
    public SportsBookOutput updateCoinSettings(@RequestBody @Valid @NotNull UserCoinDetailsTO userDetails) {
        try {
            if (!StringUtils.isEmpty(userDetails.getUserToken())) {
                LOGGER.info("userToken -" + userDetails.getUserToken());
                Boolean hasDuplicate = sportsBookService.hasDuplicate(userDetails, SportsBookConstants.COIN_SIZE);
                if (!hasDuplicate) {
                    Boolean res = sportsBookService.updateUserCoins(userDetails);
                    if (res) {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.SUCCESSFULLY_UPDATED_COIN_SETTINGS);
                    } else {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                } else {
                    return outputGenerator.getSuccessResponse(SportsBookConstants.COINS_DUPLICATE);
                }

            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }

        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_USER_COIN);
        }
    }

    /**
     * Save Coin Settings
     *
     * @param userCoinDetailsTO User Coin Details
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.SAVE_USER_COIN_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput storeCoinSettings(@RequestBody @Valid @NotNull UserCoinDetailsTO userCoinDetailsTO) {
        try {
            if (!StringUtils.isEmpty(userCoinDetailsTO.getUserToken())) {
                Boolean res = sportsBookService.storeCoinSettingDetails(userCoinDetailsTO);
                if (res) {
                    return outputGenerator.getSuccessResponse(SportsBookConstants.SUCCESSFULLY_SAVED);
                } else {
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.SAVE_USER_COIN_DETAILS);
        }
    }


    /**
     * Update one click coin settings
     *
     * @param userCoinDetailsTO User Coin Details
     * @return SportsBookOutput
     */
    @RequestMapping(value = SportsBookConstants.UPDATE_USER_ONE_CLICK_COIN, method = RequestMethod.POST)
    public SportsBookOutput updateOneClickCoinSettings(@RequestBody UserCoinDetailsTO userCoinDetailsTO) {
        try {
            if (!StringUtils.isEmpty(userCoinDetailsTO.getUserToken())) {
                Boolean hasDuplicate = sportsBookService.hasDuplicate(userCoinDetailsTO, SportsBookConstants.ONE_CLICK_COINS_SIZE);
                if (!hasDuplicate) {
                    Boolean res = sportsBookService.updateOneClickCoins(userCoinDetailsTO);
                    if (res) {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.SUCCESSFULLY_UPDATED);
                    } else {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                } else {
                    return outputGenerator.getSuccessResponse(SportsBookConstants.COINS_DUPLICATE);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_USER_ONE_CLICK_COIN);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_USER_TRANSACTION_DETAILS, method = RequestMethod.GET)
    public SportsBookOutput getTransactionDetails(String userToken, String transactionType, String fromDate, String toDate, Integer pageNumber, Integer pageSize,Integer sportId) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                if (!StringUtils.isEmpty(transactionType) && !StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) {
                    Boolean res = sportsBookService.validateUserToken(userToken);
                    if (res) {
                        UserTransactionDetailsWrapperTO userTransactionDetailsWrapperTO = new UserTransactionDetailsWrapperTO();
                        userTransactionDetailsWrapperTO.setUserTransactionsCount(sportsBookService.getTransactionsCount(userToken, transactionType, fromDate, toDate));
                        userTransactionDetailsWrapperTO.setUserTransList(sportsBookService.getTransactionDetails(userToken, transactionType, fromDate, toDate, pageNumber, pageSize, sportId));
                        BalanceDetailsTO balanceDetails = sportsBookService.getUserBalance(userToken);
                        userTransactionDetailsWrapperTO.setUserBalance(BigDecimal.valueOf(balanceDetails.getUserBalance()));
                        return outputGenerator.getSuccessResponse(userTransactionDetailsWrapperTO);
                    } else {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                }else {
                    return outputGenerator.getSuccessResponse(ExceptionConstants.FIELDS_EMPTY);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.FAILED_GET_TRANS_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_USER_TRANSACTION_PROFIT_LOSS_VIEW, method = RequestMethod.POST)
    public SportsBookOutput getTransactionEventViewDetails(@RequestBody @Valid @NotNull TransactionDetailsInputTO transactionDetails) {
        try {
            if (!StringUtils.isEmpty(transactionDetails.getUserToken())){
                if (!StringUtils.isEmpty(transactionDetails.getTransactionType()) && !StringUtils.isEmpty(transactionDetails.getTransactionDate()) && !StringUtils.isEmpty(transactionDetails.getEventId()) && !StringUtils.isEmpty(transactionDetails.getOutcomeName())) {
                    Boolean res = sportsBookService.validateUserToken(transactionDetails.getUserToken());
                    if (res) {
                        List<UserTransactionEventViewDetailsTO> userTransactionEventViewList = sportsBookService.getTransactionProfitLossView(transactionDetails.getUserToken(), transactionDetails.getTransactionType()
                                , transactionDetails.getTransactionDate(), transactionDetails.getBetPlaced(), transactionDetails.getEventId(), transactionDetails.getOutcomeName());
                        return outputGenerator.getSuccessResponse(userTransactionEventViewList);
                    } else {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                } else {
                    throw new Exception(ExceptionConstants.FIELDS_EMPTY);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.FAILED_GET_TRANS_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_SPORTWISE_PROFIT_LOSS, method = RequestMethod.GET)
    public SportsBookOutput getSportsProftLossDetails(String userToken, String fromDate, String toDate) {
        try {
            if (!StringUtils.isEmpty(userToken)) {
                if (!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) {
                    Boolean res = sportsBookService.validateUserToken(userToken);
                    if (res) {
                        List<SportProfitLossDetailsTo> sportProfitLossDetails = sportsBookService.getSportsProftLossDetails(userToken, fromDate, toDate);
                        return outputGenerator.getSuccessResponse(sportProfitLossDetails);
                    } else {
                        return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                    }
                }else {
                    return outputGenerator.getSuccessResponse(ExceptionConstants.FIELDS_EMPTY);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.FAILED_GET_TRANS_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_USER_BET_HISTORY, method = RequestMethod.POST)
    public SportsBookOutput getBetHistory(@RequestBody BetHistoryFetchDetailsTO betHistoryFetchDetailsTO) {
        try {
            if (!StringUtils.isEmpty(betHistoryFetchDetailsTO.getUserToken())) {
                Boolean res = sportsBookService.validateUserToken(betHistoryFetchDetailsTO.getUserToken());
                if (res) {
                    UserBetHistoryDetailsWrapperTO userBetHistoryDetailsWrapperTO = sportsBookService.getBetHistoryDetails(betHistoryFetchDetailsTO);
                    return outputGenerator.getSuccessResponse(userBetHistoryDetailsWrapperTO);
                } else {
                    return outputGenerator.getSuccessResponse(SportsBookConstants.USER_ID_NOT_EXIST);
                }

            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.FAILED_GET_TRANS_DETAILS);
        }
    }

    @RequestMapping(value = SportsBookConstants.SEND_EMAIL_FORGET_PASSWORD_CONFIRMATION, method = RequestMethod.GET)
    public SportsBookOutput sendEmailForgetPasswordConfirmation(String emailId) {
        PasswordDetailsTo otpDetails = new PasswordDetailsTo();
        if (!StringUtils.isEmpty(emailId)) {
            LOGGER.info("User Email-Id -" + emailId);
            if (!(sportsBookService.userCredentialsValidation(emailId, null).isUniqueEmailId()) ){
                String validToken = sportsBookService.UpdateForgetPwdEmailConfirmation(emailId);
                if (!StringUtils.isEmpty(validToken)) {
                    otpDetails.setUpdated(true);
                    otpDetails.setMessage(SportsBookConstants.EMAIL_GENERATED);
                    return outputGenerator.getSuccessResponse(otpDetails);
                } else {
                    otpDetails.setUpdated(false);
                    otpDetails.setMessage(SportsBookConstants.TCHNICAL_SUPPORT);
                    return outputGenerator.getSuccessResponse(otpDetails);
                }
            } else {
                LOGGER.warn("Incorrect Email-Id");
                otpDetails.setUpdated(false);
                otpDetails.setMessage(SportsBookConstants.EMAIL_ID_NOT_EXIST);
                return outputGenerator.getSuccessResponse(otpDetails);
            }
        } else {
            LOGGER.warn("Invalid Email-Id");
            otpDetails.setUpdated(false);
            otpDetails.setMessage(SportsBookConstants.EMAIL_ID_INVALID);
            return outputGenerator.getSuccessResponse(otpDetails);
        }
    }

    @RequestMapping(value = SportsBookConstants.UPDATE_FORGET_PASSWORD, method = RequestMethod.POST)
    public SportsBookOutput updateForgetPassword(@RequestBody @Valid @NotNull ForgetPasswordInputTO forgetPasswordInput) {
        try {
            PasswordDetailsTo passwordDetailsTo = new PasswordDetailsTo();
            if (!StringUtils.isEmpty(forgetPasswordInput.getEmailConfirmationToken())) {

                LOGGER.info("User Email Id -" + forgetPasswordInput.getEmailConfirmationToken());
                String emailId = sportsBookService.validateEmail(forgetPasswordInput.getEmailConfirmationToken());

                if (!StringUtils.isEmpty(emailId)) {
                    sportsBookService.updateForgetPassword(forgetPasswordInput, emailId);
                    passwordDetailsTo.setUpdated(true);
                    passwordDetailsTo.setMessage(SportsBookConstants.PASSWORD_UPDATE);
                } else {
                    LOGGER.warn("Session Expired");
                    //throw new Exception(ExceptionConstants.SESSION_EXPIRED);
                    passwordDetailsTo.setUpdated(false);
                    passwordDetailsTo.setMessage(ExceptionConstants.SESSION_EXPIRED);
                }
            } else {
                LOGGER.warn("Session Token not found");
                //throw new Exception(ExceptionConstants.SESSION_TOKEN);
                passwordDetailsTo.setUpdated(false);
                passwordDetailsTo.setMessage(ExceptionConstants.SESSION_TOKEN);
            }
            return outputGenerator.getSuccessResponse(passwordDetailsTo);

        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.UPDATE_USER_PASSWORD);
        }
    }

    @RequestMapping(value = SportsBookConstants.GET_SESSION_KEY, method = RequestMethod.GET)
    public SportsBookOutput getSessionKey() {
        try {
            BetfairSessionTO sessionKey = sportsBookService.getSessionKey();
            return outputGenerator.getSuccessResponse(sessionKey);
        } catch (Exception e) {
            return outputGenerator.getFailureResponse(e, SportsBookConstants.FAILED_GET_SESSION_KEY);
        }
    }


}