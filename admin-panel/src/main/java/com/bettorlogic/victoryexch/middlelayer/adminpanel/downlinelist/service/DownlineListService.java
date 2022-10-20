package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface DownlineListService {

    DownlineListDetailsWrapperTO getDownLineListDetails(String userToken, Integer userId);

    Map<String, Object> addUser(UserDetailsTO userDetails) throws Exception;

    Map<String, Object> changeStatus(ChangeStatusTO changeStatusTO, UserBetDetailsTO userBetDetailsTO) throws Exception;

    List<BetListLiveTO> getBetListLive(String userToken) throws ExecutionException, InterruptedException;

    BetListLiveWrapperTO getBetListLiveDetails(BetListLiveDetailsInputTO betListLiveDetailsInput);

    String getDbPassword(String userToken);

    String updateCreditLimit(CreditRequestTO creditLimitTO);

    boolean getstatusChangedBy(Integer userId);

    String getUserName(Integer userId);
}