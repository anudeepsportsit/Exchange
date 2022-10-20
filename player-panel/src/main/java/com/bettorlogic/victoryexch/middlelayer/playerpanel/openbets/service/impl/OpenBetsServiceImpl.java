package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.service.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dao.BetSlipDetailsDao;
import com.bettorlogic.victoryexch.middlelayer.common.dao.SportsBookDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ReplaceInstructionReportTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportErrorCode;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.service.BetPlacement;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dao.OpenBetsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto.*;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.service.OpenBetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants.DATE_PATTERN;

@Service
public class OpenBetsServiceImpl implements OpenBetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenBetsServiceImpl.class);
    @Autowired
    private OpenBetsDao openBetsDao;

    @Autowired
    private BetSlipDetailsDao betSlipDetailsDao;

    @Autowired
    private SportsBookDao sportsBookDao;

    @Override
    public List<OpenBetsEventWrapperTO> getOpenBetDetails(String userToken) {
        return openBetsDao.getOpenBetDetails(userToken);
    }

    @Override
    public List<UpdateOpenBetsOutput> updateOpenBetDetails(BetDetailsSubWrapperTO betDetailsSubWrapper, int userId) throws Exception {
        List<UpdateOpenBetsOutput> updateOpenBetsOutputList = new ArrayList<>();
        UpdateOpenBetsOutput updateOpenBetsOutput = null;
        for (OpenBetDetailsTO openBetDetails : betDetailsSubWrapper.getBackBets()) {

            //if(openBetDetails.getOddType().equalsIgnoreCase("Back")) {
            updateOpenBetsOutput = new UpdateOpenBetsOutput();
            BetPlacement placement = new BetPlacement();

            Double availableBalance = sportsBookDao.getUpdateBalance(openBetDetails.getEventId(), openBetDetails.getOddType(), openBetDetails.getStake(),
                    openBetDetails.getReturns(), 0, openBetDetails.getOdds(), openBetDetails.getBetId(), userId, ColumnLabelConstants.UPDATE, openBetDetails.getOutcomeId());

            if (availableBalance > 0) {

                ReplaceInstructionReportTO instructionReport = placement.updateOpenBets(openBetDetails.getClientMarketId(), openBetDetails.getBetfairBetId(), Double.valueOf(openBetDetails.getOdds()), betSlipDetailsDao.getSessionKey());
                if (instructionReport.getStatus() == InstructionReportStatus.SUCCESS) {
                    LOGGER.info("Bet Updated for bet id : " + openBetDetails.getBetId());
                    //openBetsDao.updateOpenBet(instructionReport.getPlaceInstructionReport(),openBetDetails.getOdds(),openBetDetails.getBetId(),openBetDetails.getReturns());
                    openBetsDao.updateOpenBetDetails(betDetailsSubWrapper.getUserToken(), openBetDetails, new Double(openBetDetails.getStake()), instructionReport.getPlaceInstructionReport());
                    updateOpenBetsOutput.setIsmatched(instructionReport.getPlaceInstructionReport().getOrderStatus() == OrderStatus.EXECUTABLE ? false : true);
                    updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                    updateOpenBetsOutput.setBetUpdated(true);
                    updateOpenBetsOutputList.add(updateOpenBetsOutput);
                }
                if (instructionReport.getErrorCode() != null) {
                    if (instructionReport.getErrorCode().toString().equalsIgnoreCase(InstructionReportErrorCode.CANCELLED_NOT_PLACED.toString())) {
                        LOGGER.info("Bet Cancelled for bet id : " + openBetDetails.getBetId());
                        populateCancelled(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                    } else {
                        populateMatchedLapsed(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                    }
                }
            } else {
                updateOpenBetsOutput.setReasonForFailure("Insufficient Exposure for Bet Update --  bet id : " + openBetDetails.getBetId());
                updateOpenBetsOutput.setBetUpdated(false);
                updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                updateOpenBetsOutputList.add(updateOpenBetsOutput);
            }
            /*}else{
                updateOpenBetsOutput = new UpdateOpenBetsOutput();
                BetPlacement placement = new BetPlacement();
                //Double availableBalance = openBetsDao.getBalance(openBetDetails,userId);

                Double availableBalance = sportsBookDao.getUpdateBalance(openBetDetails.getEventId(),openBetDetails.getOddType(),openBetDetails.getStake(),
                        openBetDetails.getReturns(),0,openBetDetails.getOdds(),openBetDetails.getBetId(),userId, ColumnLabelConstants.UPDATE, openBetDetails.getOutcomeId());

                if(availableBalance < new Double(openBetDetails.getReturns())- new Double(openBetDetails.getStake())){
                    LOGGER.info("Insufficient Balance for Bet Update --  bet id : " +openBetDetails.getBetId());
                    updateOpenBetsOutput.setReasonForFailure("Insufficient Balance");
                    updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                    updateOpenBetsOutput.setBetUpdated(false);
                    updateOpenBetsOutputList.add(updateOpenBetsOutput);
                }else {
                    ReplaceInstructionReportTO instructionReport = placement.updateOpenBets(openBetDetails.getClientMarketId(), openBetDetails.getBetfairBetId(), Double.valueOf(openBetDetails.getOdds()), betSlipDetailsDao.getSessionKey());
                    if (instructionReport.getStatus() == InstructionReportStatus.SUCCESS) {
                        LOGGER.info("Bet Updated for bet id : " +openBetDetails.getBetId());
                        //openBetsDao.updateOpenBet(instructionReport.getPlaceInstructionReport(),openBetDetails.getOdds(),openBetDetails.getBetId(), openBetDetails.getReturns());
                        openBetsDao.updateOpenBetDetails(betDetailsSubWrapper.getUserToken(), openBetDetails, new Double(openBetDetails.getReturns()) - new Double(openBetDetails.getStake()), instructionReport.getPlaceInstructionReport());
                        updateOpenBetsOutput.setIsmatched(instructionReport.getPlaceInstructionReport().getOrderStatus() == OrderStatus.EXECUTABLE ? false : true);
                        updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                        updateOpenBetsOutput.setBetUpdated(true);
                        updateOpenBetsOutputList.add(updateOpenBetsOutput);
                    }
                    if (instructionReport.getErrorCode() != null) {
                        if (instructionReport.getErrorCode().toString().equalsIgnoreCase(InstructionReportErrorCode.CANCELLED_NOT_PLACED.toString())) {
                            LOGGER.info("Bet Cancelled for bet id : " +openBetDetails.getBetId());
                            populateCancelled(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                        } else {
                            populateMatchedLapsed(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                        }
                    }
                }
            }*/
        }
        for (OpenBetDetailsTO openBetDetails : betDetailsSubWrapper.getLayBets()) {
            updateOpenBetsOutput = new UpdateOpenBetsOutput();
            BetPlacement placement = new BetPlacement();
            //Double availableBalance = openBetsDao.getBalance(openBetDetails,userId);

            Double availableBalance = sportsBookDao.getUpdateBalance(openBetDetails.getEventId(),openBetDetails.getOddType(),openBetDetails.getStake(),
                    openBetDetails.getReturns(),0,openBetDetails.getOdds(),openBetDetails.getBetId(),userId, ColumnLabelConstants.UPDATE, openBetDetails.getOutcomeId());

            if(availableBalance < new Double(openBetDetails.getReturns())- new Double(openBetDetails.getStake())){
                LOGGER.info("Insufficient Balance for Bet Update --  bet id : " +openBetDetails.getBetId());
                updateOpenBetsOutput.setReasonForFailure("Insufficient Balance");
                updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                updateOpenBetsOutput.setBetUpdated(false);
                updateOpenBetsOutputList.add(updateOpenBetsOutput);
            }else {
                ReplaceInstructionReportTO instructionReport = placement.updateOpenBets(openBetDetails.getClientMarketId(), openBetDetails.getBetfairBetId(), Double.valueOf(openBetDetails.getOdds()), betSlipDetailsDao.getSessionKey());
                if (instructionReport.getStatus() == InstructionReportStatus.SUCCESS) {
                    LOGGER.info("Bet Updated for bet id : " +openBetDetails.getBetId());
                    //openBetsDao.updateOpenBet(instructionReport.getPlaceInstructionReport(),openBetDetails.getOdds(),openBetDetails.getBetId(), openBetDetails.getReturns());
                    openBetsDao.updateOpenBetDetails(betDetailsSubWrapper.getUserToken(), openBetDetails, new Double(openBetDetails.getReturns()) - new Double(openBetDetails.getStake()), instructionReport.getPlaceInstructionReport());
                    updateOpenBetsOutput.setIsmatched(instructionReport.getPlaceInstructionReport().getOrderStatus() == OrderStatus.EXECUTABLE ? false : true);
                    updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
                    updateOpenBetsOutput.setBetUpdated(true);
                    updateOpenBetsOutputList.add(updateOpenBetsOutput);
                }
                if (instructionReport.getErrorCode() != null) {
                    if (instructionReport.getErrorCode().toString().equalsIgnoreCase(InstructionReportErrorCode.CANCELLED_NOT_PLACED.toString())) {
                        LOGGER.info("Bet Cancelled for bet id : " +openBetDetails.getBetId());
                        populateCancelled(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                    } else {
                        populateMatchedLapsed(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
                    }
                }
            }
        }

        /*for (OpenBetDetailsTO openBetDetails : betDetailsSubWrapper.getLayBetDetailsList()) {

        }*/
        return updateOpenBetsOutputList;
    }

    public void populateCancelled(List<UpdateOpenBetsOutput> updateOpenBetsOutputList, UpdateOpenBetsOutput updateOpenBetsOutput, OpenBetDetailsTO openBetDetails) {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        String currentUtc = odt.format((DateTimeFormatter.ofPattern(DATE_PATTERN)));
        openBetsDao.cancelBets(openBetDetails.getBetId(), currentUtc, openBetDetails.getBetfairBetId());
        openBetsDao.updateUserBalance(currentUtc);
        updateOpenBetsOutput.setReasonForFailure("Bet Cancelled");
        updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
        updateOpenBetsOutput.setBetUpdated(false);
        updateOpenBetsOutputList.add(updateOpenBetsOutput);
    }

    public void populateMatchedLapsed(List<UpdateOpenBetsOutput> updateOpenBetsOutputList, UpdateOpenBetsOutput updateOpenBetsOutput, OpenBetDetailsTO openBetDetails) {
        Integer isMatched = openBetsDao.getIsMatched(openBetDetails.getBetId());
        if(isMatched == 1){
            LOGGER.info("Bet Matched for bet id : " +openBetDetails.getBetId());
            updateOpenBetsOutput.setReasonForFailure("Bet Matched");
            updateOpenBetsOutput.setBetId(openBetDetails.getBetId());
            updateOpenBetsOutput.setBetUpdated(false);
            updateOpenBetsOutputList.add(updateOpenBetsOutput);
        }
        else {
            LOGGER.info("Bet Cancelled for bet id : " +openBetDetails.getBetId());
            populateCancelled(updateOpenBetsOutputList, updateOpenBetsOutput, openBetDetails);
        }
    }

    private void updateUserBalance(Double totalStake, Double totalExposure, String betIds, String userToken) {
        UserBalanceDetailsTO userBalanceDetails = new UserBalanceDetailsTO();
        userBalanceDetails.setTransactionAmount(totalStake);
        userBalanceDetails.setFromTo(SportsBookConstants.OpenBetsConstants.OPEN_BETS_FROM_TO);
        userBalanceDetails.setTransactionRemarks(SportsBookConstants.OpenBetsConstants.OPEN_BETS_TRANSACTION_TYPE_ID + betIds);
        userBalanceDetails.setTransactionTypeId(0);
        userBalanceDetails.setTransactionType(SportsBookConstants.OpenBetsConstants.OPEN_BETS_TRANS_TYPE);
        userBalanceDetails.setUserToken(userToken);
        betSlipDetailsDao.updateUserBalanceDetails(userBalanceDetails, totalExposure);
    }

    @Override
    public CancelOpenBetsOutput cancelOpenBets(String userToken, List<Integer> betIdList) throws Exception {
        List<CancelOpenBetsOutput> cancelOpenBetsOutputList = new ArrayList<>();
        betIdList.forEach(betId ->
                cancelOpenBetsOutputList.add(openBetsDao.cancelOpenBets(userToken, betId))
        );
        return this.setReasonForFailureForCancelBets(cancelOpenBetsOutputList);
    }

    @Override
    public int getId(String userToken) {
        try {
            return openBetsDao.getUserId(userToken);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<OpenBetsEventWrapperTO> getAgentOpenBetDetails(String marketName) {
        return openBetsDao.getAgentOpenBetDetails(marketName);
    }

    @Override
    public Double getExposureLimit(int userId) {
        return openBetsDao.getUserExposureLimit(userId);
    }

    private CancelOpenBetsOutput setReasonForFailureForCancelBets(List<CancelOpenBetsOutput> cancelOpenBetsOutputList) throws Exception {
        CancelOpenBetsOutput cancelOpenBetsOutput = new CancelOpenBetsOutput();
        cancelOpenBetsOutput.setBetCancelled(cancelOpenBetsOutputList.parallelStream().allMatch(CancelOpenBetsOutput::isBetCancelled));
        if (!cancelOpenBetsOutput.isBetCancelled()) {
            cancelOpenBetsOutput.setValidPlayer(cancelOpenBetsOutputList.parallelStream().allMatch(CancelOpenBetsOutput::isValidPlayer));
            if (cancelOpenBetsOutput.isValidPlayer()) {
                cancelOpenBetsOutput.setPlayerHasBet(cancelOpenBetsOutputList.parallelStream().allMatch(CancelOpenBetsOutput::isPlayerHasBet));
                if (cancelOpenBetsOutput.isPlayerHasBet()) {
                    cancelOpenBetsOutput.setInOpenStatus(cancelOpenBetsOutputList.parallelStream().allMatch(CancelOpenBetsOutput::isInOpenStatus));
                    if (cancelOpenBetsOutput.isInOpenStatus()) {
                        cancelOpenBetsOutput.setUnmatchedBet(cancelOpenBetsOutputList.parallelStream().allMatch(CancelOpenBetsOutput::isUnmatchedBet));
                        if (!cancelOpenBetsOutput.isUnmatchedBet()) {
                            cancelOpenBetsOutput.setReasonForFailure(ExceptionConstants.UNMATCHED_BETS_CANNOT_BET_EDITED);
                        }
                    } else {
                        cancelOpenBetsOutput.setReasonForFailure(ExceptionConstants.IS_NOT_OPEN_BET);
                    }
                } else {
                    cancelOpenBetsOutput.setReasonForFailure(ExceptionConstants.PLAYER_DOES_NOT_HAVE_THE_GIVEN_BET);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }
        return cancelOpenBetsOutput;
    }

    /*private UpdateOpenBetsOutput setReasonForFailure(List<UpdateOpenBetsOutput> updateOpenBetsOutputList) throws Exception {
        UpdateOpenBetsOutput updateOpenBetsOutput = new UpdateOpenBetsOutput();
        updateOpenBetsOutput.setBetUpdated(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::isBetUpdated));
        if (!updateOpenBetsOutput.isBetUpdated()) {
            updateOpenBetsOutput.setValidPlayer(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::isValidPlayer));
            if (updateOpenBetsOutput.isValidPlayer()) {
                updateOpenBetsOutput.setPlayerHasBet(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::isPlayerHasBet));
                if (updateOpenBetsOutput.isPlayerHasBet()) {
                    updateOpenBetsOutput.setHasExceededExposureLimit(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::hasExceededExposureLimit));
                    if (!updateOpenBetsOutput.hasExceededExposureLimit()) {
                        updateOpenBetsOutput.setUnmatchedBet(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::isUnmatchedBet));
                        if (updateOpenBetsOutput.isUnmatchedBet()) {
                            updateOpenBetsOutput.setHasSufficientBalance(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::hasSufficientBalance));
                            if (updateOpenBetsOutput.hasSufficientBalance()) {
                                updateOpenBetsOutput.setOpenBet(updateOpenBetsOutputList.parallelStream().allMatch(UpdateOpenBetsOutput::isOpenBet));
                                if (!updateOpenBetsOutput.isOpenBet()) {
                                    updateOpenBetsOutput.setReasonForFailure(ExceptionConstants.IS_NOT_OPEN_BET);
                                }
                            } else {
                                updateOpenBetsOutput.setReasonForFailure(SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
                            }
                        } else {
                            updateOpenBetsOutput.setReasonForFailure(ExceptionConstants.UNMATCHED_BETS_CANNOT_BET_EDITED);
                        }
                    } else {
                        updateOpenBetsOutput.setReasonForFailure(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT);
                    }
                } else {
                    updateOpenBetsOutput.setReasonForFailure(ExceptionConstants.PLAYER_DOES_NOT_HAVE_THE_GIVEN_BET);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }
        return updateOpenBetsOutput;
    }*/
}