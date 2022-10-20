package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.DownlineListDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.impl.DownlineListDaoImpl;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service.DownlineListService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.FailureReasonConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DownlineListServiceImpl implements DownlineListService {

    @Autowired
    private DownlineListDao downlineListDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DownlineListDaoImpl downlineListDaoImpl;

    @Override
    public DownlineListDetailsWrapperTO getDownLineListDetails(String userToken, Integer userId) {
        DownlineListDetailsWrapperTO downLineListDetailsWrapper = downlineListDao.getDownListUserBalanceDetails(userToken, userId);
        List<DownLineUserDetailsTO> downLineUserDetailsList = downlineListDao.getDownlineListDetails(userToken, userId);
        downLineUserDetailsList
                .parallelStream()
                .forEach(downLineUserDetails ->
                        downLineUserDetails
                                .setPlayerBalance(
                                        downLineUserDetails.getPlayerBalance() + downLineUserDetails.getExposure()));
        double sumOfMasterBalances = downLineUserDetailsList.stream().mapToDouble(DownLineUserDetailsTO::getBalance).sum();
        downLineListDetailsWrapper.setAvailableBalance(downLineListDetailsWrapper.getBalance() + downLineListDetailsWrapper.getTotalAvailableBalance());
        downLineListDetailsWrapper.setTotalBalance(sumOfMasterBalances);
        downLineListDetailsWrapper.setCreditDistributed(downLineUserDetailsList.stream().mapToDouble(DownLineUserDetailsTO::getCreditLimmit).sum());
        downLineListDetailsWrapper.setUserDetailsList(downLineUserDetailsList);
        downLineListDetailsWrapper.setTotalProfitLoss(downLineUserDetailsList.stream().mapToDouble(DownLineUserDetailsTO::getRefProfitLoss).sum());
        this.truncateBalances(downLineListDetailsWrapper);
        return downLineListDetailsWrapper;
    }

    private void truncateBalances(DownlineListDetailsWrapperTO downLineListDetailsWrapper) {
        RoundingMode roundingMode = RoundingMode.CEILING;
        int newScale = 2;
        downLineListDetailsWrapper.getUserDetailsList().forEach(downLineUserDetails -> {
                    downLineUserDetails.setBalance(new BigDecimal(downLineUserDetails.getBalance())
                            .setScale(newScale, roundingMode).doubleValue());
                    downLineUserDetails.setPlayerBalance(new BigDecimal(downLineUserDetails.getPlayerBalance())
                            .setScale(newScale, roundingMode).doubleValue());
                    downLineUserDetails.setRefProfitLoss(new BigDecimal(downLineUserDetails.getRefProfitLoss())
                            .setScale(newScale, roundingMode).doubleValue());
                    downLineUserDetails.setAvailableBalance(new BigDecimal(downLineUserDetails.getAvailableBalance())
                            .setScale(newScale, roundingMode).doubleValue());
                    downLineUserDetails.setExposure(new BigDecimal(downLineUserDetails.getExposure())
                            .setScale(newScale, roundingMode).doubleValue());
                    downLineUserDetails.setExposureLimit(new BigDecimal(downLineUserDetails.getExposureLimit())
                            .setScale(newScale, roundingMode).doubleValue());
                }
        );
        downLineListDetailsWrapper.setTotalAvailableBalance(new BigDecimal(downLineListDetailsWrapper.getTotalAvailableBalance())
                .setScale(newScale, roundingMode).doubleValue());
        downLineListDetailsWrapper.setAvailableBalance(new BigDecimal(downLineListDetailsWrapper.getAvailableBalance())
                .setScale(newScale, roundingMode).doubleValue());
        downLineListDetailsWrapper.setTotalBalance(new BigDecimal(downLineListDetailsWrapper.getTotalBalance())
                .setScale(newScale, roundingMode).doubleValue());
        downLineListDetailsWrapper.setTotalExposure(new BigDecimal(downLineListDetailsWrapper.getTotalExposure())
                .setScale(newScale, roundingMode).doubleValue());
        downLineListDetailsWrapper.setBalance(new BigDecimal(downLineListDetailsWrapper.getBalance())
                .setScale(newScale, roundingMode).doubleValue());
    }

    public Boolean validateToken(UserDetailsTO userDetails) throws Exception {
        Integer loginUserRoleId;
        loginUserRoleId = downlineListDao.getDbRole(userDetails.getUserToken());

        if (loginUserRoleId == 1 && (userDetails.getRoleId() == 3 || userDetails.getRoleId() == 4 || userDetails.getRoleId() == 1)) {
            throw new Exception(ExceptionConstants.INVALID_ADMIN_ROLE);
        } else if (loginUserRoleId == 2 && (userDetails.getRoleId() == 1 || userDetails.getRoleId() == 2 || userDetails.getRoleId() == 4 || userDetails.getRoleId() == 5
                || userDetails.getRoleId() == 6)) {
            throw new Exception(ExceptionConstants.INVALID_SUPER_ROLE);
        } else if (loginUserRoleId == 3 && (userDetails.getRoleId() == 1 || userDetails.getRoleId() == 3 || userDetails.getRoleId() == 2 || userDetails.getRoleId() == 5
                || userDetails.getRoleId() == 6)) {
            throw new Exception(ExceptionConstants.INVALID_MASTER_ROLE);
        } else if (loginUserRoleId == 4 && (userDetails.getRoleId() == 4 || userDetails.getRoleId() == 1 || userDetails.getRoleId() == 3 || userDetails.getRoleId() == 2 ||
                userDetails.getRoleId() == 5
                || userDetails.getRoleId() == 6)) {
            throw new Exception(ExceptionConstants.INVALID_PLAYER_ROLE);
        } else if (loginUserRoleId == 5 && (userDetails.getRoleId() == 4 || userDetails.getRoleId() == 1 || userDetails.getRoleId() == 3 || userDetails.getRoleId() == 5
                || userDetails.getRoleId() == 6)) {
            throw new Exception(ExceptionConstants.INVALID_PLAYER_ROLE);
        } else {
            return downlineListDao.getDbToken(userDetails.getUserToken());
        }
    }

    private Boolean validateChangeStatusToken(ChangeStatusRoleIdTo changeStatusRole) throws Exception {
        boolean validRole;
        if (changeStatusRole.getRoleId() == 1 && (changeStatusRole.getSubRoleId() == 3 || changeStatusRole.getSubRoleId() == 4 || changeStatusRole.getSubRoleId() == 1)) {
            throw new Exception(ExceptionConstants.INVALID_CHANGE_ADMIN_ROLE);
        } else if (changeStatusRole.getRoleId() == 2 && (changeStatusRole.getSubRoleId() == 1 || changeStatusRole.getSubRoleId() == 2 || changeStatusRole.getSubRoleId() == 4)) {
            throw new Exception(ExceptionConstants.INVALID_CHANGE_SUPER_ROLE);
        } else if (changeStatusRole.getRoleId() == 3 && (changeStatusRole.getSubRoleId() == 1 || changeStatusRole.getSubRoleId() == 3 || changeStatusRole.getSubRoleId() == 2)) {
            throw new Exception(ExceptionConstants.IINVALID_CHANGE_MASTER_ROLE);
        } else if (changeStatusRole.getRoleId() == 4 && (changeStatusRole.getSubRoleId() == 1 || changeStatusRole.getSubRoleId() == 3 || changeStatusRole.getSubRoleId() == 2)) {
            throw new Exception(ExceptionConstants.INVALID_CHANGE_PLAYER_ROLE);
        } else {
            validRole = true;
        }
        return validRole;
    }
    @Override
    public Map<String, Object> addUser(UserDetailsTO userDetails) throws Exception {
        if (this.validateToken(userDetails)) {
            Map<String, Object> result = new HashMap<>();
            boolean isCreated;
            Integer emailIdCount = downlineListDao.getEmailIdCount(userDetails.getEmailId());
            Integer userNameCount = downlineListDao.getUserNameCount(userDetails.getUserName());
            Double userBalance = downlineListDao.getBalance(userDetails.getUserToken());

            if(userBalance<userDetails.getDepositedAmount() )
            {
                result.put(DownLineListConstants.USER_CREATED_KEY, false);
                result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
                result.put(DownLineListConstants.REASON, ExceptionConstants.INSUFFICIENT_BALANCE+" : Upline Balance ="+userBalance);
                return result;
            }
            if (emailIdCount < 1) {
                if(userNameCount<1 ) {
                    if(userDetails.getUserName().length()<8){
                        result.put(DownLineListConstants.USER_CREATED_KEY, false);
                        result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
                        result.put(DownLineListConstants.REASON, ExceptionConstants.INVALID_USER_NAME_LENGTH);
                    }else{
                        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
                        String emailConfirmationToken = UUID.randomUUID().toString();
                        isCreated = downlineListDaoImpl.processSaveUser(userDetails, encodedPassword, emailConfirmationToken) != 0;
                        result.put(DownLineListConstants.USER_CREATED_KEY, isCreated);
                    }
                }
                else {
                    result.put(DownLineListConstants.USER_CREATED_KEY, false);
                    result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
                    result.put(DownLineListConstants.REASON, ExceptionConstants.DUPLICATE_USER_NAME);
                }
            } else {
                result.put(DownLineListConstants.USER_CREATED_KEY, false);
                result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
                result.put(DownLineListConstants.REASON, FailureReasonConstants.DUPLICATE_EMAIL_ID.description());
            }
            return result;
        } else {
            throw new Exception(ExceptionConstants.WRONG_TOKEN);
        }
    }

    @Override
    public Map<String, Object> changeStatus(ChangeStatusTO changeStatus, UserBetDetailsTO userBetDetailsTO) {
        Map<String, Object> result = new HashMap<>();
        boolean isCreated = false;
        isCreated = downlineListDaoImpl.updateStatus(changeStatus,userBetDetailsTO) != 0;
        result.put(DownLineListConstants.USER_UPDATED_KEY, isCreated);
        return result;
    }

    @Override
    public List<BetListLiveTO> getBetListLive(String userToken) {
        return downlineListDao.getDownlineBetListLive(userToken);
    }

    @Override
    public BetListLiveWrapperTO getBetListLiveDetails(BetListLiveDetailsInputTO betListLiveDetailsInput) {
        if (betListLiveDetailsInput.getTransactionLimit() != null) {
            if (betListLiveDetailsInput.getTransactionLimit() == DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_25.getRefValue()) {
                betListLiveDetailsInput.setTransactionLimit(DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_25.getTransactionLimit());
            } else if (betListLiveDetailsInput.getTransactionLimit() == DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_50.getRefValue()) {
                betListLiveDetailsInput.setTransactionLimit(DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_50.getTransactionLimit());
            } else if (betListLiveDetailsInput.getTransactionLimit() == DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_100.getRefValue()) {
                betListLiveDetailsInput.setTransactionLimit(DownLineListConstants.TransactionLimits.TRANSACTION_LIMIT_100.getTransactionLimit());
            }
        }
        return downlineListDao.getDownlineBetListLiveDetails(betListLiveDetailsInput);
    }

    @Override
    public String getDbPassword(String userToken) {
        return downlineListDao.getUserDBPassword(userToken);
    }

    @Override
    public String updateCreditLimit(CreditRequestTO creditLimitTO) {
        return downlineListDao.updateCredit(creditLimitTO.getCreditList());
    }

    @Override
    public boolean getstatusChangedBy(Integer userId) {
        return downlineListDao.isChangedByAdmin(userId);
    }

    @Override
    public String getUserName(Integer userId) {
        return downlineListDao.getUser(userId);
    }

}