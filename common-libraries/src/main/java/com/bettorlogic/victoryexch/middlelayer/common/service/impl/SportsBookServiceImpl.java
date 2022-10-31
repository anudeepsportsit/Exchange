package com.bettorlogic.victoryexch.middlelayer.common.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.common.dao.BetSlipDetailsDao;
import com.bettorlogic.victoryexch.middlelayer.common.dao.EventViewDetailsDao;
import com.bettorlogic.victoryexch.middlelayer.common.dao.SportsBookDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.BalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportHierarchyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.EmailSenderService;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.*;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.*;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.service.BetPlacement;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SportsBookServiceImpl implements SportsBookService {

    private static final String DOMAIN_NAME_URL = "${domain-name-url}";
    private static final String PASSWORD_DOMAIN_NAME_URL = "${pwd-domain-name-url}";
    private static final Logger LOGGER = LogManager.getLogger(SportsBookServiceImpl.class);
    @Autowired
    private BetResponseTO response;
    @Autowired
    private SportsBookDao sportsBookDao;
    @Autowired
    private BetSlipDetailsDao betSlipDetailsDao;
    @Autowired
    private EventViewDetailsDao eventViewDetailsDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private BetPlacement betPlacement;

    @Value(DOMAIN_NAME_URL)
    private String confirmAccount;

    @Value(PASSWORD_DOMAIN_NAME_URL)
    private String confirmPassword;

    @Override
    public UniqueUserResultsTO userCredentialsValidation(String emailId, String userName) {
        return sportsBookDao.isEmailIdAndUsernameUnique(emailId, userName);
    }

    @Override
    public Map<String, Object> addLeagues(LeaguesTO leaguesDetails) {
        Map<String, Object> result = new HashMap<>();
        boolean isCreated;

        isCreated = sportsBookDao.addAdminLeagues(leaguesDetails) != 0;
        result.put(SportsBookConstants.LEAGUE_CREATED_KEY, isCreated);

        return result;
    }

    @Override
    public Boolean saveProfitLossDetails(String userToken, List<MarketProfitLossTO> marketProfitLossList) {
        AtomicBoolean saveStatus = new AtomicBoolean(false);
        marketProfitLossList.forEach(marketProfitLoss -> {
            saveStatus.set(sportsBookDao.saveMarketProfitLossDetails(userToken, marketProfitLoss) == 1);
        });
        return saveStatus.get();
    }

    @Override
    public List<MarketProfitLossTO> getProfitLossDetails(String userToken) {
        List<MarketProfitLossTO> marketProfitLossList = sportsBookDao.getMarketProfitLossDetails(userToken);
        return marketProfitLossList;
    }

    @Override
    public List<MarketProfitLossTO> getAdminProfitLossDetails() {
        List<MarketProfitLossTO> marketProfitLossList = sportsBookDao.getAgentMarketProfitLossDetails();
        return marketProfitLossList;
    }

    @Override
    public UserBetDetailsTO checkValidation(String userToken) {
        UserBetDetailsTO userBetDetailsTO = sportsBookDao.validateBet(userToken);
        return userBetDetailsTO;
    }

    @Override
    public boolean saveTime(Integer id, String userToken) {
        int status = sportsBookDao.saveUserTimeZone(id,userToken);
           if(status==1)
               return true;
           else
               return false;
    }

    @Override
    public Map<String, Object> agentLoginValidation(UserLoginDetailsTO userDetails) {
        AgentTO agentTO=sportsBookDao.insertAgentLoginToken(userDetails.getEmailId());
        AgentTO agentDetails=sportsBookDao.getAgentDetails(userDetails.getEmailId());
        Map<String,Object> result=new HashMap<>();
        if(agentDetails.getAgentName().equals(userDetails.getEmailId()) &&
                agentDetails.getPassword().equals(userDetails.getPassword()))
        {
            sportsBookDao.insertLogin(agentDetails.getToken());
            result.put(SportsBookConstants.AGENTNAME, agentDetails.getAgentName());
            result.put(SportsBookConstants.INACTIVE_SESSION, agentDetails.getInactiveSessionSuspension());
            result.put(SportsBookConstants.BOOKPOSITION, agentDetails.getBookPosition());
            result.put(SportsBookConstants.BETLISTLIVE, agentDetails.getBetlistLive());
            result.put(SportsBookConstants.RESULTDELARATION, agentDetails.getResultDeclation());
            result.put(SportsBookConstants.AVALABLE, agentDetails.getAvailable());
            result.put(SportsBookConstants.STATUS, agentDetails.getStatus());
            result.put(SportsBookConstants.TOKEN, agentDetails.getToken());
            result.put(SportsBookConstants.ROLE_ID,agentDetails.getRoleId());
            result.put(SportsBookConstants.AGENTID, agentDetails.getAgentId());
            return  result;
        }
        else
        {
            LOGGER.warn("Invalid email-id ");
            try {
                throw new Exception(ExceptionConstants.INVALID_EMAILID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Integer getRoleId(String username) {
         int roleId=sportsBookDao.getRole(username);
         return  roleId;
    }

    @Override
    public Map<String, Object> getEzugiToken(String userToken) {
        String ezugiToken=sportsBookDao.getEzugiToken(userToken);
              Map<String, Object> result = new HashMap<>();

               result.put(SportsBookConstants.EZUGI_TOKEN, ezugiToken);

                           return result;
    }

    @Override
    public Integer getLoginUserRoleId(String userToken) {
        return sportsBookDao.getRoleId(userToken);
    }

    @Override
    public SportsBookConstants.PasswordMessages updatePasswordByAdmin(UserPasswordUpdateDetailsTO password, Integer roleId) throws Exception {
        UserLoginDetailsTO userDetails = sportsBookDao.getUserDetails(password);
        String currentPassword = sportsBookDao.getPassword(password.getEmailId());
        Boolean isPasswordMatch = passwordEncoder.matches((password.getCurrentPassword()), userDetails.getPassword());
        if(isPasswordMatch){
            if(password.getEmailId() == null) {
                Boolean isOldPassword = passwordEncoder.matches((password.getNewPassword()), userDetails.getPassword());
                if(isOldPassword){
                    return SportsBookConstants.PasswordMessages.PASSWORD_MATCH;
                }
                sportsBookDao.updateUserPassword(passwordEncoder.encode(password.getNewPassword()), userDetails.getEmailId());
                return SportsBookConstants.PasswordMessages.PASSWORD_UPDATE;
            }
            Boolean isOldPassword = passwordEncoder.matches((password.getNewPassword()), currentPassword);
            if(isOldPassword){
                return SportsBookConstants.PasswordMessages.PASSWORD_MATCH;
            }
            sportsBookDao.updateUserPassword(passwordEncoder.encode(password.getNewPassword()), password.getEmailId());
            return SportsBookConstants.PasswordMessages.PASSWORD_UPDATE;
        }
        else{
            return SportsBookConstants.PasswordMessages.PASSWORD_NOT_MATCHED;
        }
    }

    @Override
    public Integer getStatus(String emailId) {
        return sportsBookDao.getStatus(emailId);
    }

    @Override
    public void suspendMarkets(Integer agentId, String loginToken) {
        sportsBookDao.suspendAgentMarket(agentId,loginToken);
    }

    @Override
    public void unSuspendMarkets(Integer agentId) {
        sportsBookDao.unSuspend(agentId);
    }

    @Override
    public void updateAgentPassword(UserPasswordUpdateDetailsTO password) {
        sportsBookDao.updateAgentPwd(password);
    }

    @Override
    public void getUserId(String emailId) {
       Integer id = sportsBookDao.getId(emailId);
       sportsBookDao.insertCommission(id);
    }

    @Override
    public SportsBookConstants.PasswordMessages updateAgentPasswordDetails(UserPasswordUpdateDetailsTO password) {
        String currentPassword = sportsBookDao.getPassword(password.getEmailId());
        Boolean isPasswordMatch = passwordEncoder.matches((password.getCurrentPassword()), currentPassword);
        if(isPasswordMatch){
            sportsBookDao.updateUserPassword(passwordEncoder.encode(password.getNewPassword()), password.getEmailId());
            return SportsBookConstants.PasswordMessages.PASSWORD_UPDATE;
        }else{
            return SportsBookConstants.PasswordMessages.PASSWORD_NOT_MATCHED;
        }
    }

    @Override
    public BigDecimal getProfit(BetDetailsTO userBetDetailsTO, Integer userId) {
        return sportsBookDao.getUserProfit(userBetDetailsTO,userId);
    }

    @Override
    public int getId(String userToken) {
        int status = sportsBookDao.getuserId(userToken);
        return status;
    }

    @Override
    public UserRegistrationOutput processUserRegistration(UserRegistrationDetailsTO registrationDetails) {
        UserRegistrationOutput userRegistrationOutput = new UserRegistrationOutput();

        boolean isCreated;
        UniqueUserResultsTO uniqueUserResults = sportsBookDao.isEmailIdAndUsernameUnique(registrationDetails.getEmailId(), registrationDetails.getUserName());
        if (uniqueUserResults.isUniqueEmailId() && uniqueUserResults.isUniqueUserName()) {
            String encodedPassword = passwordEncoder.encode(registrationDetails.getPassword());
            String emailConfirmationToken = UUID.randomUUID().toString();
            isCreated = sportsBookDao.processUserRegistration(registrationDetails, encodedPassword, emailConfirmationToken) != null;
            userRegistrationOutput.setCreated(isCreated);
            sportsBookDao.saveCoinDetails(registrationDetails.getEmailId());
            this.sendEmailConfirmation(registrationDetails.getEmailId(), emailConfirmationToken);
        } else {
            userRegistrationOutput.setCreated(false);
            if (uniqueUserResults.isUniqueEmailId()) {
                if (!uniqueUserResults.isUniqueUserName()) {
                    userRegistrationOutput.setReason(ExceptionConstants.DUPLICATE_USER_NAME);
                }

            } else {
                userRegistrationOutput.setReason(ExceptionConstants.DUPLICATE_EMAIL_ID);
            }
        }
        return userRegistrationOutput;
    }

    private void sendEmailConfirmation(String emailId, String emailConfirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailId);
        mailMessage.setSubject(SportsBookConstants.EmailVerificationConstants.EMAIL_VERIFICATION);
        mailMessage.setFrom("info@gamex.group");
        mailMessage.setText(SportsBookConstants.EmailVerificationConstants.CONTACT + confirmAccount + emailConfirmationToken);
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public Boolean isValidEmail(String email) throws AddressException {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
        return true;
    }

    @Override
    public Map<String, Object> userLoginValidation(UserLoginDetailsTO userDetails) throws Exception {
        Map<String, Object> result = new HashMap<>();
        UserLoginDetailsTO userLoginDetailsTO = sportsBookDao.insertLoginToken(userDetails.getEmailId());
        UserLoginDetailsTO userLoginDetails = sportsBookDao.getLoginDetails(userDetails.getEmailId());

        boolean isValidEmail = true;
        boolean isValidPassword = true;
        if (userLoginDetails.getEmailId() != null && userLoginDetails.getPassword() != null) {
            isValidEmail = (userLoginDetails.getEmailId().equalsIgnoreCase(userDetails.getEmailId()) ||
                    userLoginDetails.getUserName().equalsIgnoreCase(userDetails.getEmailId()));  // checking emailId field with either emailId or username from database

            isValidPassword = true;
//             if(!isValidPassword)
//             {
//                 throw new Exception(ExceptionConstants.INVALID_PASSWORD);
//             }
        }
        result.put(SportsBookConstants.USER_VALIDATION_KEY, isValidEmail && isValidPassword);
        if (isValidEmail && isValidPassword) {
            result.put(SportsBookConstants.USER_LOGIN_TOKEN_KEY, userLoginDetailsTO.getLoginToken());
            result.put(SportsBookConstants.USER_ACCOUNT_BALANCE, userLoginDetailsTO.getUserBalance());
            result.put(SportsBookConstants.USER_EXPOSURE, userLoginDetailsTO.getExposure());
            result.put(SportsBookConstants.USER_ID, userLoginDetails.getUserId());
            result.put(SportsBookConstants.USER_NAME, userLoginDetails.getUserName());
            result.put(SportsBookConstants.ROLE_ID, userLoginDetails.getRoleId());
            result.put(SportsBookConstants.STATUS_ID, userLoginDetails.getStatusId());
            result.put(SportsBookConstants.CURRENCY_ID, userLoginDetails.getCurrencyId());
            result.put(SportsBookConstants.CURRENCY_CODE, userLoginDetails.getCurrencyCode());
            result.put(SportsBookConstants.USER_TIME_ZONE, userLoginDetails.getTimeZone());
            result.put(SportsBookConstants.SESSIONTOKEN,userLoginDetails.getSessionToken());
//            if (userLoginDetails.getStatusId() == 3) {
//                throw new Exception(ExceptionConstants.ACCOUNT_LOCKED);
//            }
//            if ((StringUtils.isEmpty(userLoginDetailsTO.getLoginToken()) || StringUtils.containsWhitespace(userLoginDetailsTO.getLoginToken()))) {
//                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
//            }
        }
        return result;
    }

    @Override
    public BetSlipValidationDetailsTO checkValidationOfBetSlipDetails(BetSlipDetailsWrapperTO betSlipWrapperDetails) {
        List<BetSlipValidationDetailsTO> betSlipValidationDetailsList = new ArrayList<>();
        for (BetDetailsTO betDetails : betSlipWrapperDetails.getBetDetailsList()) {
            betSlipValidationDetailsList.add(betSlipDetailsDao.validateBetSlipDetails(betSlipWrapperDetails, betDetails));
        }
        betSlipWrapperDetails.setBetSlipValidationDetailsList(betSlipValidationDetailsList);
        return this.setReasonForFailureForBetSlip(betSlipValidationDetailsList);
    }

    private BetSlipValidationDetailsTO setReasonForFailureForBetSlip(List<BetSlipValidationDetailsTO> betSlipValidationDetailsList) {
        BetSlipValidationDetailsTO betSlipValidationDetails = new BetSlipValidationDetailsTO();
        betSlipValidationDetailsList.forEach(betSlipValidationDetailsSub -> {
            if (!betSlipValidationDetailsSub.getValidBet()) {
                if (betSlipValidationDetailsSub.getIsValidUser()) {
                    if (betSlipValidationDetailsSub.getIsPlayer()) {
                        if (betSlipValidationDetailsSub.getIsActive()) {
                            if (betSlipValidationDetailsSub.getIsValidEvent()) {
                                if (betSlipValidationDetailsSub.getIsValidMarket()) {
                                    if (!betSlipValidationDetailsSub.getMarketSuspended()) {
                                        if (betSlipValidationDetailsSub.getIsValidOutcome()) {
                                            if (!betSlipValidationDetailsSub.getOddsChangeFlag()) {
                                                if (betSlipValidationDetailsSub.hasExceededExposureLimit()) {
                                                    betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT);
                                                } else if (!betSlipValidationDetailsSub.hasSufficientBalance()) {
                                                    betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
                                                }
                                            } else {
                                                betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.ODDS_CHANGED);
                                            }
                                        } else {
                                            betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.INVALID_OUTCOME);
                                        }
                                    } else {
                                        betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.MARKET_SUSPENDED);
                                    }
                                } else {
                                    betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.INVALID_MARKET);
                                }
                            } else {
                                betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.INVALID_EVENT);
                            }
                        } else {
                            betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.SUSPENDED_LOCKED_USER);
                        }
                    } else {
                        betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.USER_NOT_PLAYER);
                    }
                } else {
                    betSlipValidationDetails.setReasonForFailure(SportsBookConstants.BetSlipConstants.INVALID_USER);
                }
            }
        });
        betSlipValidationDetails.setValidBet(betSlipValidationDetailsList.parallelStream().allMatch(BetSlipValidationDetailsTO::getValidBet));
        return betSlipValidationDetails;
    }

    private Double updateUserBalanceInBetSlip(BetSlipDetailsWrapperTO betSlipWrapperDetails) {
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        Double totalStake = 0.0;
        for (BetDetailsTO betDetailsTO : betSlipWrapperDetails.getBetDetailsList()) {
            if (betDetailsTO.getOddType().equals(SportsBookConstants.BetSlipConstants.ODD_TYPE)) {
                Double stake = betDetailsTO.getStakeAmount();
                totalStake = totalStake + stake;
            } else {
                Double returns = betDetailsTO.getReturns() - betDetailsTO.getStakeAmount();
                totalStake = totalStake + returns;
            }
        }
        userBalanceDetails.setTransactionAmount(totalStake);
        userBalanceDetails.setFromTo(SportsBookConstants.BetSlipConstants.BET_SLIP_FROM_TO);
        userBalanceDetails.setTransactionRemarks(SportsBookConstants.BetSlipConstants.BET_SLIP_TRANSACTION_TYPE_ID + betSlipWrapperDetails.getBetSlipId());
        userBalanceDetails.setTransactionTypeId(betSlipWrapperDetails.getBetSlipId());
        userBalanceDetails.setTransactionType(SportsBookConstants.BetSlipConstants.BET_SLIP_TRANSACTION_TYPE);
        userBalanceDetails.setUserToken(betSlipWrapperDetails.getUserToken());
        UserBalanceDetailsTO userBalanceDetailsOutput = betSlipDetailsDao.updateUserBalanceDetails(userBalanceDetails, totalStake);
        return userBalanceDetailsOutput.getRemainingBalance();
    }

    /*@Override
    public UserBalanceDetailsTO saveBetSlipDetailsSuccessResponse(BetSlipDetailsWrapperTO betSlipWrapperDetails, BetSlipValidationDetailsTO betSlipValidationDetails) {

        boolean isValidBet = betSlipWrapperDetails
                .getBetSlipValidationDetailsList().parallelStream().allMatch(BetSlipValidationDetailsTO::getValidBet);

        List<BetDetailsTO> betDetailsList = betSlipWrapperDetails.getBetDetailsList();
        boolean betSaveStatus = false;
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        if (isValidBet) {
            int betSlipId = betSlipDetailsDao.saveBetSlipDetails(betSlipWrapperDetails);
            int[] betSaveDetails = betSlipDetailsDao.saveBetDetails(betDetailsList, betSlipId);
            betSlipWrapperDetails.setBetSlipId(betSlipId);
            for (int i : betSaveDetails) {
                betSaveStatus = (i != 0);
            }
            if (betSaveStatus) {
                userBalanceDetails = this.updateUserBalanceInBetSlip(betSlipWrapperDetails, betSlipValidationDetails,
                        betSlipWrapperDetails.getTotalReturns() - betSlipWrapperDetails.getTotalStake());
            }
        }

        return userBalanceDetails;
    }*/

    /*@Override
    public UserBalanceDetailsTO saveBetSlipDetailsSuccessResponse(BetSlipDetailsWrapperTO betSlipWrapperDetails, BetSlipValidationDetailsTO betSlipValidationDetails) {

        boolean isValidBet = betSlipWrapperDetails
                .getBetSlipValidationDetailsList().parallelStream().allMatch(BetSlipValidationDetailsTO::getValidBet);

        List<BetDetailsTO> betDetailsList = betSlipWrapperDetails.getBetDetailsList();
        List<Integer> betSaveStatus = new ArrayList<>();
        List<BetSlipResponseTO> responseTOList = new ArrayList<>();
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        AtomicReference<Double> remainingBalance = new AtomicReference<>(0.0);
        if (isValidBet) {
            int betSlipId = betSlipDetailsDao.saveBetSlipDetails(betSlipWrapperDetails);
            betSlipWrapperDetails.setBetSlipId(betSlipId);
            betDetailsList.stream().forEach(betDetailsTO -> {
                BetSlipResponseTO responseTO = new BetSlipResponseTO();
                Double percentToBet = sportsBookDao.getBetPercentage();
                Double betFairStake = (betDetailsTO.getStakeAmount() * percentToBet) / 100;
                Double vendorStake = (betDetailsTO.getStakeAmount()) - (betDetailsTO.getStakeAmount() * percentToBet) / 100;
                Long intervalTime = 0L;
                try {
                    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                    SimpleDateFormat format = new SimpleDateFormat(SportsBookConstants.UTC_FORMAT);
                    Date date1 = format.parse(utc.format(DateTimeFormatter.ofPattern(SportsBookConstants.UTC_FORMAT)));
                    Date date2 = format.parse(sportsBookDao.getEventKO(betDetailsTO.getEventId()));
                    long difference = date2.getTime() - date1.getTime();
                    intervalTime = (difference / 1000) / 60;
                } catch (Exception ex) {
                    responseTO.setBetStatus(SportsBookConstants.FAILURE_MESSAGE);
                }
                if ((betDetailsTO.getProviderId() == 2 && betDetailsTO.getIsLive() == 1) || (betDetailsTO.getProviderId() == 2 && intervalTime <= 180)) {
                    processBetfairBets(betSlipWrapperDetails, betSlipValidationDetails, betSaveStatus, responseTOList, userBalanceDetails, remainingBalance, betSlipId, betDetailsTO, responseTO, betFairStake);
                } else if (betDetailsTO.getProviderId() == 2 && betDetailsTO.getIsLive() == 0) {
                    responseTO.setEventId(betDetailsTO.getEventId());
                    responseTO.setBetStatus(SportsBookConstants.FAILURE_MESSAGE);
                    responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
                    responseTO.setErrorCode(SportsBookConstants.ERROR_CODE);
                    responseTO.setOddType(betDetailsTO.getOddType());
                    responseTO.setSelectionId(betDetailsTO.getSelectionId());
                    responseTOList.add(responseTO);
                } else {
                    betDetailsTO.setIsMatched(1);
                    int result = betSlipDetailsDao.saveBet(betDetailsTO, betSlipId);
                    betSaveStatus.add(result);
                    betSlipWrapperDetails.setBetSlipId(betSlipId);

                    userBalanceDetails.setIsBetPlaced(true);
                    responseTO.setEventId(betDetailsTO.getEventId());
                    responseTO.setBetStatus(SportsBookConstants.SUCCESS_MESSAGE);
                    responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
                    responseTO.setOddType(betDetailsTO.getOddType());
                    responseTO.setMatched(true);
                    responseTOList.add(responseTO);
                }
            });
            if (userBalanceDetails.getIsBetPlaced()) {
                remainingBalance.set(this.updateUserBalanceInBetSlip(betSlipWrapperDetails, betSlipValidationDetails));
                betSlipDetailsDao.updateBetSettlementDetails();
            } else {
                BalanceDetailsTO userBalance = sportsBookDao.extractUserBalance(betSlipWrapperDetails.getUserToken());
                remainingBalance.set(userBalance.getUserBalance());
            }
            userBalanceDetails.setBetSlipResponseList(responseTOList);
            userBalanceDetails.setRemainingBalance(remainingBalance.get());
        }
        return userBalanceDetails;
    }*/

    @Override
    public UserBalanceDetailsTO saveBetSlipDetailsSuccessResponse1(BetSlipDetailsWrapperTO betSlipWrapperDetails, Integer userId, BigDecimal exposureLimit, BigDecimal settledBalance) {
        settledBalance=settledBalance==null?BigDecimal.ZERO: settledBalance;
        List<BetDetailsTO> betDetailsList = betSlipWrapperDetails.getBetDetailsList();
        List<Integer> betSaveStatus = new ArrayList<>();
        List<BetSlipResponseTO> responseTOList = new ArrayList<>();
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        AtomicReference<Double> remainingBalance = new AtomicReference<>(0.0);
        int betSlipId = betSlipDetailsDao.saveBetSlipDetails(betSlipWrapperDetails);
        betSlipWrapperDetails.setBetSlipId(betSlipId);
        BigDecimal finalSettledBalance = settledBalance;
        betDetailsList.stream().forEach(betDetailsTO -> {
            Date currentTime = new Date();

            LOGGER.info("Bet Validation for betfair percentage and userbalance for user ID : " + userId + "starts at ---- :" + currentTime);

            BetSlipResponseTO responseTO = new BetSlipResponseTO();
            Double percentToBet = sportsBookDao.getBetPercentage();
            Double availableBalance = sportsBookDao.getBalance(betDetailsTO, userId, ColumnLabelConstants.PLACEMENT, 0);
            Double exposure = finalSettledBalance.doubleValue() - availableBalance;

            if(exposureLimit != null) {
                if(exposure > exposureLimit.doubleValue() || exposureLimit.compareTo(BigDecimal.ZERO) == 0) {
                    populateInsufficientExposureResponse(responseTOList, userBalanceDetails, betDetailsTO, responseTO);
                }else{
                    processBets(betSlipWrapperDetails, userId, betSaveStatus, responseTOList, userBalanceDetails, remainingBalance, betSlipId, betDetailsTO, responseTO, percentToBet, availableBalance);
                }
            }else{
                processBets(betSlipWrapperDetails, userId, betSaveStatus, responseTOList, userBalanceDetails, remainingBalance, betSlipId, betDetailsTO, responseTO, percentToBet, availableBalance);
            }
        });
        if (userBalanceDetails.getIsBetPlaced()) {
            Date currentTime = new Date();
            LOGGER.info("Updating user balance for user ID : " + userId + " at ---- :" + currentTime);
            remainingBalance.set(this.updateUserBalanceInBetSlip(betSlipWrapperDetails));
        } else {
            BalanceDetailsTO userBalance = sportsBookDao.extractUserBalance(betSlipWrapperDetails.getUserToken());
            remainingBalance.set(userBalance.getUserBalance());
        }
        userBalanceDetails.setBetSlipResponseList(responseTOList);
        userBalanceDetails.setRemainingBalance(remainingBalance.get());
        Integer betCount = betSlipDetailsDao.getBetsCount(betSlipId);
        LOGGER.info("Remaining balance user ID " + userId + " is : " + userBalanceDetails.getRemainingBalance());
        if (betCount == 0) {
            betSlipDetailsDao.removeBetSlip(betSlipId);
        }
        return userBalanceDetails;
    }

    private void processBets(BetSlipDetailsWrapperTO betSlipWrapperDetails, Integer userId, List<Integer> betSaveStatus, List<BetSlipResponseTO> responseTOList, UserBalanceDetailsTO userBalanceDetails, AtomicReference<Double> remainingBalance, int betSlipId, BetDetailsTO betDetailsTO, BetSlipResponseTO responseTO, Double percentToBet, Double availableBalance) {
        Date currentTime;
        currentTime = new Date();
        LOGGER.info("Bet Validation for betfair percentage and userbalance for user ID : " + userId + " ends at ---- :" + currentTime);
        LOGGER.info("available balance to bet for user ID " + userId + " is : " + availableBalance);
        if (availableBalance < 0) {
            populateInsufficientBalanceResponse(responseTOList, userBalanceDetails, betDetailsTO, responseTO);
        } else {
            processBet(betSlipWrapperDetails, betSaveStatus, responseTOList, userBalanceDetails, remainingBalance, betSlipId, betDetailsTO, responseTO, percentToBet, userId);
        }
    }

    public void processBet(BetSlipDetailsWrapperTO betSlipWrapperDetails, List<Integer> betSaveStatus, List<BetSlipResponseTO> responseTOList, UserBalanceDetailsTO userBalanceDetails, AtomicReference<Double> remainingBalance, int betSlipId, BetDetailsTO betDetailsTO, BetSlipResponseTO responseTO, Double percentToBet, Integer userId) {
        Double betFairStake = 0.0;
        betFairStake = (betDetailsTO.getStakeAmount() * percentToBet) / 100;
        LOGGER.info("Betfair stake amount for user ID : " + userId + ":" + betFairStake);
        //Double vendorStake = (betDetailsTO.getStakeAmount()) - (betDetailsTO.getStakeAmount() * percentToBet) / 100;
        Long intervalTime = 0L;
        try {
            ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
            SimpleDateFormat format = new SimpleDateFormat(SportsBookConstants.UTC_FORMAT);
            Date date1 = format.parse(utc.format(DateTimeFormatter.ofPattern(SportsBookConstants.UTC_FORMAT)));
            Date date2 = format.parse(sportsBookDao.getEventKO(betDetailsTO.getEventId()));
            long difference = date2.getTime() - date1.getTime();
            intervalTime = (difference / 1000) / 60;
        } catch (Exception ex) {
            responseTO.setBetStatus(SportsBookConstants.FAILURE_MESSAGE);
        }
        if ((betDetailsTO.getProviderId() == 2 && betDetailsTO.getIsLive() == 1) || (betDetailsTO.getProviderId() == 2 && intervalTime <= 172800)) {

            processBetfairBets(betSlipWrapperDetails, betSaveStatus, responseTOList, userBalanceDetails, remainingBalance, betSlipId, betDetailsTO, responseTO, betFairStake, userId);

        } else if (betDetailsTO.getProviderId() == 2 && betDetailsTO.getIsLive() == 0) {
            responseTO.setEventId(betDetailsTO.getEventId());
            responseTO.setBetStatus(SportsBookConstants.FAILURE_MESSAGE);
            responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
            responseTO.setErrorCode(SportsBookConstants.ERROR_CODE);
            responseTO.setOddType(betDetailsTO.getOddType());
            responseTO.setSelectionId(betDetailsTO.getSelectionId());
            LOGGER.info("Pre match Bets not allowed --- response :" + SportsBookConstants.ERROR_CODE);
            responseTOList.add(responseTO);
        } else {
            betDetailsTO.setIsMatched(1);
            Integer betDelay = 0;
            if(betDetailsTO.getProviderId() == 1){
                betDelay = betSlipDetailsDao.getBetDelayMatchOdds(betDetailsTO.getEventId());
            }else {
                betDelay = betSlipDetailsDao.getBetDelay(betDetailsTO.getMarketId());
            }
            if(betDelay == null){
                betDelay = 0;
            }
            try { Thread.sleep(betDelay); } catch (InterruptedException e) { }
            String betId = String.valueOf(System.currentTimeMillis() / 1000);
            betDetailsTO.setClientBetId(Long.parseLong(betId.concat(new Date().toString().substring(8, 10))));
            int result = betSlipDetailsDao.saveBet(betSlipWrapperDetails.getUserToken(), betDetailsTO, betSlipId);
            betSaveStatus.add(result);
            if (result > 0) {
                betSlipDetailsDao.updateBetSettlementDetails();
            }
            betSlipWrapperDetails.setBetSlipId(betSlipId);
            userBalanceDetails.setIsBetPlaced(true);
            responseTO.setEventId(betDetailsTO.getEventId());
            responseTO.setBetStatus(SportsBookConstants.SUCCESS_MESSAGE);
            responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
            responseTO.setOddType(betDetailsTO.getOddType());
            responseTO.setMatched(true);
            responseTOList.add(responseTO);
            LOGGER.info("Bet placed for userId " + userId + " and for event id : " + betDetailsTO.getEventId());
        }
    }

    public void populateInsufficientBalanceResponse(List<BetSlipResponseTO> responseTOList, UserBalanceDetailsTO userBalanceDetails, BetDetailsTO betDetailsTO, BetSlipResponseTO responseTO) {
        responseTO.setEventId(betDetailsTO.getEventId());
        responseTO.setBetStatus(SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
        responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
        responseTO.setErrorCode(SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
        responseTO.setOddType(betDetailsTO.getOddType());
        responseTOList.add(responseTO);
        LOGGER.info("Bet Placed :" + false + ", response :" + SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
        userBalanceDetails.setIsBetPlaced(false);
    }


    public void populateInsufficientExposureResponse(List<BetSlipResponseTO> responseTOList, UserBalanceDetailsTO userBalanceDetails, BetDetailsTO betDetailsTO, BetSlipResponseTO responseTO) {
        responseTO.setEventId(betDetailsTO.getEventId());
        responseTO.setBetStatus(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT);
        responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
        responseTO.setErrorCode(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT);
        responseTO.setOddType(betDetailsTO.getOddType());
        responseTOList.add(responseTO);
        LOGGER.info("Bet Placed :" + false + ", response :" + SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
        userBalanceDetails.setIsBetPlaced(false);
    }

    private void processBetfairBets(BetSlipDetailsWrapperTO betSlipWrapperDetails, List<Integer> betSaveStatus, List<BetSlipResponseTO> responseTOList, UserBalanceDetailsTO userBalanceDetails, AtomicReference<Double> remainingBalance, int betSlipId, BetDetailsTO betDetailsTO, BetSlipResponseTO responseTO, Double betFairStake, Integer userId) {
        BetRequestTO request = new BetRequestTO();
        Date currentTime = new Date();
        request.setSelectionId(betDetailsTO.getSelectionId());
        request.setMarketId(betDetailsTO.getClientMarketId());
        request.setStake(betFairStake);
        request.setPrice(betDetailsTO.getOdds().doubleValue());
        String type = betDetailsTO.getOddType().equalsIgnoreCase(SportsBookConstants.B_ODD_TYPE) ? SportsBookConstants.BACK_ODD_TYPE.toUpperCase() : SportsBookConstants.LAY_ODD_TYPE.toUpperCase();
        request.setType(type);
        request.setAppKey(SportsBookConstants.APP_KEY);
        request.setToken(betSlipDetailsDao.getSessionKey());
        currentTime = new Date();
        LOGGER.info("Getting betfair session key from DB ---- :" + currentTime);

        betSlipWrapperDetails.setBetSlipId(betSlipId);

        currentTime = new Date();
        LOGGER.info("Processing bet for Betfair for userId " + userId + " starts at ---- :" + currentTime);
        response = betPlacement.placeBets(request, betSlipWrapperDetails.getOddsChangeAcceptanceFlag());

        currentTime = new Date();
        LOGGER.info("Processing bet for Betfair for userId " + userId + " ended at ---- :" + currentTime);

        if (response.getBetStatus().equalsIgnoreCase(SportsBookConstants.SUCCESS_MESSAGE)) {
            betDetailsTO.setOriginalOdds(BigDecimal.valueOf(response.getBetPlacedOdds()));
            betDetailsTO.setClientStakeAmount(betFairStake);
            betDetailsTO.setClientBetId(Long.valueOf(response.getBetId()));
            betDetailsTO.setClientBetPlacedDate(response.getBetPlacedDate());
            betDetailsTO.setOdds(BigDecimal.valueOf(response.getBetPlacedOdds()));
            betDetailsTO.setReturns(response.getBetPlacedOdds() * betDetailsTO.getStakeAmount());
            betDetailsTO.setIsMatched(response.getOrderStatus().equalsIgnoreCase(SportsBookConstants.EXECUTABLE) ? 0 : 1);

            currentTime = new Date();
            LOGGER.info("Saving bet for userId " + userId + " Started at ---- :" + currentTime);

            int result = betSlipDetailsDao.saveBet(betSlipWrapperDetails.getUserToken(), betDetailsTO, betSlipId);

            currentTime = new Date();
            LOGGER.info("Bet saved successfully for userId " + userId + " at ---- :" + currentTime + " and betfair Bet ID is :" + response.getBetId());

            betSaveStatus.add(result);
            /*if(result>0){
                betSlipDetailsDao.updateBetSettlementDetails();
            }*/
            userBalanceDetails.setIsBetPlaced(true);

            responseTO.setEventId(betDetailsTO.getEventId());
            responseTO.setBetStatus(SportsBookConstants.SUCCESS_MESSAGE);
            responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
            responseTO.setBetfairBetId(response.getBetId());
            responseTO.setMatched(response.getOrderStatus().equalsIgnoreCase(SportsBookConstants.EXECUTABLE) ? false : true);
            responseTO.setOddType(betDetailsTO.getOddType());
            responseTO.setSelectionId(request.getSelectionId());
            responseTOList.add(responseTO);
        } else if (response.getErrorCode().equalsIgnoreCase(SportsBookConstants.EXPIRED)) {
            responseTO.setErrorCode(response.getErrorCode());
            responseTO.setOddType(betDetailsTO.getOddType());
            responseTO.setSelectionId(request.getSelectionId());
            LOGGER.info("Bet not placed in Betfair for userId : " + userId + " and--- response :" + response.getErrorCode());
        } else {
            responseTO.setEventId(betDetailsTO.getEventId());
            responseTO.setBetStatus(SportsBookConstants.FAILURE_MESSAGE);
            responseTO.setOdds(betDetailsTO.getOdds().doubleValue());
            responseTO.setErrorCode(response.getErrorCode());
            responseTO.setOddType(betDetailsTO.getOddType());
            responseTO.setSelectionId(request.getSelectionId());
            LOGGER.info("Bet not placed in Betfair for userId : " + userId + " and--- response :" + response.getErrorCode());
            responseTOList.add(responseTO);
        }
    }

    @Override
    public List<OrdersResponseTO> getBetDetails(OrderDetailsTO orderDetailsTO) {
        return betPlacement.getBetStatus(orderDetailsTO);
    }

    @Override
    public BetfairServerResponse<ClearedOrderSummaryReportTO> getSettledDetails(OrderDetailsTO orderDetailsTO) throws ParseException {
        BetfairServerResponse<ClearedOrderSummaryReportTO> response = betPlacement.getSettledBets(orderDetailsTO);
        if (response.getResponse() != null) {
            sportsBookDao.betfairSettlement(response);
        }
        return response;
    }

    @Override
    public boolean userLogout(String loginToken) {
        return sportsBookDao.processUserLogout(loginToken) != null;
    }

    @Override
    public SubHeaderDetailsWrapperTO retrieveSubHeaders() {
        return sportsBookDao.getSubHeaderDetails();
    }

    @Override
    public FooterControlsWrapperTO getFooterControls() {
        return sportsBookDao.getLogoAndLicencingInfoInFCs(sportsBookDao.getFooterControls());
    }

    @Override
    public OneClickCoinWrapperTO getOneClickCoin(String userToken) {
        return sportsBookDao.getOneClickDetails(userToken);
    }

    @Override
    public UserCoinDetailsTO getUserCoins(String userToken) {
        return sportsBookDao.getUserCoins(userToken);
    }

    @Override
    public Boolean updateUserCoins(UserCoinDetailsTO UserCoinDetailsTO) {
        return sportsBookDao.updateUserCoins(UserCoinDetailsTO);
    }

    @Override
    public String saveUserCoins(UserCoinDetailsTO saveUserCoinDetails) {
        return sportsBookDao.storeCoinDetails(saveUserCoinDetails);
    }

    @Override
    public Boolean updateOneClickCoins(UserCoinDetailsTO UserCoinDetailsTO) {
        return sportsBookDao.updateOneClick(UserCoinDetailsTO);
    }

    @Override
    public Boolean storeCoinSettingDetails(UserCoinDetailsTO UserCoinDetailsTO) {
        return sportsBookDao.storeCoinSettingDetails(UserCoinDetailsTO);
    }

    @Override
    public Boolean validateUserToken(String userToken) {
        return sportsBookDao.validateUserToken(userToken);
    }

    @Override
    public Integer getUsersList(String userToken) {
        return sportsBookDao.getUser(userToken);
    }

    @Override
    public FavouriteEventDetailsTO saveFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails) {
        return sportsBookDao.saveFavouriteEventDetails(favouriteTeamDetails);
    }

    @Override
    public FavouriteEventDetailsTO deleteFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails) {
        return sportsBookDao.deleteFavouriteEventDetails(favouriteTeamDetails);
    }

    private void replaceOutcomeNamesInEventViewDetails(EventViewDetailsWrapperTO eventViewDetailsWrapper) {
        eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList()
                .forEach(outcomeDetails -> outcomeDetails.setOutcomeName(this.replaceOutcomeNames(outcomeDetails.getOutcomeName(),
                        eventViewDetailsWrapper.getMatchOddDetails().getHomeTeam(), eventViewDetailsWrapper.getMatchOddDetails().getAwayTeam())));
        eventViewDetailsWrapper.getPopularMarketDetailsList()
                .forEach(marketDetails ->
                        marketDetails.getOutcomeDetailsList().forEach(matchOdds ->
                                matchOdds.setOutcomeName(this.replaceOutcomeNames(matchOdds.getOutcomeName(),
                                        marketDetails.getHomeTeam(), marketDetails.getAwayTeam()))));
        eventViewDetailsWrapper.getMatchOdd1Details().getOutcomeDetailsList().forEach(matchOdds ->
                matchOdds.setOutcomeName(this.replaceOutcomeNames(matchOdds.getOutcomeName(),
                        eventViewDetailsWrapper.getMatchOdd1Details().getHomeTeam(), eventViewDetailsWrapper.getMatchOdd1Details().getAwayTeam())));

        if(eventViewDetailsWrapper.getMatchOdd1Details().getOutcomeDetailsList().size() > 0) {
            Collections.swap(eventViewDetailsWrapper.getMatchOdd1Details().getOutcomeDetailsList(), 1, 2);
        }
    }

    @Override
    public EventViewDetailsWrapperTO getEventViewDetails(Integer eventId, String userToken) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(7);
        EventViewDetailsWrapperTO eventViewDetailsWrapper = executorService.submit(() -> eventViewDetailsDao.getMatchScoreDetails(eventId, userToken)).get();
        eventViewDetailsWrapper.setFancyBetDetails(executorService.submit(() -> eventViewDetailsDao.getFancyBetDetails(eventId, userToken, 3)).get());
        eventViewDetailsWrapper.setFancyBet1Details(executorService.submit(() -> eventViewDetailsDao.getFancyBetDetails(eventId, userToken, 4)).get());
        eventViewDetailsWrapper.setMatchOddDetails(executorService.submit(() -> eventViewDetailsDao.getMatchOddDetails(eventId, userToken, 2)).get());
        eventViewDetailsWrapper.setMatchOdd1Details(executorService.submit(() -> eventViewDetailsDao.getMatchOddDetails(eventId, userToken, 1)).get());
        eventViewDetailsWrapper.setPopularMarketDetailsList(executorService.submit(() -> eventViewDetailsDao.getPopularMarketDetails(eventId, userToken, 2)).get());
        eventViewDetailsWrapper.setGoalMarketDetailsList(executorService.submit(() -> eventViewDetailsDao.getGoalMarketDetails(eventId, userToken, 2)).get());
        executorService.shutdown();
        this.processDataOfEventViewDetails(eventViewDetailsWrapper);
        return eventViewDetailsWrapper;
    }

    private void processDataOfEventViewDetails(EventViewDetailsWrapperTO eventViewDetailsWrapper) {
        this.replaceOutcomeNamesInEventViewDetails(eventViewDetailsWrapper);
        this.sortingOutcomesForPopularMarkets(eventViewDetailsWrapper.getPopularMarketDetailsList(), null, 2);
        this.sortingOutcomesForPopularMarkets(eventViewDetailsWrapper.getGoalMarketDetailsList(), null, 2);
        this.sortingOutcomesForPopularMarkets(null, eventViewDetailsWrapper.getMatchOdd1Details(), 1);
        List<MatchOddDetailsTO> popularMatchOddDetailsList = eventViewDetailsWrapper.getPopularMarketDetailsList();
        popularMatchOddDetailsList.forEach(matchOddDetails -> matchOddDetails.getOutcomeDetailsList().forEach(eventViewOutcomeDetails -> {
            matchOddDetails.setMarketSuspended(OutcomeSorter.sortAndGetBackLayOddsWithHighestSize(eventViewOutcomeDetails).getMarketSuspended());
        }));
        List<MatchOddDetailsTO> goalMatchOddDetailsList = eventViewDetailsWrapper.getGoalMarketDetailsList();
        goalMatchOddDetailsList.forEach(matchOddDetails -> matchOddDetails.getOutcomeDetailsList().forEach(eventViewOutcomeDetails -> {
            matchOddDetails.setMarketSuspended(OutcomeSorter.sortAndGetBackLayOddsWithHighestSize(eventViewOutcomeDetails).getMarketSuspended());
        }));
        eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList().forEach(OutcomeSorter::sortBackLayOddsAccordingToSize);
        if(eventViewDetailsWrapper.getMatchOdd1Details().getOutcomeDetailsList().size() > 0) {
            ArrayList<Integer> list = new ArrayList<Integer>(3);
            for (EventViewOutcomeDetailsTO outcomeDetailsTO : eventViewDetailsWrapper.getMatchOdd1Details().getOutcomeDetailsList()) {
                list.add(outcomeDetailsTO.getMarketSuspended());
            }
            if (list.contains(0)) {
                eventViewDetailsWrapper.getMatchOdd1Details().setMarketSuspended(0);
            } else {
                eventViewDetailsWrapper.getMatchOdd1Details().setMarketSuspended(1);
            }
        }
        if (eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList().size() > 0) {
            if ((eventViewDetailsWrapper.getSportId() == 1) && eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList().size() == 3) {
                Collections.swap(eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList(), 1, 2);
            }
            eventViewDetailsWrapper.getMatchOddDetails().setMarketSuspended(eventViewDetailsWrapper.getMatchOddDetails().getOutcomeDetailsList().get(0).getMarketSuspended());
        }
        this.restrictOutcomesToThreeInMatchOdds(eventViewDetailsWrapper.getMatchOddDetails());
    }

    private void sortingOutcomesForPopularMarkets(List<MatchOddDetailsTO> popularMarketsList, MatchOddDetailsTO matchOdd1Details, Integer providerId) {
        if (providerId == 2) {
            popularMarketsList.forEach(matchOddDetails ->
                    matchOddDetails.getOutcomeDetailsList().forEach(OutcomeSorter::sortAndGetBackLayOddsForEventViewPopularAndGoalMarkets));
        } else {
            matchOdd1Details.getOutcomeDetailsList().forEach(OutcomeSorter::sortAndGetBackLayOddsForEventViewPopularAndGoalMarkets);
        }

    }

    private void restrictOutcomesToThreeInMatchOdds(MatchOddDetailsTO matchOddDetails) {
        matchOddDetails.getOutcomeDetailsList().forEach(eventViewOutcomeDetails -> {
            eventViewOutcomeDetails.setBackOddList(processBackLayOdds(eventViewOutcomeDetails.getBackOddList()));
            eventViewOutcomeDetails.setLayOddList(processBackLayOdds(eventViewOutcomeDetails.getLayOddList()));
        });
    }

    private List<OddDetailsTO> processBackLayOdds(List<OddDetailsTO> oddDetailsList) {
        List<OddDetailsTO> oddDetailsSubList;
        oddDetailsList.sort(new OddDetailsOddsComparator());
        if (oddDetailsList.size() > 3) {
            oddDetailsSubList = oddDetailsList.subList(0, 3);
        } else if (oddDetailsList.size() < 3) {
            oddDetailsSubList = new ArrayList<>(oddDetailsList);
            for (int i = 0; i <= 3 - oddDetailsSubList.size(); i++) {
                OddDetailsTO oddDetails = new OddDetailsTO();
                oddDetails.setOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetails.setSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetails.setOutcomeId(SportsBookConstants.DEFAULT_ODDS_AND_SIZE.intValue());
                oddDetailsSubList.add(oddDetails);
            }
        } else return oddDetailsList;
        return oddDetailsSubList;
    }


   /* private List<OddDetailsTO> processBackOdds(List<OddDetailsTO> oddDetailsList) {
        List<OddDetailsTO> oddDetailsSubList = new ArrayList<>();
        if (oddDetailsList.size() > 3) {
            oddDetailsSubList = oddDetailsList.subList(0, 3);
        } else if (oddDetailsList.size() > 0 && oddDetailsList.size() < 3) {
            OddDetailsTO oddDetails = new OddDetailsTO();
            for (int i = 0; i <= 3 - oddDetailsList.size(); i++) {
                oddDetails.setOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetails.setSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetailsSubList.add(oddDetails);
            }
        } else return oddDetailsList;
        return oddDetailsSubList;
    }

    private List<OddDetailsTO> processLayOdds(List<OddDetailsTO> oddDetailsList) {
        List<OddDetailsTO> oddDetailsSubList = new ArrayList<>();
        if (oddDetailsList.size() > 3) {
            oddDetailsSubList = oddDetailsList.subList(3, 6);
        } else if (oddDetailsList.size() > 0 && oddDetailsList.size() < 3) {
            OddDetailsTO oddDetails = new OddDetailsTO();
            for (int i = 0; i <= 3 - oddDetailsList.size(); i++) {
                oddDetails.setOdds(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetails.setSize(SportsBookConstants.DEFAULT_ODDS_AND_SIZE);
                oddDetailsSubList.add(oddDetails);
            }
        } else return oddDetailsList;
        return oddDetailsSubList;
    }*/

    @Override
    public SportHierarchyDetailsTO getCommonEventDetails(Integer sportId) {
        SportHierarchyDetailsTO sportDetails = sportsBookDao.getCommonEventDetails(sportId);
        sportDetails.getLeagueDetailsList().forEach(leagueDetails ->
                leagueDetails.getEventsList().forEach(eventDetails -> {
                            FancyMarketDetailsWrapperTO fancyMarketDetailsWrapperInput = eventDetails.getFancyMarketDetailsWrapper();
                            eventDetails.setFancyBetDetails(this.setFancyMarketDetails(fancyMarketDetailsWrapperInput, 3));
                            eventDetails.setFancyBet1Details(this.setFancyMarketDetails(fancyMarketDetailsWrapperInput, 4));
                        }
                ));
        return sportDetails;
    }

    private FancyMarketDetailsWrapperTO setFancyMarketDetails(FancyMarketDetailsWrapperTO fancyMarketDetailsWrapperInput, int providerId) {
        Predicate<FancyBetDetailsTO> fancyBetDetailsPredicate = fancyBetDetails -> fancyBetDetails.getProviderId() == providerId;
        FancyMarketDetailsWrapperTO fancyMarketDetailsWrapperOutput = new FancyMarketDetailsWrapperTO();
        FancyBetDetailsTO fancyBetDetails = fancyMarketDetailsWrapperInput
                .getFancyBetDetailsList()
                .parallelStream()
                .filter(fancyBetDetailsPredicate)
                .collect(Collectors.toList()).get(0);
        if (fancyBetDetails != null && fancyBetDetails.getProviderId() != null && fancyBetDetails.getProviderName() != null) {
            fancyMarketDetailsWrapperOutput.setProviderId(fancyBetDetails.getProviderId());
            fancyMarketDetailsWrapperOutput.setProviderName(fancyBetDetails.getProviderName());
        }
        fancyMarketDetailsWrapperOutput.setFancyBetDetailsList(fancyMarketDetailsWrapperInput
                .getFancyBetDetailsList()
                .parallelStream()
                .filter(fancyBetDetailsPredicate)
                .collect(Collectors.toList()));
        return fancyMarketDetailsWrapperOutput;
    }

    @Override
    public BetSlipFetchDetailsWrapperTO getBetSlipDetails(BetSlipFetchDetailsWrapperTO betSlipFetchDetailsWrapper) {
        BetSlipFetchDetailsWrapperTO betSlipFetchDetailsWrapper2 = new BetSlipFetchDetailsWrapperTO();
        List<Integer> eventIdList = new ArrayList<>();
        betSlipFetchDetailsWrapper.getBackBetsList().forEach(betDetailsFetch -> {
            BetDetailsFetchTO bets = betSlipDetailsDao.getBetSlipDetails(betDetailsFetch);
            if (bets.getEventId() != null) {
                betSlipFetchDetailsWrapper2.getBackBetsList().add(bets);
            } else {
                eventIdList.add(bets.getEventId());
            }
        });
        betSlipFetchDetailsWrapper.getLayBetsList().forEach(betDetailsFetch -> {
            BetDetailsFetchTO bets = betSlipDetailsDao.getBetSlipDetails(betDetailsFetch);
            if (bets.getEventId() != null) {
                betSlipFetchDetailsWrapper2.getLayBetsList().add(bets);
            } else {
                eventIdList.add(bets.getEventId());
            }
        });
        if (eventIdList.size() > 0) {
            betSlipFetchDetailsWrapper2.setUserComments(SportsBookConstants.EVENTS_DOES_NOT_EXISTS);
        }
        return betSlipFetchDetailsWrapper2;
    }

    private String replaceOutcomeNames(String outcomeName, String homeTeam, String awayTeam) {
        switch (outcomeName) {
            case SportsBookConstants.OutcomeNames.ONE:
                outcomeName = homeTeam;
                break;
            case SportsBookConstants.OutcomeNames.TWO:
                outcomeName = awayTeam;
                break;
            case SportsBookConstants.OutcomeNames.X:
                outcomeName = SportsBookConstants.OutcomeNames.TO_DRAW;
                break;
        }
        return outcomeName;
    }

    @Override
    public UserProfileDetailsWrapperTO getUserProfile(String userToken) {
        return sportsBookDao.getUserProfileDetails(userToken);
    }

    @Override
    public SportsBookConstants.PasswordMessages updateUserPasswordDetails(UserPasswordUpdateDetailsTO password) throws Exception {
        if (!password.getCurrentPassword().equalsIgnoreCase(password.getNewPassword())) {
            UserLoginDetailsTO userDetails = sportsBookDao.getUserDetails(password);
            boolean isPasswordMatch = passwordEncoder.matches((password.getCurrentPassword()), userDetails.getPassword());
            if (isPasswordMatch) {
                sportsBookDao.updateUserPassword(passwordEncoder.encode(password.getNewPassword()), userDetails.getEmailId());
                return SportsBookConstants.PasswordMessages.PASSWORD_UPDATE;
            } else {
                return SportsBookConstants.PasswordMessages.PASSWORD_NOT_MATCHED;
            }
        } else {
            return SportsBookConstants.PasswordMessages.PASSWORD_MATCH;
        }
    }

    @Override
    public String validateEmail(String emailConfirmationToken) {
        return sportsBookDao.validateEmail(emailConfirmationToken);
    }

    @Override
    public void updateForgetPassword(ForgetPasswordInputTO forgetPasswordInput, String emailId) {
        sportsBookDao.updateUserPassword(passwordEncoder.encode(forgetPasswordInput.getNewPassword()), emailId);
    }

    public Boolean hasDuplicate(UserCoinDetailsTO userDetails, int coinSize) {
        if (userDetails.getUserCoinsDetailsList().size() != coinSize) {
            return true;
        }
        Set<Double> appeared = new HashSet<>();
        for (CoinDetailsTO coinDetailsTO : userDetails.getUserCoinsDetailsList()) {
            if (!appeared.add(coinDetailsTO.getCoinValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserTransactionEventViewDetailsTO> getTransactionProfitLossView(String userToken, String transactionType, String transactionDate, String betPlaced, Integer eventId, String outcomeName) {
        return sportsBookDao.getTransactionProfitLossView(userToken, transactionType, transactionDate, betPlaced, eventId, outcomeName);
    }

    public int getTransactionsCount(String userToken, String transactionType, String fromDate, String toDate) {
        return sportsBookDao.getTransactionsCount(userToken, transactionType, fromDate, toDate);
    }

    @Override
    public List<UserTransactionDetailsTO> getTransactionDetails(String userToken, String transactionType, String fromDate, String toDate, Integer pageNumber, Integer pageSize, Integer sportId) {
        return sportsBookDao.getTransactionDetails(userToken, transactionType, fromDate, toDate, pageNumber, pageSize, sportId);
    }

    @Override
    public List<SportProfitLossDetailsTo> getSportsProftLossDetails(String userToken, String fromDate, String toDate) {
        return sportsBookDao.getSportsProftLossDetails(userToken, fromDate, toDate);
    }

    @Override
    public UserBetHistoryDetailsWrapperTO getBetHistoryDetails(BetHistoryFetchDetailsTO betHistoryFetchDetailsTO) {
        return sportsBookDao.extractBetHistoryDetails(betHistoryFetchDetailsTO);

    }

    @Override
    public BalanceDetailsTO getUserBalance(String loginToken) {
        return sportsBookDao.extractUserBalance(loginToken);
    }

    @Override
    public EmailVerificationDetailsTo getEmailVerifiedStatus(String token) {
        EmailVerificationDetailsTo emailVerificationDetailsTo = null;
        String emailVerificationStatus = sportsBookDao.getEmailVerifiedStatus(token);
        if (emailVerificationStatus.equalsIgnoreCase(SportsBookConstants.EmailVerificationStatus.VERIFIED.toString())) {
            emailVerificationDetailsTo = new EmailVerificationDetailsTo();
            emailVerificationDetailsTo.setStatus(SportsBookConstants.EmailVerificationStatus.VERIFIED);
            emailVerificationDetailsTo.setMessage(SportsBookConstants.EmailVerificationConstants.VERIFIED);
        } else if (emailVerificationStatus.equalsIgnoreCase(SportsBookConstants.EmailVerificationStatus.EMAIL_ALREADY_VERIFIED.toString())) {
            emailVerificationDetailsTo = new EmailVerificationDetailsTo();
            emailVerificationDetailsTo.setStatus(SportsBookConstants.EmailVerificationStatus.EMAIL_ALREADY_VERIFIED);
            emailVerificationDetailsTo.setMessage(SportsBookConstants.EmailVerificationConstants.ALREADY_VERIFIED);
        } else if (emailVerificationStatus.equalsIgnoreCase(SportsBookConstants.EmailVerificationStatus.TOKEN_EXPIRED.toString())) {
            emailVerificationDetailsTo = new EmailVerificationDetailsTo();
            emailVerificationDetailsTo.setStatus(SportsBookConstants.EmailVerificationStatus.TOKEN_EXPIRED);
            emailVerificationDetailsTo.setMessage(SportsBookConstants.EmailVerificationConstants.TOKEN_EXPIRED);
        }
        return emailVerificationDetailsTo;
    }

    @Override
    public Boolean authenticateUser(String emailId) {
        Integer emailVerified = sportsBookDao.getEmailVerificationStatus(emailId);
        return emailVerified != null && emailVerified == 1;
    }

    @Override
    public int getEmailIdCount(String emailId) {
        int count = sportsBookDao.getEmailIdCount(emailId);

        return count;
    }

    @Override
    public String UpdateForgetPwdEmailConfirmation(String emailId) {
        String emailConfirmationToken = UUID.randomUUID().toString();
        sportsBookDao.updateGeneratedTokenConfirmation(emailId, emailConfirmationToken);
        this.toCreatePwdSendEmailConfirmation(emailId, emailConfirmationToken);
        return emailConfirmationToken;
    }

    private void toCreatePwdSendEmailConfirmation(String emailId, String emailConfirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailId);
        mailMessage.setSubject(SportsBookConstants.EmailVerificationConstants.EMAIL_VERIFICATION);
        mailMessage.setFrom("info@gamex.group");
        mailMessage.setText(SportsBookConstants.EmailVerificationConstants.CREATE_PASSWORD + confirmPassword + emailConfirmationToken);
        emailSenderService.sendEmail(mailMessage);
    }

    public BetfairSessionTO getSessionKey() {
        return sportsBookDao.getBetfairSessionKey();
    }

}
