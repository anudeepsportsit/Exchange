package com.bettorlogic.victoryexch.middlelayer.common.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.BetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.BetHistoryFetchDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetHistoryDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.BalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportHierarchyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ClearedOrderSummaryReportTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface SportsBookDao {

    Integer saveMarketProfitLossDetails(String userToken, MarketProfitLossTO marketProfitLossDetails);

    List<MarketProfitLossTO> getMarketProfitLossDetails(String userToken);

    SubHeaderDetailsWrapperTO getSubHeaderDetails();

    int getuserId(String userToken);

    Integer addAdminLeagues(LeaguesTO leaguesDetails);

    Integer processUserRegistration(UserRegistrationDetailsTO registrationDetails, String encodedPassword, String emailConfirmationToken);

    UniqueUserResultsTO isEmailIdAndUsernameUnique(String emailId, String userName);

    UserLoginDetailsTO getLoginDetails(String emailId) throws IOException;

    UserLoginDetailsTO insertLoginToken(String emailId);

    String processUserLogout(String loginToken);

    FooterControlsWrapperTO getFooterControls();

    FooterControlsWrapperTO getLogoAndLicencingInfoInFCs(FooterControlsWrapperTO footerControlsWrapper);

    BalanceDetailsTO extractUserBalance(String loginToken);

    OneClickCoinWrapperTO getOneClickDetails(String userToken);

    UserCoinDetailsTO getUserCoins(String userToken);

    Boolean updateUserCoins(UserCoinDetailsTO UserCoinDetailsTO);

    String saveCoinDetails(String userId);

    String storeCoinDetails(UserCoinDetailsTO saveUserCoinDetailsTO);

    Boolean updateOneClick(UserCoinDetailsTO UserCoinDetailsTO);

    Boolean storeCoinSettingDetails(UserCoinDetailsTO UserCoinDetailsTO);

    Boolean validateUserToken(String userToken);

    FavouriteEventDetailsTO saveFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails);

    FavouriteEventDetailsTO deleteFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails);

    UserProfileDetailsWrapperTO getUserProfileDetails(String userToken);

    void updateUserPassword(String password, String emailId);

    UserLoginDetailsTO getUserDetails(UserPasswordUpdateDetailsTO password) throws Exception;

    List<UserTransactionEventViewDetailsTO> getTransactionProfitLossView(String userToken, String transactionType,String transactionDate,String betPlaced,Integer eventId,String outcomeName);

    int getTransactionsCount (String userToken, String transactionType, String fromDate, String toDate);

    List<UserTransactionDetailsTO> getTransactionDetails(String userToken, String transactionType, String fromDate, String toDate, Integer pageNumber, Integer pageSize,Integer sportId);

    List<SportProfitLossDetailsTo> getSportsProftLossDetails(String userToken, String fromDate, String toDate);

    UserBetHistoryDetailsWrapperTO extractBetHistoryDetails(BetHistoryFetchDetailsTO betHistoryFetchDetailsTO);

    SportHierarchyDetailsTO getCommonEventDetails(Integer sportId);

    String getEmailVerifiedStatus(String token);

    Integer getEmailVerificationStatus(String emailId);

    int getEmailIdCount(String emailId);

    void updateGeneratedTokenConfirmation(String emailId, String emailConfirmationToken);

    String validateEmail(String emailConfirmationToken);

    BetfairSessionTO getBetfairSessionKey();

    Double getBetPercentage();

    String getEventKO(Integer eventId);

    void betfairSettlement(BetfairServerResponse<ClearedOrderSummaryReportTO> response) throws ParseException;

    UserBetDetailsTO validateBet(String userToken);

    Double getBalance(BetDetailsTO betDetailsTO, Integer userId, String bet, Integer betId);


    Double getUpdateBalance(Integer eventId, String oddType, String stake, String returns, Integer marketId, String odds, Integer betId, int userId, String update, Integer outcomeId);

    int saveUserTimeZone(Integer id, String userToken);


    AgentTO insertAgentLoginToken(String emailId);
    AgentTO getAgentDetails(String emailId);
    Integer getRole(String username);

    List<MarketProfitLossTO> getAgentMarketProfitLossDetails();

    String getEzugiToken(String userToken);

    Integer getRoleId(String userToken);

    Integer getUser(String userToken);

    Integer getStatus(String emailId);

    String getPassword(String agentName);

    void suspendAgentMarket(Integer agentId, String loginToken);

    void unSuspend(Integer agentId);

    void insertLogin(String token);

    void updateAgentPwd(UserPasswordUpdateDetailsTO password);

    Integer getId(String emailId);

    void insertCommission(Integer id);

    BigDecimal getUserProfit(BetDetailsTO userBetDetailsTO, Integer userId);
}