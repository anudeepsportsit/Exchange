package com.bettorlogic.victoryexch.middlelayer.common.service;

import com.bettorlogic.victoryexch.middlelayer.common.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.BalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportHierarchyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ClearedOrderSummaryReportTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.OrderDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.OrdersResponseTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;

import javax.mail.internet.AddressException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface SportsBookService {
    UniqueUserResultsTO userCredentialsValidation(String emailId, String userName);

    Map<String, Object> addLeagues(LeaguesTO leaguesDetails);

    Boolean saveProfitLossDetails(String userToken, List<MarketProfitLossTO> marketProfitLossList);

    List<MarketProfitLossTO> getProfitLossDetails(String userToken);

    List<MarketProfitLossTO> getAdminProfitLossDetails();

    int getId(String userToken);

    UserRegistrationOutput processUserRegistration(UserRegistrationDetailsTO registrationDetails);

    Boolean isValidEmail(String email) throws AddressException;

    Map<String, Object> userLoginValidation(UserLoginDetailsTO userDetails) throws Exception;

    //UserBalanceDetailsTO saveBetSlipDetailsSuccessResponse(BetSlipDetailsWrapperTO betSlipWrapperDetails, BetSlipValidationDetailsTO betSlipValidationDetails);

    UserBalanceDetailsTO saveBetSlipDetailsSuccessResponse1(BetSlipDetailsWrapperTO betSlipWrapperDetails, Integer userId, BigDecimal exposureLimit, BigDecimal settledBalance);

    boolean userLogout(String loginToken);

    SubHeaderDetailsWrapperTO retrieveSubHeaders();

    FooterControlsWrapperTO getFooterControls();

    BetSlipValidationDetailsTO checkValidationOfBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails);

    OneClickCoinWrapperTO getOneClickCoin(String userToken);

    UserCoinDetailsTO getUserCoins(String userToken);

    Boolean updateUserCoins(UserCoinDetailsTO UserCoinDetailsTO);

    String saveUserCoins(UserCoinDetailsTO userCoinDetails);

    BalanceDetailsTO getUserBalance(String loginToken);

    Boolean updateOneClickCoins(UserCoinDetailsTO UserCoinDetailsTO);

    Boolean storeCoinSettingDetails(UserCoinDetailsTO UserCoinDetailsTO);

    Boolean validateUserToken(String userToken);

    Integer getUsersList(String userToken);

    FavouriteEventDetailsTO saveFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails);

    FavouriteEventDetailsTO deleteFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails);

    EventViewDetailsWrapperTO getEventViewDetails(Integer eventId, String userToken) throws ExecutionException, InterruptedException;

    SportHierarchyDetailsTO getCommonEventDetails(Integer sportId);

    BetSlipFetchDetailsWrapperTO getBetSlipDetails(BetSlipFetchDetailsWrapperTO betSlipFetchDetailsWrapperTO);

    UserProfileDetailsWrapperTO getUserProfile(String userToken);

    SportsBookConstants.PasswordMessages updateUserPasswordDetails(UserPasswordUpdateDetailsTO password) throws Exception;

    List<UserTransactionEventViewDetailsTO> getTransactionProfitLossView(String userToken, String transactionType, String transactionDate, String betPlaced, Integer eventId, String outcomeName);

    int getTransactionsCount (String userToken, String transactionType, String fromDate, String toDate);

    List<UserTransactionDetailsTO> getTransactionDetails(String userToken, String transactionType, String fromDate, String toDate, Integer pageNumber, Integer pageSize,Integer sportId);

    List<SportProfitLossDetailsTo> getSportsProftLossDetails(String userToken, String fromDate, String toDate);

    UserBetHistoryDetailsWrapperTO getBetHistoryDetails(BetHistoryFetchDetailsTO betHistoryFetchDetailsTO);

    Boolean hasDuplicate(UserCoinDetailsTO userDetails, int coinSize);

    EmailVerificationDetailsTo getEmailVerifiedStatus(String token);

    Boolean authenticateUser(String emailId);

    int getEmailIdCount(String emailId);

    String UpdateForgetPwdEmailConfirmation(String emailId);

    String validateEmail(String emailConfirmationToken);

    void updateForgetPassword(ForgetPasswordInputTO forgetPasswordInputTO, String emailId);

    BetfairSessionTO getSessionKey();

    List<OrdersResponseTO> getBetDetails(OrderDetailsTO orderDetailsTO);

    BetfairServerResponse<ClearedOrderSummaryReportTO> getSettledDetails(OrderDetailsTO orderDetailsTO) throws ParseException;

    UserBetDetailsTO checkValidation(String userToken);

    boolean saveTime(Integer id, String userToken);

    Map<String, Object> agentLoginValidation(UserLoginDetailsTO userDetails);

    Integer getRoleId(String username);

    Map<String, Object> getEzugiToken(String userToken);

    Integer getLoginUserRoleId(String userToken);

    SportsBookConstants.PasswordMessages updatePasswordByAdmin(UserPasswordUpdateDetailsTO password, Integer roleId) throws Exception;

    Integer getStatus(String emailId);

    void suspendMarkets(Integer agentId, String loginToken);

    void unSuspendMarkets(Integer agentId);

    void updateAgentPassword(UserPasswordUpdateDetailsTO password);

    void getUserId(String emailId);

    SportsBookConstants.PasswordMessages updateAgentPasswordDetails(UserPasswordUpdateDetailsTO password);

    BigDecimal getProfit(BetDetailsTO userBetDetailsTO, Integer userId);
}